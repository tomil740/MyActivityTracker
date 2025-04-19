package com.tomiappdevelopment.goalsTracker.data.workers

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tomiappdevelopment.core.domain.run.ActivitiesRepository
import com.tomiappdevelopment.goalstracker.domain.WeeklyGoalsRepository
import com.tomiappdevelopment.goalstracker.domain.useCase.GetWeekSummaryUseCase
import kotlinx.coroutines.flow.first

/**
 * WorkoutStatusWorker
 *
 * Daily worker that fetches weekly goals and activities from local database,
 * calculates progress summary using GetWeekSummaryUseCase,
 * and triggers a notification based on current status.
 *
 * ✅ Safe to run without failure handling – data sources are local and wrapped with safe flows.
 * ❗ Assumes goals and activities are available; otherwise, silently skips notification.
 * 🔁 Edge cases (e.g., empty data or nulls) are implicitly handled by design or retried by WorkManager.
 *
 * Triggered daily (e.g., at 8AM) via WorkManager setup at app-level.
 */

class WorkoutStatusWorker(
    private val context: Context,
    private val params: WorkerParameters,
    private val activitiesRepository: ActivitiesRepository,
    private val weeklyGoalsRepository: WeeklyGoalsRepository,
    private val getWeekSummaryUseCase: GetWeekSummaryUseCase
) : CoroutineWorker(context, params) {


    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        createNotificationChannel(context)
        return try {
            val goals = weeklyGoalsRepository.observeGoals().first()
            val activities = activitiesRepository.getWeekActivities().first()

            val summary = getWeekSummaryUseCase.invoke(
                weekActivities = activities,
                targets = goals
            )

            if (summary.summary.quantity > 0) {
                showWorkoutSummaryNotification(feedback = summary, context = context)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry() //  Result.failure()
        }
    }
}

