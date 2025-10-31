package com.example.univibe.ui.screens.features

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.example.univibe.ui.theme.*

object ReelsScreen : Screen {
    @Composable
    override fun Content() {
        ReelsScreenContent()
    }
}

@Composable
private fun ReelsScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Reels - Coming Soon", style = MaterialTheme.typography.titleLarge)
    }
}