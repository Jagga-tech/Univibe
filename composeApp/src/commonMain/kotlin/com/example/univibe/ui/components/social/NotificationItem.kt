package com.example.univibe.ui.components.social

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

@Composable
fun NotificationItem(
    notification: Notification,
    onNotificationClick: (Notification) -> Unit,
    onDismiss: (Notification) -> Unit,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
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
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = formatNotificationTime(notification.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Close button
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
    }
    
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
    )
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
            text = user.fullName.first().toString().uppercase(),
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
    val currentTime = System.currentTimeMillis()
    val differenceInMillis = currentTime - createdAt
    
    return when {
        differenceInMillis < 60000 -> "now"
        differenceInMillis < 3600000 -> "${differenceInMillis / 60000}m ago"
        differenceInMillis < 86400000 -> "${differenceInMillis / 3600000}h ago"
        differenceInMillis < 604800000 -> "${differenceInMillis / 86400000}d ago"
        else -> "${differenceInMillis / 604800000}w ago"
    }
}