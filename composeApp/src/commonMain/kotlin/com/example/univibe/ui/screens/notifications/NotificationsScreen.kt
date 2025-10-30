package com.example.univibe.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.OutlineButton
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a notification/activity item.
 *
 * @param id Unique identifier for the notification
 * @param type Type of notification (like, comment, follow, rsvp, join_group, etc.)
 * @param title Title/subject of the notification
 * @param description Brief description or message content
 * @param actorName Name of the person who triggered the notification
 * @param actorAvatarUrl Optional URL for the actor's avatar
 * @param timestamp When the notification occurred (e.g., "2 mins ago", "Yesterday")
 * @param isRead Whether the notification has been read
 * @param actionId Reference ID to the entity (post, group, event, etc.)
 * @param actionLabel Optional label for action button
 */
data class NotificationItem(
    val id: String,
    val type: String,
    val title: String,
    val description: String,
    val actorName: String,
    val actorAvatarUrl: String? = null,
    val timestamp: String,
    val isRead: Boolean = false,
    val actionId: String? = null,
    val actionLabel: String? = null
)

/**
 * Notifications screen composable - view and manage notifications and activity.
 * Displays notifications with filtering capabilities and read status tracking.
 *
 * @param notifications List of notifications to display
 * @param onNotificationClick Callback when a notification is clicked
 * @param onNotificationActionClick Callback when an action button is clicked
 * @param onSettingsClick Callback when settings button is clicked
 * @param onMarkAsRead Callback when notification is marked as read
 * @param onDismissNotification Callback when notification is dismissed
 * @param onBackClick Callback when back button is clicked
 */
@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem> = getSampleNotifications(),
    onNotificationClick: (String) -> Unit = {},
    onNotificationActionClick: (String, String) -> Unit = { _, _ -> },
    onSettingsClick: () -> Unit = {},
    onMarkAsRead: (String) -> Unit = {},
    onDismissNotification: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("All", "Unread", "Following")

    // Filter notifications based on selected tab
    val filteredNotifications = when (selectedTab) {
        0 -> notifications // All notifications
        1 -> notifications.filter { !it.isRead } // Only unread
        2 -> notifications.filter { it.type in listOf("follow", "user_post", "group_activity") } // Following activity
        else -> notifications
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with title and settings
        NotificationsHeader(
            onSettingsClick = onSettingsClick
        )

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                )
            }
        }

        // Notifications list
        NotificationsTabContent(
            notifications = filteredNotifications,
            onNotificationClick = onNotificationClick,
            onNotificationActionClick = onNotificationActionClick,
            onMarkAsRead = onMarkAsRead,
            onDismissNotification = onDismissNotification
        )
    }
}

/**
 * Header with title and settings button.
 */
