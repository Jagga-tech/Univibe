package com.example.univibe.ui.screens.detail

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

data class ConversationScreen(val conversationId: String) : Screen {
    @Composable
    override fun Content() {
        ConversationScreenContent(conversationId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConversationScreenContent(conversationId: String) {
    val navigator = LocalNavigator.currentOrThrow
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(
                            symbol = UISymbols.BACK,
                            contentDescription = "Back",
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
            Text("Conversation - Coming Soon", style = MaterialTheme.typography.titleLarge)
        }
    }
}