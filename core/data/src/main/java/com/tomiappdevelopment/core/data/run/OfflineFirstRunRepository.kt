package com.tomiappdevelopment.core.data.run


import com.tomiappdevelopment.core.data.networking.get
import com.tomiappdevelopment.core.domain.run.LocalRunDataSource
import com.tomiappdevelopment.core.domain.run.RemoteRunDataSource
import com.tomiappdevelopment.core.domain.run.Run
import com.tomiappdevelopment.core.domain.run.RunId
import com.tomiappdevelopment.core.domain.run.RunRepository
import com.tomiappdevelopment.core.domain.util.DataError
import com.tomiappdevelopment.core.domain.util.EmptyResult
import io.ktor.client.HttpClient
import com.tomiappdevelopment.core.domain.util.Result
import com.tomiappdevelopment.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import com.tomiappdevelopment.core.database.dao.RunPendingSyncDao
import com.tomiappdevelopment.core.database.mappers.toRun
import com.tomiappdevelopment.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.tomiappdevelopment.core.database.mappers.toRun
import com.tomiappdevelopment.core.domain.run.SyncRunScheduler
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope,
    private val runPendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
    private val syncRunScheduler: SyncRunScheduler,
    private val client: HttpClient
): RunRepository {

    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when(val result = remoteRunDataSource.getRuns()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if(localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDataSource.postRun(
            run = runWithId,
            mapPicture = mapPicture
        )

        return when(remoteResult) {
            is Result.Error -> {
                //will be scedule a matched soultion to handle the offline sync when online job
                applicationScope.launch {
                    syncRunScheduler.scheduleSync(
                        type = SyncRunScheduler.SyncType.CreateRun(
                            run = runWithId,
                            mapPictureBytes = mapPicture
                        )
                    )
                }.join()
                Result.Success(Unit)
            }
            is Result.Success -> {
                //why are we update again the local db ,is there an y differnt when pushed to remote ?
                applicationScope.async {
                    localRunDataSource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }


    override suspend fun syncPendingRuns() {
        withContext(Dispatchers.IO) {
            val userId = sessionStorage.get()?.userId ?: return@withContext

            val createdRuns = async {
                runPendingSyncDao.getAllRunPendingSyncEntities(userId)
            }
            val deletedRuns = async {
                runPendingSyncDao.getAllDeletedRunSyncEntities(userId)
            }

            val createJobs = createdRuns
                .await()
                .map {
                    launch {
                        val run = it.run.toRun()
                        when(remoteRunDataSource.postRun(run, it.mapPictureBytes)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    runPendingSyncDao.deleteRunPendingSyncEntity(it.runId)
                                }.join()
                            }
                        }
                    }
                }
            val deleteJobs = deletedRuns
                .await()
                .map {
                    launch {
                        when(remoteRunDataSource.deleteRun(it.runId)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    runPendingSyncDao.deleteDeletedRunSyncEntity(it.runId)
                                }.join()
                            }
                        }
                    }
                }

            createJobs.forEach { it.join() }
            deleteJobs.forEach { it.join() }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.deleteRun(id)

        // Edge case where the run is created in offline-mode,
        // and then deleted in offline-mode as well. In that case,
        // we don't need to sync anything.
        val isPendingSync = runPendingSyncDao.getRunPendingSyncEntity(id) != null
        if(isPendingSync) {
            runPendingSyncDao.deleteRunPendingSyncEntity(id)
            return
        }

        val remoteResult = applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()
    }

    override suspend fun deleteAllRuns() {
        localRunDataSource.deleteAllRuns()
    }

    override suspend fun logout(): EmptyResult<DataError.Network> {
        val result = client.get<Unit>(
            route = "/logout"
        ).asEmptyDataResult()

        client.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
            .firstOrNull()
            ?.clearToken()

        return result
    }
}