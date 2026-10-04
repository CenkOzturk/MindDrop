package com.kukurodev.minddrop.notification

interface ReminderScheduler {

    fun schedule(
        reminderId: Long,
        title: String,
        dueAt: Long
    )

    fun cancel(reminderId: Long)
}