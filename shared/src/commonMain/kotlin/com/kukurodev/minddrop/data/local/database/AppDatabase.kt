package com.kukurodev.minddrop.data.local.database

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
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun trackerLogDao(): TrackerLogDao
}