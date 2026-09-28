package com.kukurodev.minddrop.di

import com.kukurodev.minddrop.data.local.database.getDatabaseBuilder
import com.kukurodev.minddrop.data.local.database.getRoomDatabase
import org.koin.core.context.startKoin

object KoinInitializer {

    fun start() {
        val database = getRoomDatabase(
            getDatabaseBuilder()
        )

        startKoin {
            modules(
                commonModule,
                databaseModule(database)
            )
        }
    }
}