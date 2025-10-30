package com.example.univibe.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a chat conversation participant.
 *
 * @param userId Unique identifier for the user
 * @param userName Display name of the user
 * @param avatarUrl Optional URL for the user's avatar
 * @param isOnline Whether the user is currently online
 * @param lastSeen Last time the user was seen (e.g., "online", "2 hours ago", "Yesterday")
 */
data class ChatUser(
    val userId: String,
    val userName: String,
    val avatarUrl: String? = null,
    val isOnline: Boolean = true,
    val lastSeen: String = "online"
)

/**
 * Chat screen composable - displays one-on-one conversation with real-time messaging.
 * Shows message history, input field, and delivery/read status indicators.
 *
 * @param userId The ID of the user being chatted with (optional if chatUser is provided)
 * @param chatUser The user being chatted with (optional if userId is provided)
 * @param messages List of messages in the conversation
 * @param onSendMessage Callback when a message is sent
 * @param onBackClick Callback when back button is clicked
 * @param onCallClick Callback when call button is clicked
 * @param onInfoClick Callback when info button is clicked
 */
@Composable
fun ChatScreen(
    userId: String = "",
    chatUser: ChatUser? = null,
    messages: List<Message> = getSampleMessages(),
    onSendMessage: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onInfoClick: () -> Unit = {}
) {
    // If userId is provided, look up the chat user, otherwise use provided chatUser or default
    val actualChatUser = when {
        userId.isNotEmpty() -> getChatUsers().find { it.userId == userId } ?: getChatUsers()[0]
        chatUser != null -> chatUser
        else -> getChatUsers()[0]
    }
    var messageInput by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Chat header with user info and action buttons
        ChatHeader(
            chatUser = actualChatUser,
            onBackClick = onBackClick,
            onCallClick = onCallClick,
            onInfoClick = onInfoClick
        )

        // Messages list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(vertical = Dimensions.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            if (messages.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Spacing.lg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No messages yet. Start the conversation!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(messages.size) { index ->
                    MessageBubble(message = messages[index])
                }
            }
        }

        // Message input section
        ChatInputBar(
            messageText = messageInput,
            onMessageChange = { messageInput = it },
            onSend = {
                if (messageInput.isNotBlank()) {
                    onSendMessage(messageInput)
                    messageInput = ""
                }
            }
        )
    }
}

/**
 * Chat header showing user info and action buttons.
 */
@Composable
private fun ChatHeader(
    chatUser: ChatUser,
    onBackClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onInfoClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(Dimensions.IconSize.large)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // User info
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
        ) {
            Text(
                text = chatUser.userName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = if (chatUser.isOnline) "online" else chatUser.lastSeen,
                style = MaterialTheme.typography.labelSmall,
                color = if (chatUser.isOnline)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Call button
        IconButton(
            onClick = onCallClick,
            modifier = Modifier.size(Dimensions.IconSize.large)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Call",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Info button
        IconButton(
            onClick = onInfoClick,
            modifier = Modifier.size(Dimensions.IconSize.large)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Message input bar at the bottom of the chat.
 * Includes text input field and send button.
 */
@Composable
private fun ChatInputBar(
    messageText: String,
    onMessageChange: (String) -> Unit = {},
    onSend: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Text input field
        androidx.compose.material3.OutlinedTextField(
            value = messageText,
            onValueChange = onMessageChange,
            modifier = Modifier
                .weight(1f)
                .heightIn(min = Dimensions.InputHeight),
            placeholder = { Text("Type a message...") },
            textStyle = MaterialTheme.typography.bodyMedium,
            maxLines = 4,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.CornerRadius.large)
        )

        // Send button
        IconButton(
            onClick = onSend,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            enabled = messageText.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send message",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * Convenience function to create sample chat users for preview and testing.
 */
fun getChatUsers(): List<ChatUser> = listOf(
    ChatUser(
        userId = "user_2",
        userName = "Alex Morgan",
        isOnline = true,
        lastSeen = "online"
    ),
    ChatUser(
        userId = "user_3",
        userName = "Jordan Lee",
        isOnline = false,
        lastSeen = "30 mins ago"
    ),
    ChatUser(
        userId = "user_4",
        userName = "Casey Williams",
        isOnline = true,
        lastSeen = "online"
    ),
    ChatUser(
        userId = "user_5",
        userName = "Taylor Chen",
        isOnline = false,
        lastSeen = "Yesterday"
    ),
    ChatUser(
        userId = "user_6",
        userName = "Sam Jackson",
        isOnline = true,
        lastSeen = "online"
    )
)