package com.kukurodev.minddrop.feature.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukurodev.minddrop.domain.model.Item
import com.kukurodev.minddrop.domain.model.ItemType
import com.kukurodev.minddrop.domain.repository.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock

class ReminderViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    val uiState: StateFlow<ReminderUiState> =
        repository.observeItems()
            .map { items ->
                ReminderUiState(
                    items = items.filter {
                        it.type == ItemType.REMINDER
                    }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ReminderUiState()
            )

    fun addReminder(
        title: String,
        dueAt: Long
    ) {
        if (title.isBlank()) return

        viewModelScope.launch {
            repository.addItem(
                Item(
                    title = title.trim(),
                    type = ItemType.REMINDER,
                    isCompleted = false,
                    createdAt = Clock.System.now()
                        .toEpochMilliseconds(),
                    dueAt = dueAt
                )
            )
        }
    }

    fun deleteReminder(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item.id)
        }
    }
}