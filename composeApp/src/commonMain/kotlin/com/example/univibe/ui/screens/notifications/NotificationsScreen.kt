package com.example.univibe.ui.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.repository.mock.MockNotificationRepository
import com.example.univibe.domain.models.Notification
import com.example.univibe.domain.models.NotificationType
import com.example.univibe.domain.repository.NotificationRepository
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.ListScreen
import com.example.univibe.ui.templates.ListScreenConfig
import com.example.univibe.ui.components.UserAvatar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Notifications Screen using UniVibe Design System
 * Professional notification center with grouping, actions, and comprehensive management
 */
@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit = {}
) {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf(NotificationFilter.ALL) }
    
    val repository: NotificationRepository = remember { MockNotificationRepository() }
    
    // Load notifications
    LaunchedEffect(Unit) {
        delay(300)
        notifications = repository.getAll()
        isLoading = false
    }
    
    val filteredNotifications = remember(notifications, selectedFilter) {
        when (selectedFilter) {
            NotificationFilter.ALL -> notifications
            NotificationFilter.UNREAD -> notifications.filter { !it.isRead }
            NotificationFilter.MENTIONS -> notifications.filter { 
                it.type in listOf(NotificationType.COMMENT_ON_POST, NotificationType.REPLY_TO_COMMENT)
            }
            NotificationFilter.FOLLOWS -> notifications.filter { 
                it.type in listOf(NotificationType.FOLLOW, NotificationFilter.FOLLOW_ACCEPTED)
            }
            NotificationFilter.LIKES -> notifications.filter { 
                it.type in listOf(NotificationType.LIKE_POST, NotificationType.LIKE_COMMENT)
            }
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Custom header with actions
        NotificationHeader(
            unreadCount = notifications.count { !it.isRead },
            onBack = { navigator.pop() },
            onMarkAllRead = {
                scope.launch {
                    notifications.filter { !it.isRead }.forEach { notification ->
                        repository.markAsRead(notification.id)
                    }
                    notifications = notifications.map { it.copy(isRead = true) }
                }
            },
            onSettings = {
                // TODO: Navigate to notification settings
            }
        )
        
        // Filter tabs
        NotificationFilterTabs(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
        )
        
        // Notifications content
        if (isLoading) {
            UniVibeDesign.LoadingState(
                modifier = Modifier.weight(1f),
                message = "Loading notifications..."
            )
        } else if (filteredNotifications.isEmpty()) {
            NotificationEmptyState(
                filter = selectedFilter,
                modifier = Modifier.weight(1f)
            )
        } else {
            NotificationsList(
                notifications = filteredNotifications,
                onNotificationClick = { notification ->
                    if (!notification.isRead) {
                        scope.launch {
                            repository.markAsRead(notification.id)
                            notifications = notifications.map { 
                                if (it.id == notification.id) it.copy(isRead = true) else it 
                            }
                        }
                    }
                    // TODO: Handle notification navigation
                },
                onNotificationAction = { notification, action ->
                    scope.launch {
                        when (action) {
                            NotificationAction.MARK_READ -> {
                                repository.markAsRead(notification.id)
                                notifications = notifications.map { 
                                    if (it.id == notification.id) it.copy(isRead = true) else it 
                                }
                            }
                            NotificationAction.DELETE -> {
                                // TODO: Delete notification
                                notifications = notifications.filter { it.id != notification.id }
                            }
                            NotificationAction.MUTE -> {
                                // TODO: Mute notification type
                            }
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Notification filter types
 */
enum class NotificationFilter(val displayName: String, val icon: ImageVector) {
    ALL("All", Icons.Default.Notifications),
    UNREAD("Unread", Icons.Default.FiberManualRecord),
    MENTIONS("Mentions", Icons.Default.AlternateEmail),
    FOLLOWS("Follows", Icons.Default.PersonAdd),
    LIKES("Likes", Icons.Default.Favorite);
    
    companion object {
        val FOLLOW_ACCEPTED = NotificationType.FOLLOW_ACCEPTED
    }
}

/**
 * Notification actions
 */
enum class NotificationAction {
    MARK_READ,
    DELETE,
    MUTE
}

/**
 * Custom notification header with actions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationHeader(
    unreadCount: Int,
    onBack: () -> Unit,
    onMarkAllRead: () -> Unit,
    onSettings: () -> Unit
) {
    Surface(
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UniVibeDesign.Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = "Notifications",
                    style = UniVibeDesign.Text.screenTitle()
                )
                
                if (unreadCount > 0) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (unreadCount > 99) "99+" else unreadCount.toString(),
                                style = UniVibeDesign.Text.caption().copy(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
            ) {
                if (unreadCount > 0) {
                    TextButton(onClick = onMarkAllRead) {
                        Text(
                            "Mark all read",
                            style = UniVibeDesign.Text.caption().copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
                
                IconButton(onClick = onSettings) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Notification filter tabs
 */
@Composable
private fun NotificationFilterTabs(
    selectedFilter: NotificationFilter,
    onFilterSelected: (NotificationFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(vertical = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        items(NotificationFilter.entries) { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { 
                    Text(
                        text = filter.displayName,
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        )
                    ) 
                },
                leadingIcon = {
                    Icon(
                        filter.icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                shape = UniVibeDesign.Buttons.pillShape
            )
        }
    }
}

/**
 * Notifications list with grouping
 */
@Composable
private fun NotificationsList(
    notifications: List<Notification>,
    onNotificationClick: (Notification) -> Unit,
    onNotificationAction: (Notification, NotificationAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val grouped = remember(notifications) { groupNotifications(notifications) }
    
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(UniVibeDesign.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
    ) {
        // Today section
        grouped.today.takeIf { it.isNotEmpty() }?.let { todayList ->
            item {
                NotificationSectionHeader("Today")
            }
            items(todayList) { notification ->
                NotificationCard(
                    notification = notification,
                    onClick = { onNotificationClick(notification) },
                    onAction = { action -> onNotificationAction(notification, action) }
                )
            }
        }
        
        // This week section
        grouped.thisWeek.takeIf { it.isNotEmpty() }?.let { weekList ->
            item {
                NotificationSectionHeader("This Week")
            }
            items(weekList) { notification ->
                NotificationCard(
                    notification = notification,
                    onClick = { onNotificationClick(notification) },
                    onAction = { action -> onNotificationAction(notification, action) }
                )
            }
        }
        
        // Earlier section
        grouped.earlier.takeIf { it.isNotEmpty() }?.let { earlierList ->
            item {
                NotificationSectionHeader("Earlier")
            }
            items(earlierList) { notification ->
                NotificationCard(
                    notification = notification,
                    onClick = { onNotificationClick(notification) },
                    onAction = { action -> onNotificationAction(notification, action) }
                )
            }
        }
    }
}

/**
 * Section header for notification groups
 */
@Composable
private fun NotificationSectionHeader(text: String) {
    Text(
        text = text,
        style = UniVibeDesign.Text.sectionTitle(),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(
            vertical = UniVibeDesign.Spacing.sm,
            horizontal = UniVibeDesign.Spacing.xs
        )
    )
}

/**
 * Individual notification card
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationCard(
    notification: Notification,
    onClick: () -> Unit,
    onAction: (NotificationAction) -> Unit
) {
    var showActions by remember { mutableStateOf(false) }
    
    UniVibeDesign.StandardCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!notification.isRead) {
                    Modifier.background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                        shape = UniVibeDesign.Cards.standardShape
                    )
                } else Modifier
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
            verticalAlignment = Alignment.Top
        ) {
            // Notification icon
            NotificationIcon(
                type = notification.type,
                isRead = notification.isRead
            )
            
            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
            ) {
                Text(
                    text = notification.title,
                    style = UniVibeDesign.Text.cardTitle().copy(
                        fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = notification.message,
                    style = UniVibeDesign.Text.bodySecondary(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = formatRelativeTime(notification.createdAt),
                    style = UniVibeDesign.Text.caption()
                )
            }
            
            // Action menu
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
            ) {
                if (!notification.isRead) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
                
                IconButton(
                    onClick = { showActions = !showActions },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Actions",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Action buttons (when expanded)
        if (showActions) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = UniVibeDesign.Spacing.sm),
                color = MaterialTheme.colorScheme.outlineVariant
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (!notification.isRead) {
                    TextButton(onClick = { 
                        onAction(NotificationAction.MARK_READ)
                        showActions = false
                    }) {
                        Text("Mark Read")
                    }
                }
                
                TextButton(onClick = { 
                    onAction(NotificationAction.MUTE)
                    showActions = false
                }) {
                    Text("Mute")
                }
                
                TextButton(onClick = { 
                    onAction(NotificationAction.DELETE)
                    showActions = false
                }) {
                    Text("Delete")
                }
            }
        }
    }
}

/**
 * Notification type icon
 */
@Composable
private fun NotificationIcon(
    type: NotificationType,
    isRead: Boolean
) {
    val (icon, containerColor) = when (type) {
        NotificationType.LIKE_POST, NotificationType.LIKE_COMMENT -> 
            Icons.Default.Favorite to MaterialTheme.colorScheme.error
        NotificationType.COMMENT_ON_POST, NotificationType.REPLY_TO_COMMENT -> 
            Icons.Default.Comment to MaterialTheme.colorScheme.primary
        NotificationType.FOLLOW, NotificationType.FOLLOW_ACCEPTED -> 
            Icons.Default.PersonAdd to MaterialTheme.colorScheme.secondary
        else -> 
            Icons.Default.Notifications to MaterialTheme.colorScheme.tertiary
    }
    
    Surface(
        shape = CircleShape,
        color = containerColor.copy(alpha = if (isRead) 0.3f else 0.8f),
        modifier = Modifier.size(48.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

/**
 * Empty state for different filters
 */
@Composable
private fun NotificationEmptyState(
    filter: NotificationFilter,
    modifier: Modifier = Modifier
) {
    val (icon, title, description) = when (filter) {
        NotificationFilter.ALL -> Triple(
            Icons.Outlined.Notifications,
            "No notifications yet",
            "You'll see notifications about likes, comments, and follows here"
        )
        NotificationFilter.UNREAD -> Triple(
            Icons.Outlined.DoneAll,
            "All caught up!",
            "No unread notifications"
        )
        NotificationFilter.MENTIONS -> Triple(
            Icons.Outlined.AlternateEmail,
            "No mentions",
            "When someone mentions you in a comment, you'll see it here"
        )
        NotificationFilter.FOLLOWS -> Triple(
            Icons.Outlined.PersonAdd,
            "No follow notifications",
            "New followers and follow requests will appear here"
        )
        NotificationFilter.LIKES -> Triple(
            Icons.Outlined.FavoriteBorder,
            "No likes yet",
            "Likes on your posts and comments will show up here"
        )
    }
    
    UniVibeDesign.EmptyState(
        modifier = modifier,
        icon = {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = title,
        description = description
    )
}

/**
 * Group notifications by time periods
 */
private data class NotificationGroups(
    val today: List<Notification>,
    val thisWeek: List<Notification>,
    val earlier: List<Notification>
)

private fun groupNotifications(notifications: List<Notification>): NotificationGroups {
    if (notifications.isEmpty()) return NotificationGroups(emptyList(), emptyList(), emptyList())
    
    val now = System.currentTimeMillis()
    val oneDayMs = 24L * 60 * 60 * 1000
    val sevenDaysMs = 7L * oneDayMs
    
    val today = notifications.filter { now - it.createdAt < oneDayMs }
    val thisWeek = notifications.filter { now - it.createdAt in oneDayMs until sevenDaysMs }
    val earlier = notifications.filter { now - it.createdAt >= sevenDaysMs }
    
    return NotificationGroups(today, thisWeek, earlier)
}

/**
 * Format relative time (simplified)
 */
private fun formatRelativeTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val minutes = diff / (60 * 1000)
    val hours = diff / (60 * 60 * 1000)
    val days = diff / (24 * 60 * 60 * 1000)
    
    return when {
        minutes < 1 -> "Just now"
        minutes < 60 -> "${minutes}m ago"
        hours < 24 -> "${hours}h ago"
        days < 7 -> "${days}d ago"
        else -> "1w+ ago"
    }
}