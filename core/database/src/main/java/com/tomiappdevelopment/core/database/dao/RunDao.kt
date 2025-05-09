package com.tomiappdevelopment.core.database.dao


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.tomiappdevelopment.core.database.entity.RunEntity
import com.tomiappdevelopment.core.domain.run.Run
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDao {

    @Upsert
    suspend fun upsertRun(run: RunEntity)

    @Upsert
    suspend fun upsertRuns(runs: List<RunEntity>)

    @Query("SELECT * FROM runentity ORDER BY dateTimeUtc DESC")
    fun getRuns(): Flow<List<RunEntity>>

    @Query(
        "SELECT * FROM runentity " +
                "WHERE dateTimeUtc BETWEEN :startUtc AND :endUtc"
    )
    fun getActivitiesThisWeek(
        startUtc: String,
        endUtc: String
    ): Flow<List<RunEntity>>

    @Query("DELETE FROM runentity WHERE id=:id")
    suspend fun deleteRun(id: String)

    @Query("DELETE FROM runentity")
    suspend fun deleteAllRuns()
}