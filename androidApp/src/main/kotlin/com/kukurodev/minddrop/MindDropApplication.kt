package com.kukurodev.minddrop

import android.app.Application
import com.kukurodev.minddrop.data.local.database.getDatabaseBuilder
import com.kukurodev.minddrop.data.local.database.getRoomDatabase
import com.kukurodev.minddrop.di.commonModule
import com.kukurodev.minddrop.di.databaseModule
import com.kukurodev.minddrop.notification.AndroidReminderScheduler
import com.kukurodev.minddrop.notification.ReminderScheduler
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MindDropApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val database = getRoomDatabase(
            getDatabaseBuilder(this)
        )

        startKoin {
            modules(
                commonModule,
                databaseModule(database),
                module {
                    single<ReminderScheduler> {
                        AndroidReminderScheduler(
                            context = this@MindDropApplication
                        )
                    }
                }
            )
        }
    }
}