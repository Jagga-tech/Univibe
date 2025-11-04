package com.example.univibe.ui.screens.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockMessages
import com.example.univibe.data.mock.MockUsers
import com.example.univibe.ui.components.social.ConversationItem
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

object MessagesScreen : Screen {
    @Composable
    override fun Content() {
        MessagesScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessagesScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    var conversations by remember { mutableStateOf(MockMessages.getAllConversations()) }
    val currentUserId = "1" // Mock current user
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(symbol = UISymbols.BACK, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Start new conversation */ }) {
                        TextIcon(symbol = UISymbols.EDIT, contentDescription = "New message")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (conversations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
                ) {
                    Text(
                        text = "No conversations yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Start messaging with friends",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(
                    items = conversations,
                    key = { it.id }
                ) { conversation ->
                    val otherUserId = conversation.participantIds.firstOrNull { it != currentUserId }
                    val otherUser = otherUserId?.let { userId ->
                        MockUsers.users.find { it.id == userId }
                    }
                    
                    ConversationItem(
                        conversation = conversation,
                        onConversationClick = { clickedConversation ->
                            // Mark as read
                            MockMessages.markConversationAsRead(clickedConversation.id)
                            conversations = MockMessages.getAllConversations()
                            
                            // Navigate to chat
                            navigator.push(ChatScreen(clickedConversation.id))
                        },
                        otherUserName = otherUser?.fullName ?: "Unknown User"
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
                }
            }
        }
    }
}