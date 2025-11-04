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

object CreatePostScreen : Screen {
    @Composable
    override fun Content() {
        CreatePostScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreatePostScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Post") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(
                            symbol = UISymbols.CLOSE,
                            contentDescription = "Close",
                            fontSize = 20
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { /* TODO: Create post */ }) {
                        Text("Post")
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
            Text("Create Post Form - Coming Soon", style = MaterialTheme.typography.titleLarge)
        }
    }
}