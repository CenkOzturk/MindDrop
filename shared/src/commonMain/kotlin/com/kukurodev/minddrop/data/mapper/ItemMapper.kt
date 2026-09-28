package com.kukurodev.minddrop.data.mapper

import com.kukurodev.minddrop.data.local.entity.ItemEntity
import com.kukurodev.minddrop.domain.model.Item

fun ItemEntity.toDomain(): Item =
    Item(
        id = id,
        title = title,
        type = type,
        isCompleted = isCompleted,
        createdAt = createdAt,
        dueAt = dueAt
    )

fun Item.toEntity(): ItemEntity =
    ItemEntity(
        id = id,
        title = title,
        type = type,
        isCompleted = isCompleted,
        createdAt = createdAt,
        dueAt = dueAt
    )