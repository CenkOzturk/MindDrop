package com.kukurodev.minddrop.feature.inbox

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

class InboxViewModel(
    private val repository: ItemRepository
) : ViewModel() {

    val uiState: StateFlow<InboxUiState> =
        repository.observeItems()
            .map { items ->
                InboxUiState(
                    items = items.filter {
                        it.type == ItemType.INBOX
                    }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = InboxUiState()
            )

    fun addInboxItem(title: String) {
        if (title.isBlank()) return

        viewModelScope.launch {
            repository.addItem(
                Item(
                    title = title.trim(),
                    type = ItemType.INBOX,
                    isCompleted = false,
                    createdAt = Clock.System.now().toEpochMilliseconds()
                )
            )
        }
    }

    fun deleteInboxItem(id: Long) {
        viewModelScope.launch {
            repository.deleteItem(id)
        }
    }

    fun moveToTodo(item: Item) {
        viewModelScope.launch {
            repository.updateItem(
                item.copy(
                    type = ItemType.TODO
                )
            )
        }
    }
}