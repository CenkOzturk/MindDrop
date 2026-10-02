package com.kukurodev.minddrop.feature.home

import com.kukurodev.minddrop.domain.model.Item

data class HomeUiState(
    val inboxCount: Int = 0,
    val todoCount: Int = 0,
    val trackerCount: Int = 0,
    val todayItems: List<Item> = emptyList()
)