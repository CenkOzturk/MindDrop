package com.kukurodev.minddrop.feature.todo

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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class TodoViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    val uiState: StateFlow<TodoUiState> =
        repository.observeItems()
            .map { items ->
                TodoUiState(
                    items = items.filter {
                        it.type == ItemType.TODO
                    }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TodoUiState()
            )

    fun addTodo(
        title: String,
        dueAt: Long? = null
    ) {
        if (title.isBlank()) return

        viewModelScope.launch {
            repository.addItem(
                Item(
                    title = title.trim(),
                    type = ItemType.TODO,
                    isCompleted = false,
                    createdAt = Clock.System.now().toEpochMilliseconds(),
                    dueAt = dueAt
                )
            )
        }
    }

    fun completeTodo(item: Item) {
        viewModelScope.launch {
            repository.updateItem(
                item.copy(
                    isCompleted = true
                )
            )
        }
    }

    fun deleteTodo(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item.id)
        }
    }
}