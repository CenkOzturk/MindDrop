package com.kukurodev.minddrop.feature.reminder

import com.kukurodev.minddrop.domain.model.Item

data class ReminderUiState(
    val items: List<Item> = emptyList()
)