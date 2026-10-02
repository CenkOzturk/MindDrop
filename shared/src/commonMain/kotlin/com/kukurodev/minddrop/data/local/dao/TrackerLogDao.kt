package com.kukurodev.minddrop.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kukurodev.minddrop.data.local.entity.TrackerLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerLogDao {

    @Insert
    suspend fun insert(log: TrackerLogEntity): Long

    @Query(
        "SELECT * FROM tracker_logs " +
                "WHERE trackerId = :trackerId " +
                "ORDER BY completedAt DESC"
    )
    fun observeLogs(trackerId: Long): Flow<List<TrackerLogEntity>>

    @Query(
        "SELECT * FROM tracker_logs " +
                "WHERE trackerId = :trackerId " +
                "ORDER BY completedAt DESC " +
                "LIMIT 1"
    )
    suspend fun getLastLog(trackerId: Long): TrackerLogEntity?

    @Query(
        """
    SELECT * FROM tracker_logs
    ORDER BY completedAt DESC
    """
    )
    fun observeAllLogs(): Flow<List<TrackerLogEntity>>
}