package com.kukurodev.minddrop.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kukurodev.minddrop.domain.model.ItemType
import com.kukurodev.minddrop.domain.repository.ItemRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class HomeViewModel(
    repository: ItemRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        repository.observeItems()
            .map { items ->

                val now = Clock.System.now()
                val timeZone = TimeZone.currentSystemDefault()

                val today = now
                    .toLocalDateTime(timeZone)
                    .date

                val todayStart = today.atStartOfDayIn(timeZone)
                val tomorrowStart = today.plus(DatePeriod(days = 1))
                    .atStartOfDayIn(timeZone)

                HomeUiState(
                    inboxCount = items.count {
                        it.type == ItemType.INBOX
                    },
                    todoCount = items.count {
                        it.type == ItemType.TODO
                    },
                    trackerCount = items.count {
                        it.type == ItemType.TRACKER
                    },
                    todayItems = items.filter { item ->
                        item.type == ItemType.TODO &&
                                !item.isCompleted &&
                                item.dueAt != null &&
                                item.dueAt >= todayStart.toEpochMilliseconds() &&
                                item.dueAt < tomorrowStart.toEpochMilliseconds()
                    }
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState()
            )
}