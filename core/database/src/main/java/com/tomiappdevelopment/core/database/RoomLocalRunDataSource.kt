package com.tomiappdevelopment.core.database


import android.database.sqlite.SQLiteFullException
import android.util.Log
import com.tomiappdevelopment.core.database.dao.RunDao
import com.tomiappdevelopment.core.database.mappers.toRun
import com.tomiappdevelopment.core.database.mappers.toRunEntity
import com.tomiappdevelopment.core.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.core.domain.run.LocalRunDataSource
import com.tomiappdevelopment.core.domain.run.Run
import com.tomiappdevelopment.core.domain.run.RunId
import com.tomiappdevelopment.core.domain.util.DataError
import com.tomiappdevelopment.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset

class RoomLocalRunDataSource(
    private val runDao: RunDao
): LocalRunDataSource {
    override fun getWeekActivities(): Flow<List<Run>> {
        val (startUtc, endUtc) = getCurrentWeekRangeUtc()
        return runDao.getActivitiesThisWeek(startUtc, endUtc)
            .map { entities -> entities.map { it.toRun() } }
    }


    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns()
            .map { runEntities ->
                runEntities.map { it.toRun() }
            }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val entity = run.toRunEntity()
            runDao.upsertRun(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val entities = runs.map { it.toRunEntity() }
            runDao.upsertRuns(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}

private fun getCurrentWeekRangeUtc(): Pair<String, String> {
    val today = LocalDate.now(ZoneOffset.UTC)

    // Go back to the previous (or same) Sunday
    val startOfWeek = today.minusDays(today.dayOfWeek.value % 7L)

    // Saturday is 6 days after Sunday
    val endOfWeek = startOfWeek.plusDays(6)

    val start = startOfWeek.atStartOfDay().atOffset(ZoneOffset.UTC).toInstant().toString()
    val end = endOfWeek.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC).toInstant().toString()

    Log.i("a", "start=$start , end=$end")
    return start to end
}

