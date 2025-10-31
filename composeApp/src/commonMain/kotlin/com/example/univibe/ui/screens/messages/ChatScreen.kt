package com.example.univibe.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
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
import com.example.univibe.ui.components.social.ChatMessage
import com.example.univibe.ui.theme.Dimensions
import kotlinx.coroutines.launch

data class ChatScreen(val conversationId: String) : Screen {
    @Composable
    override fun Content() {
        ChatScreenContent(conversationId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreenContent(conversationId: String) {
    val navigator = LocalNavigator.currentOrThrow
    val currentUserId = "1" // Mock current user
    
    val conversation = remember { MockMessages.getConversationById(conversationId) }
    var messages by remember { mutableStateOf(MockMessages.getMessagesForConversation(conversationId)) }
    var messageText by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    
    // Get other user info
    val otherUserId = conversation?.participantIds?.firstOrNull { it != currentUserId }
    val otherUser = otherUserId?.let { userId ->
        MockUsers.users.find { it.id == userId }
    }
    
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(otherUser?.fullName ?: "Chat") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            MessageInputBar(
                value = messageText,
                onValueChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        isSending = true
                        
                        // Send message
                        MockMessages.sendMessage(conversationId, currentUserId, messageText)
                        messages = MockMessages.getMessagesForConversation(conversationId)
                        
                        // Clear input
                        messageText = ""
                        isSending = false
                        
                        // Scroll to bottom
                        scope.launch {
                            listState.animateScrollToItem(messages.size - 1)
                        }
                    }
                },
                enabled = !isSending
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface),
            state = listState,
            contentPadding = PaddingValues(vertical = Dimensions.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(
                items = messages,
                key = { it.id }
            ) { message ->
                ChatMessage(
                    message = message,
                    isCurrentUser = message.senderId == currentUserId
                )
            }
        }
    }
}

@Composable
private fun MessageInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Type a message...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(48.dp),
                enabled = enabled,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                textStyle = MaterialTheme.typography.bodySmall
            )
            
            IconButton(
                onClick = onSendClick,
                enabled = enabled && value.isNotBlank(),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send message",
                    tint = if (enabled && value.isNotBlank())
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}