package com.kukurodev.minddrop.data.mapper

import com.kukurodev.minddrop.data.local.entity.TrackerLogEntity
import com.kukurodev.minddrop.domain.model.TrackerLog

fun TrackerLogEntity.toDomain(): TrackerLog =
    TrackerLog(
        id = id,
        trackerId = trackerId,
        completedAt = completedAt
    )

fun TrackerLog.toEntity(): TrackerLogEntity =
    TrackerLogEntity(
        id = id,
        trackerId = trackerId,
        completedAt = completedAt
    )