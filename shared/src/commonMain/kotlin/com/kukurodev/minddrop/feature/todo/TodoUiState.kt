package com.kukurodev.minddrop.feature.todo

import com.kukurodev.minddrop.domain.model.Item

data class TodoUiState(
    val items: List<Item> = emptyList()
)