@Composable
private fun NotificationsHeader(
    onSettingsClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Activity",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier.size(Dimensions.IconSize.large)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Tab content showing list of notifications.
 */
@Composable
private fun NotificationsTabContent(
    notifications: List<NotificationItem>,
    onNotificationClick: (String) -> Unit,
    onNotificationActionClick: (String, String) -> Unit,
    onMarkAsRead: (String) -> Unit,
    onDismissNotification: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (notifications.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No notifications yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            item {
                NotificationCardList(
                    notifications = notifications,
                    onNotificationClick = onNotificationClick,
                    onNotificationActionClick = onNotificationActionClick,
                    onMarkAsRead = onMarkAsRead,
                    onDismissNotification = onDismissNotification
                )
            }
        }

        // Bottom padding for navigation bar
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Individual notification card component.
 */
@Composable
fun NotificationCard(
    item: NotificationItem,
    onClick: (String) -> Unit = {},
    onActionClick: (String, String) -> Unit = { _, _ -> },
    onMarkAsRead: (String) -> Unit = {},
    onDismiss: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (!item.isRead) {
                    onMarkAsRead(item.id)
                }
                onClick(item.id)
            },
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (!item.isRead) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surface
                )
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.Top
        ) {
            // Actor avatar with notification type icon
            Box {
                UserAvatar(
                    imageUrl = item.actorAvatarUrl,
                    size = Dimensions.AvatarSize.medium
                )

                // Notification type badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 4.dp)
                        .size(24.dp)
                        .background(
                            color = getNotificationColor(item.type),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = getNotificationIcon(item.type),
                        contentDescription = item.type,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Notification content
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                // Title with actor name
                Text(
                    text = "${item.actorName} ${item.title}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Description
                if (item.description.isNotEmpty()) {
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Action button (if applicable)
                if (!item.actionLabel.isNullOrEmpty() && !item.actionId.isNullOrEmpty()) {
                    OutlineButton(
                        text = item.actionLabel,
                        onClick = { onActionClick(item.id, item.actionId) },
                        modifier = Modifier
                            .height(32.dp)
                            .padding(top = Dimensions.Spacing.xs),
                        textStyle = MaterialTheme.typography.labelSmall
                    )
                }
            }

            // Timestamp and dismiss button
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                IconButton(
                    onClick = { onDismiss(item.id) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = item.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Vertical list of notification cards.
 */
@Composable
fun NotificationCardList(
    notifications: List<NotificationItem> = emptyList(),
    onNotificationClick: (String) -> Unit = {},
    onNotificationActionClick: (String, String) -> Unit = { _, _ -> },
    onMarkAsRead: (String) -> Unit = {},
    onDismissNotification: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        notifications.forEach { notification ->
            NotificationCard(
                item = notification,
                onClick = onNotificationClick,
                onActionClick = onNotificationActionClick,
                onMarkAsRead = onMarkAsRead,
                onDismiss = onDismissNotification
            )
        }
    }
}

/**
 * Helper function to get the appropriate icon for a notification type.
 */
private fun getNotificationIcon(type: String): ImageVector {
    return when (type) {
        "like" -> Icons.Default.FavoriteBorder
        "follow" -> Icons.Default.PersonAdd
        "join_group" -> Icons.Default.GroupAdd
        "rsvp" -> Icons.Default.CheckCircle
        else -> Icons.Default.Favorite
    }
}

/**
 * Helper function to get the appropriate color for a notification type.
 */
private fun getNotificationColor(type: String): androidx.compose.ui.graphics.Color {
    return when (type) {
        "like" -> androidx.compose.ui.graphics.Color(0xFFE91E63) // Pink
        "follow" -> androidx.compose.ui.graphics.Color(0xFF2196F3) // Blue
        "join_group" -> androidx.compose.ui.graphics.Color(0xFF4CAF50) // Green
        "rsvp" -> androidx.compose.ui.graphics.Color(0xFF9C27B0) // Purple
        "user_post" -> androidx.compose.ui.graphics.Color(0xFFFF9800) // Orange
        "comment" -> androidx.compose.ui.graphics.Color(0xFF00BCD4) // Cyan
        else -> androidx.compose.ui.graphics.Color(0xFF757575) // Gray
    }
}

/**
 * Convenience function to create sample notifications for preview and testing.
 */
fun getSampleNotifications(): List<NotificationItem> = listOf(
    NotificationItem(
        id = "notif_1",
        type = "like",
        title = "liked your post",
        description = "This is an awesome study tip!",
        actorName = "Sarah Chen",
        timestamp = "2 mins ago",
        isRead = false,
        actionId = "post_1",
        actionLabel = "View Post"
    ),
    NotificationItem(
        id = "notif_2",
        type = "follow",
        title = "started following you",
        description = "",
        actorName = "Jordan Lee",
        timestamp = "15 mins ago",
        isRead = false,
        actionId = "user_2",
        actionLabel = "Follow Back"
    ),
    NotificationItem(
        id = "notif_3",
        type = "comment",
        title = "commented on your post",
        description = "Great idea! I'm interested in joining.",
        actorName = "Casey Williams",
        timestamp = "1 hour ago",
        isRead = false,
        actionId = "post_2",
        actionLabel = "Reply"
    ),
    NotificationItem(
        id = "notif_4",
        type = "join_group",
        title = "joined the group",
        description = "Computer Science Club now has 204 members",
        actorName = "Taylor Chen",
        timestamp = "3 hours ago",
        isRead = true,
        actionId = "group_1",
        actionLabel = "View Group"
    ),
    NotificationItem(
        id = "notif_5",
        type = "rsvp",
        title = "is attending the event",
        description = "Campus Career Fair 2024",
        actorName = "Sam Jackson",
        timestamp = "Yesterday",
        isRead = true,
        actionId = "event_1",
        actionLabel = "View Event"
    ),
    NotificationItem(
        id = "notif_6",
        type = "user_post",
        title = "posted something new",
        description = "Check out my latest study notes!",
        actorName = "Riley Martinez",
        timestamp = "2 days ago",
        isRead = true,
        actionId = "user_6",
        actionLabel = "View Post"
    ),
    NotificationItem(
        id = "notif_7",
        type = "like",
        title = "liked your comment",
        description = "",
        actorName = "Alex Morgan",
        timestamp = "3 days ago",
        isRead = true,
        actionId = "comment_1"
    ),
    NotificationItem(
        id = "notif_8",
        type = "follow",
        title = "started following you",
        description = "",
        actorName = "Morgan Lee",
        timestamp = "1 week ago",
        isRead = true,
        actionId = "user_8",
        actionLabel = "Follow Back"
    )
)