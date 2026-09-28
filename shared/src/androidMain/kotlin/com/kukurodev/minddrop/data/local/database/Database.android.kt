package com.kukurodev.minddrop.data.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(
    context: Context
): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("minddrop.db")

    return Room.databaseBuilder(
        context = appContext,
        name = dbFile.absolutePath
    )
}