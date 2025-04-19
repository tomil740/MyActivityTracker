package com.tomiappdevelopment.goalsTracker.data

import com.tomiappdevelopment.goalsTracker.data.local.WeeklyGoalsDao
import com.tomiappdevelopment.goalsTracker.data.local.WeeklyGoalsEntity
import com.tomiappdevelopment.goalsTracker.data.workers.WorkoutReminderScheduler
import com.tomiappdevelopment.goalstracker.domain.WeeklyGoalsRepository
import com.tomiappdevelopment.goalstracker.domain.modules.WeekDataSum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeeklyGoalsRepositoryImpl(
    private val dao: WeeklyGoalsDao,
    private val workoutReminderScheduler:WorkoutReminderScheduler
) : WeeklyGoalsRepository {

    override suspend fun saveGoals(goals: WeekDataSum) {
        val a = WeeklyGoalsEntity(runningDistanceKm = goals.distance, workoutCount = goals.quantity)
        dao.upsertGoals(a)
    }

    override fun observeGoals(): Flow<WeekDataSum> {
        return dao.observeGoals()
            .map { entity ->
                if (entity != null) {
                        WeekDataSum(
                            entity.workoutCount,
                            entity.runningDistanceKm
                        )
                } else {
                    // Return default object if not set
                    WeekDataSum(
                        quantity = 4,
                        distance = 30f
                    )
                }
            }
    }

    init {
        workoutReminderScheduler.scheduleDailyReminder()
    }

}