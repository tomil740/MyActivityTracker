package com.tomiappdevelopment.core.domain.run


import com.tomiappdevelopment.core.domain.modules.ActivityGoalsData
import com.tomiappdevelopment.core.domain.util.DataError
import com.tomiappdevelopment.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias RunId = String

interface LocalRunDataSource {
    fun getWeekActivities():Flow<List<Run>>
    fun getRuns(): Flow<List<Run>>
    suspend fun upsertRun(run: Run): Result<RunId, DataError.Local>
    suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local>
    suspend fun deleteRun(id: String)
    suspend fun deleteAllRuns()
}