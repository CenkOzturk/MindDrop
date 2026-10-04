package com.kukurodev.minddrop.feature.reminder

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.launch
import kotlinx.datetime.number

@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    var title by remember {
        mutableStateOf("")
    }

    val now = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())

    var selectedDate by remember {
        mutableStateOf(now.date)
    }

    var selectedHour by remember {
        mutableIntStateOf(now.hour)
    }

    var selectedMinute by remember {
        mutableIntStateOf(now.minute)
    }

    var dateTimeError by remember {
        mutableStateOf(false)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                Text(
                    text = "Reminder",
                    style = MaterialTheme.typography.headlineMedium
                )

                TextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    placeholder = {
                        Text("Neyi hatırlamak istiyorsun?")
                    }
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Tarih",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            selectedDate = selectedDate.minus(
                                DatePeriod(days = 1)
                            )
                            dateTimeError = false
                        }
                    ) {
                        Text("<")
                    }

                    Text(
                        text = "${selectedDate.day}." +
                                "${selectedDate.month.number}." +
                                "${selectedDate.year}"
                    )

                    Button(
                        onClick = {
                            selectedDate = selectedDate.plus(
                                DatePeriod(days = 1)
                            )
                            dateTimeError = false
                        }
                    ) {
                        Text(">")
                    }
                }

                if (dateTimeError) {
                    Text(
                        text = "Geçmiş bir tarih veya saat seçemezsin.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            item {
                Text(
                    text = "Saat",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val result = changeTime(
                                hour = selectedHour,
                                minute = selectedMinute,
                                amountMinutes = -60,
                                date = selectedDate,
                                onDateChanged = {
                                    selectedDate = it
                                }
                            )

                            selectedHour = result.first
                            selectedMinute = result.second
                            dateTimeError = false
                        }
                    ) {
                        Text("- Saat")
                    }

                    Button(
                        onClick = {
                            val result = changeTime(
                                hour = selectedHour,
                                minute = selectedMinute,
                                amountMinutes = -5,
                                date = selectedDate,
                                onDateChanged = {
                                    selectedDate = it
                                }
                            )

                            selectedHour = result.first
                            selectedMinute = result.second
                            dateTimeError = false
                        }
                    ) {
                        Text("-5 dk")
                    }

                    Button(
                        onClick = {
                            val result = changeTime(
                                hour = selectedHour,
                                minute = selectedMinute,
                                amountMinutes = 60,
                                date = selectedDate,
                                onDateChanged = {
                                    selectedDate = it
                                }
                            )

                            selectedHour = result.first
                            selectedMinute = result.second
                            dateTimeError = false
                        }
                    ) {
                        Text("+ Saat")
                    }

                    Button(
                        onClick = {
                            val result = changeTime(
                                hour = selectedHour,
                                minute = selectedMinute,
                                amountMinutes = 5,
                                date = selectedDate,
                                onDateChanged = {
                                    selectedDate = it
                                }
                            )

                            selectedHour = result.first
                            selectedMinute = result.second
                            dateTimeError = false
                        }
                    ) {
                        Text("+5 dk")
                    }
                }

                Text(
                    text = "${selectedHour.toString().padStart(2, '0')}:" +
                            selectedMinute.toString().padStart(2, '0'),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                Button(
                    onClick = {
                        val dateTime = LocalDateTime(
                            year = selectedDate.year,
                            month = selectedDate.month,
                            day = selectedDate.day,
                            hour = selectedHour,
                            minute = selectedMinute
                        )

                        val dueAt = dateTime
                            .toInstant(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds()

                        if (dueAt <= Clock.System.now().toEpochMilliseconds()) {
                            dateTimeError = true
                            return@Button
                        }

                        viewModel.addReminder(
                            title = title,
                            dueAt = dueAt
                        )

                        title = ""
                    },
                    enabled = title.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reminder Ekle")
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = "Reminder'lar",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(uiState.items) { item ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = item.title
                        )

                        item.dueAt?.let { dueAt ->
                            Text(
                                text = formatReminderDate(dueAt),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.deleteReminder(item)
                        }
                    ) {
                        Text("Sil")
                    }
                }
            }
        }

    }
}

private fun formatReminderDate(
    timestamp: Long
): String {
    val dateTime = Instant
        .fromEpochMilliseconds(timestamp)
        .toLocalDateTime(
            TimeZone.currentSystemDefault()
        )

    return "${dateTime.day.toString().padStart(2, '0')}." +
            "${dateTime.monthNumber.toString().padStart(2, '0')}." +
            "${dateTime.year} " +
            "${dateTime.hour.toString().padStart(2, '0')}:" +
            dateTime.minute.toString().padStart(2, '0')
}

private fun changeTime(
    hour: Int,
    minute: Int,
    amountMinutes: Int,
    date: kotlinx.datetime.LocalDate,
    onDateChanged: (kotlinx.datetime.LocalDate) -> Unit
): Pair<Int, Int> {

    val totalMinutes =
        hour * 60 + minute + amountMinutes

    return when {
        totalMinutes < 0 -> {
            onDateChanged(
                date.minus(DatePeriod(days = 1))
            )

            val normalized =
                totalMinutes + 24 * 60

            Pair(
                normalized / 60,
                normalized % 60
            )
        }

        totalMinutes >= 24 * 60 -> {
            onDateChanged(
                date.plus(DatePeriod(days = 1))
            )

            val normalized =
                totalMinutes - 24 * 60

            Pair(
                normalized / 60,
                normalized % 60
            )
        }

        else -> {
            Pair(
                totalMinutes / 60,
                totalMinutes % 60
            )
        }
    }
}