package com.example.univibe.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a conversation/chat thread.
 *
 * @param id Unique identifier for the conversation
 * @param userId ID of the other user in the conversation
 * @param userName Name of the other user
 * @param userAvatarUrl Optional URL for the user's avatar
 * @param lastMessage Preview of the last message sent
 * @param timestamp When the last message was sent (e.g., "2 mins ago", "Yesterday")
 * @param isUserOnline Whether the other user is currently online
 * @param unreadCount Number of unread messages in this conversation
 */
data class ConversationItem(
    val id: String,
    val userId: String,
    val userName: String,
    val userAvatarUrl: String? = null,
    val lastMessage: String,
    val timestamp: String,
    val isUserOnline: Boolean = false,
    val unreadCount: Int = 0
)

/**
 * Individual conversation card component for displaying a single conversation.
 * Shows user info, last message preview, and unread indicator.
 *
 * @param item The conversation item data
 * @param onClick Callback when the conversation card is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun ConversationCard(
    item: ConversationItem,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth(),
        onClick = { onClick(item.id) },
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User avatar with online indicator
            BadgedBox(
                badge = {
                    if (item.isUserOnline) {
                        Badge(
                            modifier = Modifier
                                .offset(x = (-4).dp, y = 4.dp)
                        )
                    }
                }
            ) {
                UserAvatar(
                    imageUrl = item.userAvatarUrl,
                    size = Dimensions.AvatarSize.medium,
                    showOnlineStatus = item.isUserOnline
                )
            }

            // Conversation info
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                // User name
                Text(
                    text = item.userName,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Last message preview
                Text(
                    text = item.lastMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Timestamp and unread badge
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                // Timestamp
                Text(
                    text = item.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Unread message badge
                if (item.unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .then(
                                Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = androidx.compose.foundation.shape.CircleShape
                                    )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (item.unreadCount > 9) "9+" else item.unreadCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

/**
 * Vertical list of conversation cards.
 * Used to display all user conversations.
 *
 * @param conversations List of conversation items to display
 * @param onConversationClick Callback when a conversation is clicked
 * @param modifier Modifier to apply to the container
 */
@Composable
fun ConversationCardList(
    conversations: List<ConversationItem> = emptyList(),
    onConversationClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        conversations.forEach { conversation ->
            ConversationCard(
                item = conversation,
                onClick = onConversationClick
            )
        }
    }
}

/**
 * Convenience function to create sample/mock conversations for preview and testing.
 */
fun getSampleConversations(): List<ConversationItem> = listOf(
    ConversationItem(
        id = "conv_1",
        userId = "user_1",
        userName = "Alex Morgan",
        lastMessage = "Yeah, let's meet up tomorrow at the library!",
        timestamp = "2 mins",
        isUserOnline = true,
        unreadCount = 1
    ),
    ConversationItem(
        id = "conv_2",
        userId = "user_2",
        userName = "Jordan Lee",
        lastMessage = "Did you finish the assignment?",
        timestamp = "15 mins",
        isUserOnline = true,
        unreadCount = 0
    ),
    ConversationItem(
        id = "conv_3",
        userId = "user_3",
        userName = "Casey Williams",
        lastMessage = "See you at the event tonight!",
        timestamp = "1 hour",
        isUserOnline = false,
        unreadCount = 3
    ),
    ConversationItem(
        id = "conv_4",
        userId = "user_4",
        userName = "Taylor Chen",
        lastMessage = "Thanks for helping me with the project",
        timestamp = "Yesterday",
        isUserOnline = false,
        unreadCount = 0
    ),
    ConversationItem(
        id = "conv_5",
        userId = "user_5",
        userName = "Sam Jackson",
        lastMessage = "Want to grab coffee later?",
        timestamp = "Monday",
        isUserOnline = true,
        unreadCount = 2
    ),
    ConversationItem(
        id = "conv_6",
        userId = "user_6",
        userName = "Riley Martinez",
        lastMessage = "Sounds good! See you soon 👋",
        timestamp = "2 days",
        isUserOnline = false,
        unreadCount = 0
    )
)