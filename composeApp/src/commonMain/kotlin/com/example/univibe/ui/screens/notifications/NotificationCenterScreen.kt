package com.example.univibe.ui.screens.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockNotifications
import com.example.univibe.domain.models.*
import com.example.univibe.ui.components.social.NotificationItem
import com.example.univibe.ui.screens.detail.PostDetailScreen
import com.example.univibe.ui.screens.detail.UserProfileScreen
import com.example.univibe.ui.theme.Dimensions

object NotificationCenterScreen : Screen {
    @Composable
    override fun Content() {
        NotificationCenterScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationCenterScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    var notifications by remember { mutableStateOf(MockNotifications.getAllNotifications()) }
    var showOnlyUnread by remember { mutableStateOf(false) }
    
    val displayedNotifications = if (showOnlyUnread) {
        notifications.filter { !it.isRead }
    } else {
        notifications
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            MockNotifications.clearAllNotifications()
                            notifications = MockNotifications.getAllNotifications()
                        }
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Clear all")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (displayedNotifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
                ) {
                    Text(
                        text = "No notifications",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (showOnlyUnread) {
                        Text(
                            text = "You're all caught up!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                item {
                    FilterChips(
                        showOnlyUnread = showOnlyUnread,
                        onFilterChange = { showOnlyUnread = it }
                    )
                }
                
                items(
                    items = displayedNotifications,
                    key = { it.id }
                ) { notification ->
                    NotificationItem(
                        notification = notification,
                        onNotificationClick = { clickedNotification ->
                            // Mark as read
                            MockNotifications.markAsRead(clickedNotification.id)
                            notifications = MockNotifications.getAllNotifications()
                            
                            // Handle action based on notification type
                            handleNotificationAction(clickedNotification, navigator)
                        },
                        onDismiss = { dismissedNotification ->
                            MockNotifications.deleteNotification(dismissedNotification.id)
                            notifications = MockNotifications.getAllNotifications()
                        }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
                }
            }
        }
    }
}

@Composable
private fun FilterChips(
    showOnlyUnread: Boolean,
    onFilterChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
    ) {
        FilterChip(
            selected = !showOnlyUnread,
            onClick = { onFilterChange(false) },
            label = { Text("All") }
        )
        
        FilterChip(
            selected = showOnlyUnread,
            onClick = { onFilterChange(true) },
            label = { Text("Unread") }
        )
    }
}

private fun handleNotificationAction(notification: Notification, navigator: cafe.adriel.voyager.navigator.Navigator) {
    when (notification.relatedType) {
        RelatedContentType.POST -> {
            notification.relatedId?.let { postId ->
                navigator.push(PostDetailScreen(postId))
            }
        }
        RelatedContentType.USER -> {
            notification.relatedId?.let { userId ->
                navigator.push(UserProfileScreen(userId))
            }
        }
        RelatedContentType.MESSAGE_THREAD -> {
            // TODO: Navigate to chat screen
        }
        else -> {
            // No action needed or custom handling
        }
    }
}