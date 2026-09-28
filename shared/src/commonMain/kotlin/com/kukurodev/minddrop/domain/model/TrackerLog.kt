package com.kukurodev.minddrop.domain.model

data class TrackerLog(
    val id: Long = 0,
    val trackerId: Long,
    val completedAt: Long
)