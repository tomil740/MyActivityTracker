package com.tomiappdevelopment.goalsTracker.data.workers

import android.content.Context
import androidx.work.*
import java.time.Duration
import java.util.concurrent.TimeUnit

class WorkoutReminderScheduler(
    private val context: Context
) {
    companion object {
        private const val DAILY_WORK_NAME = "daily_workout_reminder"
        private const val REMIND_LATER_WORK_NAME = "remind_later_workout"
    }

    fun scheduleDailyReminder(hour: Int = 8, minute: Int = 0) {
        // Calculate initial delay (until next 8:00 AM)
        val now = java.time.LocalDateTime.now()
        val target = now.toLocalDate().atTime(hour, minute)
        val delay = Duration.between(now, if (now < target) target else target.plusDays(1))

        val workRequest = PeriodicWorkRequestBuilder<WorkoutStatusWorker>(
            24, TimeUnit.HOURS
        )
            .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
            .addTag(DAILY_WORK_NAME)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            DAILY_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun scheduleRemindLaterReminder(delay: Duration = Duration.ofHours(2)) {
        val workRequest = OneTimeWorkRequestBuilder<WorkoutStatusWorker>()
            .setInitialDelay(delay.toMillis(), TimeUnit.MILLISECONDS)
            .addTag(REMIND_LATER_WORK_NAME)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            REMIND_LATER_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun cancelAllReminders() {
        WorkManager.getInstance(context).cancelAllWorkByTag(DAILY_WORK_NAME)
        WorkManager.getInstance(context).cancelAllWorkByTag(REMIND_LATER_WORK_NAME)
    }
}
