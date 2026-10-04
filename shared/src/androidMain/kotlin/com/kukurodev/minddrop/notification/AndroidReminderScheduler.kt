package com.kukurodev.minddrop.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AndroidReminderScheduler(
    private val context: Context
) : ReminderScheduler {

    override fun schedule(
        reminderId: Long,
        title: String,
        dueAt: Long
    ) {
        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        val intent = Intent(
            context,
            ReminderNotificationReceiver::class.java
        ).apply {
            putExtra("title", title)
            putExtra(
                "notification_id",
                reminderId.toInt()
            )
        }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                reminderId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            dueAt,
            pendingIntent
        )
    }

    override fun cancel(reminderId: Long) {
        val intent = Intent(
            context,
            ReminderNotificationReceiver::class.java
        )

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                reminderId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or
                        PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        alarmManager.cancel(pendingIntent)
    }
}