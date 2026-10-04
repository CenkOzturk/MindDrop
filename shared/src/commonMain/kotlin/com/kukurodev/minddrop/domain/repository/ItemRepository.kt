package com.kukurodev.minddrop.domain.repository

import com.kukurodev.minddrop.domain.model.Item
import com.kukurodev.minddrop.domain.model.TrackerLog
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    fun observeItems(): Flow<List<Item>>

    suspend fun getItem(id: Long): Item?

    suspend fun addItem(item: Item): Long

    suspend fun updateItem(item: Item)

    suspend fun deleteItem(id: Long)

    suspend fun addTrackerLog(log: TrackerLog)

    suspend fun getLastTrackerLog(trackerId: Long): TrackerLog?

    fun observeAllTrackerLogs(): Flow<List<TrackerLog>>

    fun observeTrackerLogs(trackerId: Long): Flow<List<TrackerLog>>
}