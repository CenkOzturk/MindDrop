package com.kukurodev.minddrop.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukurodev.minddrop.domain.model.ItemType
import com.kukurodev.minddrop.domain.repository.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    repository: ItemRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        repository.observeItems()
            .map { items ->
                HomeUiState(
                    inboxCount = items.count {
                        it.type == ItemType.INBOX
                    },
                    todoCount = items.count {
                        it.type == ItemType.TODO
                    },
                    trackerCount = items.count {
                        it.type == ItemType.TRACKER
                    }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState()
            )
}