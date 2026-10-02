package com.kukurodev.minddrop.feature.tracker

import com.kukurodev.minddrop.domain.model.Item
import com.kukurodev.minddrop.domain.model.TrackerLog

data class TrackerUiState(
    val items: List<Item> = emptyList(),
    val logs: List<TrackerLog> = emptyList()
)