package com.kukurodev.minddrop.data.repository

import com.kukurodev.minddrop.data.local.dao.ItemDao
import com.kukurodev.minddrop.data.local.dao.TrackerLogDao
import com.kukurodev.minddrop.data.mapper.toDomain
import com.kukurodev.minddrop.data.mapper.toEntity
import com.kukurodev.minddrop.domain.model.Item
import com.kukurodev.minddrop.domain.model.TrackerLog
import com.kukurodev.minddrop.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemRepositoryImpl(
    private val itemDao: ItemDao,
    private val trackerLogDao: TrackerLogDao
) : ItemRepository {

    override fun observeItems(): Flow<List<Item>> =
        itemDao.observeItems()
            .map { entities ->
                entities.map { it.toDomain() }
            }

    override suspend fun getItem(id: Long): Item? =
        itemDao.getItem(id)?.toDomain()

    override suspend fun addItem(item: Item) {
        itemDao.insert(item.toEntity())
    }

    override suspend fun updateItem(item: Item) {
        itemDao.update(item.toEntity())
    }

    override suspend fun deleteItem(id: Long) {
        itemDao.getItem(id)?.let { itemDao.delete(it) }
    }

    override suspend fun addTrackerLog(log: TrackerLog) {
        trackerLogDao.insert(log.toEntity())
    }

    override fun observeTrackerLogs(
        trackerId: Long
    ): Flow<List<TrackerLog>> =
        trackerLogDao.observeLogs(trackerId)
            .map { entities ->
                entities.map { it.toDomain() }
            }
}