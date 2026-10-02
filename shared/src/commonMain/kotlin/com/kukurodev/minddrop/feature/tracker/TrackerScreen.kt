package com.kukurodev.minddrop.feature.tracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kukurodev.minddrop.domain.model.TrackerLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant

@Composable
fun TrackerScreen(
    viewModel: TrackerViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }

    val completedTrackers = remember {
        mutableStateMapOf<Long, Boolean>()
    }

    val currentDate = remember { today() }

    var selectedDate by remember {
        mutableStateOf(currentDate)
    }

    var displayedYear by remember {
        mutableIntStateOf(currentDate.year)
    }

    var displayedMonth by remember {
        mutableStateOf(currentDate.month)
    }

    val scope = rememberCoroutineScope()

    val selectedLogs = uiState.logs.filter { log ->
        Instant
            .fromEpochMilliseconds(log.completedAt)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date == selectedDate
    }

    val offset = firstDayOffset(
        displayedYear,
        displayedMonth
    )

    val dayCount = daysInMonth(
        displayedYear,
        displayedMonth
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = 40.dp,
                top = 16.dp,
                start = 16.dp,
                end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // -------------------------
        // HEADER
        // -------------------------

        item {
            Text(
                text = "Tracker",
                style = MaterialTheme.typography.headlineMedium
            )

            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                placeholder = {
                    Text("Neyi takip etmek istiyorsun?")
                }
            )

            Button(
                onClick = {
                    viewModel.addTracker(title)
                    title = ""
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Ekle")
            }
        }

        // -------------------------
        // TRACKER LIST
        // -------------------------

        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        items(uiState.items) { item ->

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title
                    )

                    val isCompleted =
                        completedTrackers[item.id] == true

                    Button(
                        enabled = !isCompleted,
                        onClick = {
                            viewModel.completeTracker(item)

                            completedTrackers[item.id] = true

                            scope.launch {
                                delay(2_000)
                                completedTrackers.remove(item.id)
                            }
                        }
                    ) {
                        Text(
                            text = if (isCompleted) {
                                "✓ Yapıldı"
                            } else {
                                "Yaptım"
                            }
                        )
                    }
                }
            }
        }

        // -------------------------
        // CALENDAR
        // -------------------------

        item {
            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "${displayedMonth.name} $displayedYear",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    "P",
                    "S",
                    "Ç",
                    "P",
                    "C",
                    "C",
                    "P"
                ).forEach { dayName ->
                    Text(
                        text = dayName,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // -------------------------
        // CALENDAR DAYS
        // -------------------------

        item {
            Column {

                var day = 1

                repeat(6) { rowIndex ->

                    if (day > dayCount) {
                        return@repeat
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        repeat(7) { column ->

                            val cellIndex =
                                rowIndex * 7 + column

                            if (
                                cellIndex < offset ||
                                day > dayCount
                            ) {
                                Spacer(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                )
                            } else {

                                val date = LocalDate(
                                    year = displayedYear,
                                    month = displayedMonth,
                                    day = day
                                )

                                val hasActivity =
                                    hasActivityOnDate(
                                        logs = uiState.logs,
                                        date = date
                                    )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clickable {
                                            selectedDate = date
                                        },
                                    horizontalAlignment =
                                        Alignment.CenterHorizontally
                                ) {

                                    Text(
                                        text = day.toString()
                                    )

                                    if (hasActivity) {
                                        Text(
                                            text = "●",
                                            style = MaterialTheme
                                                .typography
                                                .labelSmall
                                        )
                                    }
                                }

                                day++
                            }
                        }
                    }
                }
            }
        }

        // -------------------------
        // SELECTED DAY
        // -------------------------

        item {
            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "${selectedDate.day}." +
                        "${selectedDate.monthNumber}." +
                        "${selectedDate.year}",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // -------------------------
        // SELECTED DAY LOGS
        // -------------------------

        if (selectedLogs.isEmpty()) {

            item {
                Text(
                    text = "Bu gün herhangi bir tracker yapılmamış.",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

        } else {

            items(selectedLogs) { log ->

                val tracker = uiState.items.firstOrNull {
                    it.id == log.trackerId
                }

                if (tracker != null) {
                    Text(
                        text = "✓ ${tracker.title}",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

private fun formatTrackerDate(timestamp: Long): String {
    val dateTime = Instant
        .fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    return "${dateTime.day.toString().padStart(2, '0')}." +
            "${dateTime.monthNumber.toString().padStart(2, '0')}." +
            "${dateTime.year} " +
            "${dateTime.hour.toString().padStart(2, '0')}:" +
            dateTime.minute.toString().padStart(2, '0')
}

fun hasActivityOnDate(
    logs: List<TrackerLog>,
    date: LocalDate
): Boolean {
    val timeZone = TimeZone.currentSystemDefault()

    return logs.any { log ->
        Instant
            .fromEpochMilliseconds(log.completedAt)
            .toLocalDateTime(timeZone)
            .date == date
    }
}