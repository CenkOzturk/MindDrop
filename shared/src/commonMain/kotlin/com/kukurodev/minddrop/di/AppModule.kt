package com.kukurodev.minddrop.di

import com.kukurodev.minddrop.data.local.dao.ItemDao
import com.kukurodev.minddrop.data.local.dao.TrackerLogDao
import com.kukurodev.minddrop.data.local.database.AppDatabase
import com.kukurodev.minddrop.data.repository.ItemRepositoryImpl
import com.kukurodev.minddrop.domain.repository.ItemRepository
import org.koin.dsl.module

val commonModule = module {

    single<ItemDao> {
        get<AppDatabase>().itemDao()
    }

    single<TrackerLogDao> {
        get<AppDatabase>().trackerLogDao()
    }

    single<ItemRepository> {
        ItemRepositoryImpl(
            itemDao = get(),
            trackerLogDao = get()
        )
    }
}

fun databaseModule(database: AppDatabase) = module {

    single<AppDatabase> {
        database
    }
}