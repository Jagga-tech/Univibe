package com.example.univibe.ui.screens.features

import com.example.univibe.util.getCurrentTimeMillis
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockNotifications
import com.example.univibe.domain.models.Notification
import com.example.univibe.domain.models.NotificationType
import com.example.univibe.ui.components.EmptyState
import com.example.univibe.ui.components.LoadingView
import com.example.univibe.ui.components.social.NotificationItem
import com.example.univibe.ui.theme.Dimensions
import kotlinx.coroutines.delay

object NotificationsScreen : Screen {
    @Composable
    override fun Content() {
        NotificationsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationsScreenContent() {
    var selectedTab by remember { mutableStateOf(NotificationFilterTab.ALL) }
    var notifications by remember { mutableStateOf(MockNotifications.getAllNotifications()) }
    var isLoading by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var notificationToDelete by remember { mutableStateOf<Notification?>(null) }
    
    val navigator = LocalNavigator.currentOrThrow
    val unreadCount = notifications.count { !it.isRead }

    // Filter notifications based on selected tab
    val filteredNotifications = filterNotifications(notifications, selectedTab)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    // Mark all as read button (only show if there are unread notifications)
                    if (unreadCount > 0) {
                        IconButton(
                            onClick = {
                                MockNotifications.markAllAsRead()
                                notifications = MockNotifications.getAllNotifications()
                            }
                        ) {
                            Icon(Icons.Default.DoneAll, "Mark all as read")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row for filtering
            NotificationTabRow(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                unreadCount = unreadCount
            )

            // Content with refresh indicator
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading && filteredNotifications.isEmpty() -> {
                        LoadingView()
                    }
                    filteredNotifications.isEmpty() -> {
                        EmptyState(
                            message = if (selectedTab == NotificationFilterTab.UNREAD)
                                "All caught up! No unread notifications." else
                                "No notifications yet. When you get activity, it will show up here.",
                            icon = Icons.Default.NotificationsNone,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> {
                        NotificationListWithGrouping(
                            notifications = filteredNotifications,
                            isRefreshing = isRefreshing,
                            onRefresh = {
                                // Simulate refresh
                                isRefreshing = true
                            },
                            onNotificationClick = { notification ->
                                // Mark as read when clicked
                                if (!notification.isRead) {
                                    MockNotifications.markAsRead(notification.id)
                                    notifications = MockNotifications.getAllNotifications()
                                }
                                // Navigate based on notification type and actionUrl
                                handleNotificationNavigation(notification, navigator)
                            },
                            onDismissNotification = { notification ->
                                // Show confirmation dialog
                                notificationToDelete = notification
                                showDeleteDialog = true
                            }
                        )
                    }
                }

                // Refresh indicator overlay
                if (isRefreshing) {
                    LaunchedEffect(Unit) {
                        delay(1500) // Simulate network delay
                        notifications = MockNotifications.getAllNotifications()
                        isRefreshing = false
                    }
                }
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog && notificationToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            icon = { Icon(Icons.Default.Delete, "Delete") },
            title = { Text("Delete Notification") },
            text = { Text("Are you sure you want to delete this notification?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        notificationToDelete?.let {
                            MockNotifications.deleteNotification(it.id)
                            notifications = MockNotifications.getAllNotifications()
                        }
                        showDeleteDialog = false
                        notificationToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

enum class NotificationFilterTab(val displayName: String) {
    ALL("All"),
    UNREAD("Unread"),
    CONNECTIONS("Connections"),
    ACTIVITY("Activity"),
    REMINDERS("Reminders")
}

@Composable
private fun NotificationTabRow(
    selectedTab: NotificationFilterTab,
    onTabSelected: (NotificationFilterTab) -> Unit,
    unreadCount: Int
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTab.ordinal,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        edgePadding = 0.dp
    ) {
        NotificationFilterTab.entries.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                text = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(tab.displayName)
                        if (tab == NotificationFilterTab.UNREAD && unreadCount > 0) {
                            Badge {
                                Text(
                                    if (unreadCount > 99) "99+" else unreadCount.toString(),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                },
                modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun NotificationListWithGrouping(
    notifications: List<Notification>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onNotificationClick: (Notification) -> Unit,
    onDismissNotification: (Notification) -> Unit,
    modifier: Modifier = Modifier
) {
    // Group notifications by time
    val groupedNotifications = groupNotificationsByTimeHeader(notifications)

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        // Refresh indicator at the top
        if (isRefreshing) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.md),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }

        groupedNotifications.forEach { (timeGroup, notifs) ->
            // Time header
            item {
                Text(
                    text = timeGroup,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(
                        horizontal = Dimensions.Spacing.md,
                        vertical = Dimensions.Spacing.sm
                    )
                )
            }

            // Notifications for this time group
            items(notifs) { notification ->
                NotificationItem(
                    notification = notification,
                    onNotificationClick = { onNotificationClick(notification) },
                    onDismiss = { onDismissNotification(notification) }
                )
            }
        }

        // Bottom padding for scrolling
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Group notifications by time periods.
 * Returns a linked map to maintain order.
 */
private fun groupNotificationsByTimeHeader(
    notifications: List<Notification>
): Map<String, List<Notification>> {
    val now = getCurrentTimeMillis()
    val oneDayMs = 24 * 60 * 60 * 1000
    val result = linkedMapOf<String, MutableList<Notification>>()

    notifications.forEach { notification ->
        val diff = now - notification.createdAt
        val timeGroup = when {
            diff < oneDayMs -> "Today"
            diff < 2 * oneDayMs -> "Yesterday"
            diff < 7 * oneDayMs -> "This Week"
            else -> "Earlier"
        }

        result.getOrPut(timeGroup) { mutableListOf() }.add(notification)
    }

    return result
}

/**
 * Filter notifications based on the selected tab.
 */
private fun filterNotifications(
    notifications: List<Notification>,
    tab: NotificationFilterTab
): List<Notification> {
    return when (tab) {
        NotificationFilterTab.ALL -> notifications
        NotificationFilterTab.UNREAD -> notifications.filter { !it.isRead }
        NotificationFilterTab.CONNECTIONS -> notifications.filter {
            it.type in listOf(
                NotificationType.FOLLOW,
                NotificationType.FOLLOW_ACCEPTED
            )
        }
        NotificationFilterTab.ACTIVITY -> notifications.filter {
            it.type in listOf(
                NotificationType.LIKE_POST,
                NotificationType.LIKE_COMMENT,
                NotificationType.COMMENT_ON_POST,
                NotificationType.REPLY_TO_COMMENT,
                NotificationType.MENTION,
                NotificationType.POST_SHARED
            )
        }
        NotificationFilterTab.REMINDERS -> notifications.filter {
            it.type == NotificationType.ACHIEVEMENT_UNLOCKED ||
            it.type == NotificationType.MESSAGE
        }
    }
}

/**
 * Handle notification navigation based on type and actionUrl.
 */
private fun handleNotificationNavigation(
    notification: Notification,
    navigator: cafe.adriel.voyager.navigator.Navigator
) {
    // Extract the path from actionUrl and navigate
    // Example: "post/123" -> navigate to PostDetailScreen with id 123
    notification.actionUrl?.let { url ->
        val parts = url.split("/")
        if (parts.size >= 2) {
            when (parts[0]) {
                "post" -> {
                    // Navigate to post detail
                    // navigator.push(PostDetailScreen(parts[1]))
                }
                "profile" -> {
                    // Navigate to user profile
                    // navigator.push(UserProfileScreen(parts[1]))
                }
                "messages" -> {
                    // Navigate to conversation
                    // navigator.push(ConversationScreen(parts[1]))
                }
                "comment" -> {
                    // Navigate to post with comment highlighted
                    // navigator.push(PostDetailScreen(parts[1]))
                }
            }
        }
    }
}
