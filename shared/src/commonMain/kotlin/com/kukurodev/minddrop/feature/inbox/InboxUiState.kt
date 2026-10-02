package com.kukurodev.minddrop.feature.inbox

import com.kukurodev.minddrop.domain.model.Item

data class InboxUiState(
    val items: List<Item> = emptyList()
)