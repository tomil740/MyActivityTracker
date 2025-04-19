package com.tomiappdevelopment.goalsTracker.data.workers

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.tomiappdevelopment.goalstracker.domain.modules.WeekProgressFeedback

@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
 fun showWorkoutSummaryNotification(context: Context, feedback: WeekProgressFeedback) {

    val remindIntent = Intent(context, RemindLaterReceiver::class.java).apply {
        action = "REMIND_LATER_ACTION"
    }
    val pendingRemindIntent = PendingIntent.getBroadcast(
        context,
        0,
        remindIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val theIntent = Intent(Intent.ACTION_VIEW).apply {
        data = "runique://home_screen".toUri()
        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
    val openAppIntent = PendingIntent.getActivity(
        context,
        0,
        theIntent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val collapsedView = RemoteViews(context.packageName, com.tomiappdevelopment.goalstracker.core.R.layout.notification_collapsed).apply {
        setTextViewText(com.tomiappdevelopment.goalstracker.core.R.id.title, "You have done ${feedback.summary.quantity} workouts this week")
        setTextViewText(com.tomiappdevelopment.goalstracker.core.R.id.feedback, feedback.feedbackText)
    }

    val expandedView = RemoteViews(context.packageName, com.tomiappdevelopment.goalstracker.core.R.layout.notification_layout).apply {
        setTextViewText(com.tomiappdevelopment.goalstracker.core.R.id.title, "You have done ${feedback.summary.quantity} workouts this week")
        setTextViewText(com.tomiappdevelopment.goalstracker.core.R.id.feedback, feedback.feedbackText)
        setOnClickPendingIntent(com.tomiappdevelopment.goalstracker.core.R.id.setGoalsButton, openAppIntent)
        setOnClickPendingIntent(com.tomiappdevelopment.goalstracker.core.R.id.remindLaterButton, pendingRemindIntent)
    }

    val notification = NotificationCompat.Builder(context, "workout_status_channel")
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setCustomContentView(collapsedView)
        .setCustomBigContentView(expandedView)
        .setContentIntent(openAppIntent)
        .setSmallIcon(androidx.core.R.drawable.notify_panel_notification_icon_bg)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    NotificationManagerCompat.from(context).notify(123, notification)

}
