package com.kukurodev.minddrop.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "MindDrop",
            style = MaterialTheme.typography.headlineMedium
        )

        HomeCard(
            title = "Inbox",
            count = uiState.inboxCount
        )

        HomeCard(
            title = "Todo",
            count = uiState.todoCount
        )

        HomeCard(
            title = "Tracker",
            count = uiState.trackerCount
        )

        Text(
            text = "Today",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        if (uiState.todayItems.isEmpty()) {
            Text(
                text = "Bugün yapılacak görev yok.",
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            uiState.todayItems.forEach { item ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeCard(
    title: String,
    count: Int
) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "$count items",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}