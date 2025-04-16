package com.tomiappdevelopment.goalsTracker.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WeeklyGoalsDao {

    @Upsert
    suspend fun upsertGoals(goals: WeeklyGoalsEntity)

    @Query("SELECT * FROM weekly_goals WHERE id = 0")
    fun observeGoals(): Flow<WeeklyGoalsEntity?>

    @Query("SELECT * FROM weekly_goals WHERE id = 0")
    suspend fun getGoals(): WeeklyGoalsEntity?
}
