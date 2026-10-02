package com.kukurodev.minddrop.feature.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukurodev.minddrop.domain.model.Item
import com.kukurodev.minddrop.domain.model.ItemType
import com.kukurodev.minddrop.domain.model.TrackerLog
import com.kukurodev.minddrop.domain.repository.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock

class TrackerViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    val uiState: StateFlow<TrackerUiState> =
        combine(
            repository.observeItems(),
            repository.observeAllTrackerLogs()
        ) { items, logs ->

            val trackerItems = items.filter {
                it.type == ItemType.TRACKER
            }

            TrackerUiState(
                items = trackerItems,
                logs = logs
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TrackerUiState()
            )

    fun addTracker(title: String) {
        if (title.isBlank()) return

        viewModelScope.launch {
            repository.addItem(
                Item(
                    title = title.trim(),
                    type = ItemType.TRACKER,
                    isCompleted = false,
                    createdAt = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }

    fun completeTracker(item: Item) {
        viewModelScope.launch {
            repository.addTrackerLog(
                TrackerLog(
                    trackerId = item.id,
                    completedAt = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }
}