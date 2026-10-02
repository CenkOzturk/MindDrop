package com.kukurodev.minddrop.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kukurodev.minddrop.feature.home.HomeScreen
import com.kukurodev.minddrop.feature.inbox.InboxScreen
import com.kukurodev.minddrop.feature.navigation.MindDropBottomBar
import com.kukurodev.minddrop.feature.reminder.ReminderScreen
import com.kukurodev.minddrop.feature.todo.TodoScreen
import com.kukurodev.minddrop.feature.tracker.TrackerScreen

@Composable
fun MindDropNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MindDropBottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen()
            }

            composable("inbox") {
                InboxScreen()
            }

            composable("todo") {
                TodoScreen()
            }

            composable("tracker") {
                TrackerScreen()
            }

            composable("reminder") {
                ReminderScreen()
            }
        }
    }
}