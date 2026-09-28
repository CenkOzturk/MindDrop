package com.kukurodev.minddrop

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kukurodev.minddrop.navigation.MindDropNavHost

@Composable
fun App() {
    MaterialTheme {
        MindDropNavHost()
    }
}