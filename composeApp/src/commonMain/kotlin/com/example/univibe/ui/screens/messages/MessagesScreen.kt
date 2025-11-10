package com.example.univibe.ui.screens.messages

import com.example.univibe.ui.theme.PlatformIcons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockMessages
import com.example.univibe.domain.models.Conversation
import com.example.univibe.ui.components.*
import kotlinx.coroutines.delay

@Composable
fun MessagesScreen(
    onConversationClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val navigator = LocalNavigator.currentOrThrow
    val listState = rememberLazyListState()
    var isInitialLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    val conversations = remember { MockMessages.conversations }
    
    LaunchedEffect(Unit) {
        delay(500)
        isInitialLoading = false
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(PlatformIcons.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(PlatformIcons.Add, contentDescription = "New message")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isInitialLoading) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(6) {
                        MessageSkeleton()
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Search bar
                    item {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search messages...") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                    
                    if (conversations.isEmpty()) {
                        item {
                            EmptyState(
                                title = "No messages",
                                description = "Start a conversation with someone",
                                icon = PlatformIcons.MailOutline,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        items(conversations) { conversation ->
                            ConversationCardItem(conversation)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ConversationCardItem(conversation: Conversation) {
    val participantName = conversation.participants.firstOrNull()?.name ?: "Unknown"
    val lastMessageText = conversation.lastMessage?.content ?: "No messages yet"
    val lastMessageTime = formatMessageTime(conversation.lastMessageTime)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {},
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = participantName,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = lastMessageText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            Text(
                text = lastMessageTime,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatMessageTime(timestamp: Long): String {
    if (timestamp == 0L) return ""
    // Simplified format - just show time
    return "now"
}