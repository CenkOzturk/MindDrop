package com.kukurodev.minddrop.feature.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var title by remember {
        mutableStateOf("")
    }

    var isToday by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Todo")

        TextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            placeholder = {
                Text("Ne yapman gerekiyor?")
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isToday,
                onCheckedChange = {
                    isToday = it
                }
            )

            Text("Bugün")
        }

        Button(
            onClick = {
                val dueAt = if (isToday) {
                    Clock.System.now().toEpochMilliseconds()
                } else {
                    null
                }

                viewModel.addTodo(
                    title = title,
                    dueAt = dueAt
                )

                title = ""
                isToday = false
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Ekle")
        }

        if (uiState.items.isEmpty()) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Todo boş"
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = uiState.items,
                    key = { it.id }
                ) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (item.isCompleted) {
                                "✓ ${item.title}"
                            } else {
                                item.title
                            }
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (!item.isCompleted) {
                                Button(
                                    onClick = {
                                        viewModel.completeTodo(item)
                                    }
                                ) {
                                    Text("Yaptım")
                                }
                            }

                            Button(
                                onClick = {
                                    viewModel.deleteTodo(item)
                                }
                            ) {
                                Text("Sil")
                            }
                        }
                    }
                }
            }
        }
    }
}