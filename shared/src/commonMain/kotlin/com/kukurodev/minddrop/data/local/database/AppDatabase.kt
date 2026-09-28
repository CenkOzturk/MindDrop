package com.kukurodev.minddrop.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.kukurodev.minddrop.data.local.dao.ItemDao
import com.kukurodev.minddrop.data.local.dao.TrackerLogDao
import com.kukurodev.minddrop.data.local.entity.ItemEntity
import com.kukurodev.minddrop.data.local.entity.TrackerLogEntity

@Database(
    entities = [
        ItemEntity::class,
        TrackerLogEntity::class
    ],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun trackerLogDao(): TrackerLogDao
}