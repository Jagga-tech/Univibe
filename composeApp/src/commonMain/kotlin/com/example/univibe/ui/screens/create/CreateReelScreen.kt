package com.example.univibe.ui.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.theme.*
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols

object CreateReelScreen : Screen {
    @Composable
    override fun Content() {
        CreateReelScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateReelScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Reel") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(
                            symbol = UISymbols.CLOSE,
                            contentDescription = "Close",
                            fontSize = 20
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Create Reel - Coming Soon", style = MaterialTheme.typography.titleLarge)
        }
    }
}