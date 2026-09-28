package com.kukurodev.minddrop.domain.model

data class Item(
    val id: Long = 0,
    val title: String,
    val type: ItemType,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val dueAt: Long? = null
)
