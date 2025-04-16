package com.tomiappdevelopment.goalstracker.domain

import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum
import kotlinx.coroutines.flow.Flow

interface WeeklyGoalsRepository {
    suspend fun saveGoals(goals: WeekDataSum)
    fun observeGoals(): Flow<WeekDataSum>
}