package com.example.univibe.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Data class representing a single message in a chat conversation.
 *
 * @param id Unique identifier for the message
 * @param senderId ID of the sender
 * @param senderName Name of the sender (for received messages)
 * @param senderAvatarUrl Optional URL for the sender's avatar
 * @param text Content of the message
 * @param timestamp When the message was sent (e.g., "2:30 PM", "Yesterday 3:45 PM")
 * @param isSentByCurrentUser Whether this message was sent by the current user
 * @param isRead Whether the message has been read by the recipient
 */
data class Message(
    val id: String,
    val senderId: String,
    val senderName: String = "",
    val senderAvatarUrl: String? = null,
    val text: String,
    val timestamp: String,
    val isSentByCurrentUser: Boolean,
    val isRead: Boolean = false
)

/**
 * Individual message bubble component for displaying a single message in a chat.
 * Shows message text, timestamp, and delivery status for sent messages.
 *
 * @param message The message data to display
 * @param modifier Modifier to apply to the bubble
 */
@Composable
fun MessageBubble(
    message: Message,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md, vertical = Dimensions.Spacing.xs),
        horizontalArrangement = if (message.isSentByCurrentUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isSentByCurrentUser) {
            // Received message - show avatar
            UserAvatar(
                imageUrl = message.senderAvatarUrl,
                size = Dimensions.AvatarSize.small,
                modifier = Modifier.padding(end = Dimensions.Spacing.sm)
            )
        }

        Column(
            horizontalAlignment = if (message.isSentByCurrentUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            // Sender name for received messages (optional)
            if (!message.isSentByCurrentUser && message.senderName.isNotEmpty()) {
                Text(
                    text = message.senderName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = Dimensions.Spacing.xs)
                )
            }

            // Message bubble
            Box(
                modifier = Modifier
                    .background(
                        color = if (message.isSentByCurrentUser)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                            topStart = if (message.isSentByCurrentUser) Dimensions.CornerRadius.large else Dimensions.CornerRadius.small,
                            topEnd = if (message.isSentByCurrentUser) Dimensions.CornerRadius.small else Dimensions.CornerRadius.large,
                            bottomStart = Dimensions.CornerRadius.large,
                            bottomEnd = Dimensions.CornerRadius.large
                        )
                    )
                    .clip(
                        androidx.compose.foundation.shape.RoundedCornerShape(
                            topStart = if (message.isSentByCurrentUser) Dimensions.CornerRadius.large else Dimensions.CornerRadius.small,
                            topEnd = if (message.isSentByCurrentUser) Dimensions.CornerRadius.small else Dimensions.CornerRadius.large,
                            bottomStart = Dimensions.CornerRadius.large,
                            bottomEnd = Dimensions.CornerRadius.large
                        )
                    )
                    .padding(Dimensions.Spacing.md)
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (message.isSentByCurrentUser)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
            }

            // Timestamp and status indicator for sent messages
            Row(
                modifier = Modifier
                    .padding(top = Dimensions.Spacing.xs)
                    .fillMaxWidth(0.9f),
                horizontalArrangement = if (message.isSentByCurrentUser) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(end = Dimensions.Spacing.xs)
                )

                // Status icon for sent messages
                if (message.isSentByCurrentUser) {
                    val statusSymbol = if (message.isRead) UISymbols.DONE_ALL else UISymbols.CHECK
                    val statusColor = if (message.isRead)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                    
                    TextIcon(
                        symbol = statusSymbol,
                        contentDescription = if (message.isRead) "Read" else "Sent",
                        modifier = Modifier.fillMaxWidth(0.1f),
                        tint = statusColor,
                        fontSize = 12
                    )
                }
            }
        }

        if (message.isSentByCurrentUser) {
            Spacer(modifier = Modifier.width(0.dp))
        }
    }
}

/**
 * Vertical list of message bubbles.
 * Used to display all messages in a chat conversation.
 *
 * @param messages List of messages to display
 * @param modifier Modifier to apply to the container
 */
@Composable
fun MessageBubbleList(
    messages: List<Message> = emptyList(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        messages.forEach { message ->
            MessageBubble(message = message)
        }
    }
}

/**
 * Convenience function to create sample/mock messages for preview and testing.
 */
fun getSampleMessages(): List<Message> = listOf(
    Message(
        id = "msg_1",
        senderId = "user_2",
        senderName = "Alex Morgan",
        text = "Hey! How's the project going?",
        timestamp = "2:30 PM",
        isSentByCurrentUser = false,
        isRead = true
    ),
    Message(
        id = "msg_2",
        senderId = "current_user",
        senderName = "You",
        text = "Pretty good! Almost done with the design phase.",
        timestamp = "2:31 PM",
        isSentByCurrentUser = true,
        isRead = true
    ),
    Message(
        id = "msg_3",
        senderId = "user_2",
        senderName = "Alex Morgan",
        text = "Nice! Want to grab coffee and discuss it?",
        timestamp = "2:32 PM",
        isSentByCurrentUser = false,
        isRead = true
    ),
    Message(
        id = "msg_4",
        senderId = "user_2",
        senderName = "Alex Morgan",
        text = "We could work on the implementation together",
        timestamp = "2:33 PM",
        isSentByCurrentUser = false,
        isRead = true
    ),
    Message(
        id = "msg_5",
        senderId = "current_user",
        senderName = "You",
        text = "Sounds great! ☕ Tomorrow at 3pm?",
        timestamp = "2:34 PM",
        isSentByCurrentUser = true,
        isRead = true
    ),
    Message(
        id = "msg_6",
        senderId = "user_2",
        senderName = "Alex Morgan",
        text = "Perfect! See you then 😊",
        timestamp = "2:35 PM",
        isSentByCurrentUser = false,
        isRead = true
    ),
    Message(
        id = "msg_7",
        senderId = "current_user",
        senderName = "You",
        text = "See you tomorrow!",
        timestamp = "2:36 PM",
        isSentByCurrentUser = true,
        isRead = false
    )
)