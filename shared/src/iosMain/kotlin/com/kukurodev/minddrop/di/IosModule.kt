package com.kukurodev.minddrop.di

import com.kukurodev.minddrop.notification.IosReminderScheduler
import com.kukurodev.minddrop.notification.ReminderScheduler
import org.koin.dsl.module

val iosModule = module {
    single<ReminderScheduler> {
        IosReminderScheduler()
    }
}