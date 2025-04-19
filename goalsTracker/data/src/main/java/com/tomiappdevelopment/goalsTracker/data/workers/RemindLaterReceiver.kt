package com.tomiappdevelopment.goalsTracker.data.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class RemindLaterReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "REMIND_LATER_ACTION") {
            val scheduler = WorkoutReminderScheduler(context)
            scheduler.scheduleRemindLaterReminder()
            NotificationManagerCompat.from(context).cancel(123)
        }
    }
}
