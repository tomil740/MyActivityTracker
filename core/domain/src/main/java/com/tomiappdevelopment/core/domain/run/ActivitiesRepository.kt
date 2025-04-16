package com.tomiappdevelopment.core.domain.run


import com.tomiappdevelopment.core.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.core.domain.util.DataError
import com.tomiappdevelopment.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface ActivitiesRepository {
    fun getWeekActivities():Flow<List<ActivityGoalsData>>
    fun getRuns(): Flow<List<Run>>
    suspend fun fetchRuns(): EmptyResult<DataError>
    suspend fun upsertRun(run: Run, mapPicture: ByteArray): EmptyResult<DataError>
    suspend fun deleteRun(id: RunId)
    suspend fun syncPendingRuns()
    suspend fun deleteAllRuns()
    suspend fun logout(): EmptyResult<DataError.Network>
}