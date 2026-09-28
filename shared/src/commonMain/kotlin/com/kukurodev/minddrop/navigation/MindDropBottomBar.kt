package com.kukurodev.minddrop.feature.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val route: String,
    val label: String
)

private val bottomNavItems = listOf(
    BottomNavItem("home", "Home"),
    BottomNavItem("inbox", "Inbox"),
    BottomNavItem("todo", "Todo"),
    BottomNavItem("tracker", "Tracker")
)

@Composable
fun MindDropBottomBar(
    navController: NavHostController
) {
    val navBackStackEntry =
        navController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry.value?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Text(item.label.take(1))
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}