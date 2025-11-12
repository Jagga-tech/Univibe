package com.example.univibe.ui.screens.notifications

import com.example.univibe.ui.theme.PlatformIcons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.repository.mock.MockNotificationRepository
import com.example.univibe.domain.models.Notification
import com.example.univibe.domain.models.NotificationType
import com.example.univibe.ui.components.*
import com.example.univibe.domain.repository.NotificationRepository
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBackClick: () -> Unit = {}
) {
    val navigator = LocalNavigator.currentOrThrow
    val listState = rememberLazyListState()
    var isInitialLoading by remember { mutableStateOf(true) }
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val repository: NotificationRepository = remember { MockNotificationRepository() }
    var notifications by remember { mutableStateOf<List<Notification>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // Simulate initial load
        notifications = repository.getAll()
        delay(300)
        isInitialLoading = false
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(PlatformIcons.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Manual refresh hook (KMP-safe)
                        if (!isRefreshing) {
                            isRefreshing = true
                        }
                    }) {
                        Icon(PlatformIcons.Refresh, contentDescription = "Refresh")
                    }
                    IconButton(onClick = {}) {
                        Icon(PlatformIcons.MoreVert, contentDescription = "More")
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
                        NotificationSkeleton()
                    }
                }
            } else {
                // Handle refresh simulation
                LaunchedEffect(isRefreshing) {
                    if (isRefreshing) {
                        // Simulate a network refresh and re-read from repository
                        delay(600)
                        notifications = repository.getAll()
                        isRefreshing = false
                    }
                }

                val grouped = remember(notifications) { groupNotifications(notifications) }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (notifications.isEmpty()) {
                        item {
                            EmptyState(
                                title = "No notifications",
                                description = "You're all caught up!",
                                icon = PlatformIcons.NotificationsNone,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        grouped.today.takeIf { it.isNotEmpty() }?.let { todayList ->
                            item { SectionHeader("Today") }
                            items(items = todayList, key = { it.id }) { n ->
                                NotificationCard(
                                    notification = n,
                                    onClick = {
                                        if (!n.isRead) {
                                            scope.launch {
                                                repository.markAsRead(n.id)
                                                // Optimistically update local state
                                                notifications = notifications.map { 
                                                    if (it.id == n.id) it.copy(isRead = true) else it 
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                        }
                        grouped.thisWeek.takeIf { it.isNotEmpty() }?.let { weekList ->
                            item { SectionHeader("This week") }
                            items(items = weekList, key = { it.id }) { n ->
                                NotificationCard(notification = n)
                            }
                        }
                        grouped.earlier.takeIf { it.isNotEmpty() }?.let { oldList ->
                            item { SectionHeader("Earlier") }
                            items(items = oldList, key = { it.id }) { n ->
                                NotificationCard(notification = n)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun NotificationCard(notification: Notification, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                val iconVector: androidx.compose.ui.graphics.vector.ImageVector = when (notification.type) {
                    NotificationType.LIKE_POST, NotificationType.LIKE_COMMENT -> PlatformIcons.Favorite
                    NotificationType.COMMENT_ON_POST, NotificationType.REPLY_TO_COMMENT -> PlatformIcons.ChatBubble
                    NotificationType.FOLLOW, NotificationType.FOLLOW_ACCEPTED -> PlatformIcons.PersonAdd
                    else -> PlatformIcons.Notifications
                }
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = if (notification.isRead) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodySmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (!notification.isRead) {
                // Unread indicator dot
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {}
            }
        }
    }
}

// Group notifications into Today / This week / Earlier based on relative timestamps.
private data class NotificationGroups(
    val today: List<Notification>,
    val thisWeek: List<Notification>,
    val earlier: List<Notification>
)

private fun groupNotifications(list: List<Notification>): NotificationGroups {
    if (list.isEmpty()) return NotificationGroups(emptyList(), emptyList(), emptyList())
    val referenceNow = list.maxOf { it.createdAt }
    val oneDayMs = 24L * 60 * 60 * 1000
    val sevenDaysMs = 7L * oneDayMs
    val today = list.filter { referenceNow - it.createdAt < oneDayMs }
    val thisWeek = list.filter { referenceNow - it.createdAt in oneDayMs until sevenDaysMs }
    val earlier = list.filter { referenceNow - it.createdAt >= sevenDaysMs }
    return NotificationGroups(today, thisWeek, earlier)
}