package com.example.univibe.ui.components.social

import com.example.univibe.util.getCurrentTimeMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.univibe.domain.models.*
import com.example.univibe.ui.theme.Dimensions

/**
 * Enhanced NotificationItem component that displays a single notification with:
 * - User avatar or notification type icon
 * - Title and message
 * - Timestamp
 * - Action buttons (for interactive notifications)
 * - Unread indicator
 * - Dismiss button
 */
@Composable
fun NotificationItem(
    notification: Notification,
    onNotificationClick: (Notification) -> Unit,
    onDismiss: (Notification) -> Unit,
    onActionClick: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (notification.isRead)
        MaterialTheme.colorScheme.surface
    else
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNotificationClick(notification) }
            .background(backgroundColor),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                verticalAlignment = Alignment.Top
            ) {
                // Avatar or Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    if (notification.fromUser != null) {
                        NotificationAvatar(notification.fromUser)
                    } else {
                        NotificationTypeIcon(notification.type)
                    }
                }

                // Content
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Title
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Message
                    if (notification.message.isNotEmpty()) {
                        Text(
                            text = notification.message,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )
                    }

                    // Timestamp
                    Text(
                        text = formatNotificationTime(notification.createdAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Dismiss button
                IconButton(
                    onClick = { onDismiss(notification) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Action buttons (for notifications that require user action)
            val actions = getNotificationActions(notification.type)
            if (actions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp), // Align with content, not avatar
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    actions.forEach { (actionLabel, actionId) ->
                        if (actions.indexOf(actionLabel to actionId) == 0) {
                            // Primary action button
                            Button(
                                onClick = { onActionClick?.invoke(actionId) },
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text(
                                    actionLabel,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        } else {
                            // Secondary action button
                            OutlinedButton(
                                onClick = { onActionClick?.invoke(actionId) },
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text(
                                    actionLabel,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }

            // Unread indicator dot (if unread)
            if (!notification.isRead) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .padding(start = Dimensions.Spacing.md)
                )
            }
        }
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
    )
}

/**
 * Get action buttons appropriate for each notification type.
 * Returns a list of pairs: (actionLabel, actionId)
 */
private fun getNotificationActions(type: NotificationType): List<Pair<String, String>> {
    return when (type) {
        NotificationType.FOLLOW -> listOf("Follow Back" to "follow_back")
        NotificationType.MESSAGE -> listOf("Reply" to "reply_message")
        NotificationType.LIKE_POST -> listOf("View Post" to "view_post")
        NotificationType.LIKE_COMMENT -> listOf("View Post" to "view_post")
        NotificationType.COMMENT_ON_POST -> listOf("Reply" to "reply_comment", "View Post" to "view_post")
        NotificationType.REPLY_TO_COMMENT -> listOf("Reply" to "reply_comment", "View Post" to "view_post")
        NotificationType.MENTION -> listOf("View Post" to "view_post")
        NotificationType.POST_SHARED -> listOf("View Post" to "view_post")
        NotificationType.FOLLOW_ACCEPTED -> listOf("View Profile" to "view_profile")
        NotificationType.ACHIEVEMENT_UNLOCKED -> listOf("Claim" to "claim_achievement")
        NotificationType.CUSTOM -> emptyList()
    }
}

@Composable
private fun NotificationAvatar(user: User) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = user.fullName.firstOrNull()?.toString()?.uppercase() ?: "?",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun NotificationTypeIcon(type: NotificationType) {
    val icon = when (type) {
        NotificationType.LIKE_POST, NotificationType.LIKE_COMMENT -> Icons.Default.Favorite
        NotificationType.COMMENT_ON_POST, NotificationType.REPLY_TO_COMMENT -> Icons.Default.ChatBubble
        NotificationType.FOLLOW, NotificationType.FOLLOW_ACCEPTED -> Icons.Default.PersonAdd
        NotificationType.MESSAGE -> Icons.Default.Mail
        NotificationType.MENTION -> Icons.Default.Tag
        NotificationType.POST_SHARED -> Icons.Default.Share
        NotificationType.ACHIEVEMENT_UNLOCKED -> Icons.Default.Star
        NotificationType.CUSTOM -> Icons.Default.Info
    }

    Icon(
        imageVector = icon,
        contentDescription = type.name,
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.onPrimaryContainer
    )
}

private fun formatNotificationTime(createdAt: Long): String {
    val currentTime = getCurrentTimeMillis()
    val differenceInMillis = currentTime - createdAt

    return when {
        differenceInMillis < 60000 -> "now"
        differenceInMillis < 3600000 -> "${differenceInMillis / 60000}m ago"
        differenceInMillis < 86400000 -> "${differenceInMillis / 3600000}h ago"
        differenceInMillis < 604800000 -> "${differenceInMillis / 86400000}d ago"
        else -> "${differenceInMillis / 604800000}w ago"
    }
}
