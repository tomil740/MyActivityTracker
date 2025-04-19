package com.tomiappdevelopment.goalsTracker.data.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

fun createNotificationChannel(theContext:Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "workout_status_channel",
            "Workout Status Updates",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for weekly workout motivation and stats"
        }

        val manager = theContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
