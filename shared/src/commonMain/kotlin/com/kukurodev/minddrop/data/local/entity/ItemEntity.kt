package com.kukurodev.minddrop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kukurodev.minddrop.domain.model.ItemType

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val type: ItemType,
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val dueAt: Long? = null
)