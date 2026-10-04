package com.kukurodev.minddrop.notification

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNUserNotificationCenter

@OptIn(ExperimentalForeignApi::class)
class IosReminderScheduler : ReminderScheduler {

    private val notificationCenter =
        UNUserNotificationCenter.currentNotificationCenter()

    override fun schedule(
        reminderId: Long,
        title: String,
        dueAt: Long
    ) {
        val date = NSDate.dateWithTimeIntervalSince1970(
            dueAt.toDouble() / 1000.0
        )

        val calendar = NSCalendar.currentCalendar

        val components = calendar.components(
            unitFlags =
                NSCalendarUnitYear or
                        NSCalendarUnitMonth or
                        NSCalendarUnitDay or
                        NSCalendarUnitHour or
                        NSCalendarUnitMinute,
            fromDate = date
        )

        val content = UNMutableNotificationContent()

        content.setTitle("MindDrop")
        content.setBody(title)
        content.setSound(
            UNNotificationSound.defaultSound
        )

        val trigger =
            UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
                dateComponents = components,
                repeats = false
            )

        val request =
            UNNotificationRequest.requestWithIdentifier(
                identifier = reminderId.toString(),
                content = content,
                trigger = trigger
            )

        notificationCenter.addNotificationRequest(
            request
        ) { error ->
            if (error != null) {
                println(
                    "Reminder notification error: $error"
                )
            }
        }
    }

    override fun cancel(
        reminderId: Long
    ) {
        notificationCenter
            .removePendingNotificationRequestsWithIdentifiers(
                listOf(reminderId.toString())
            )
    }
}