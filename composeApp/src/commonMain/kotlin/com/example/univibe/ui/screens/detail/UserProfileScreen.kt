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

data class UserProfileScreen(val userId: String) : Screen {
    @Composable
    override fun Content() {
        UserProfileScreenContent(userId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserProfileScreenContent(userId: String) {
    val navigator = LocalNavigator.currentOrThrow
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
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
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                Text("User Profile", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text("User ID: $userId", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(Spacing.sm))
                Text("Profile content coming soon...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}