package com.example.univibe.ui.screens.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockNotifications
import com.example.univibe.domain.models.Notification
import com.example.univibe.ui.components.*
import com.example.univibe.ui.utils.OnBottomReached
import com.example.univibe.ui.utils.rememberPaginationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object NotificationsScreen : Screen {
    @Composable
    override fun Content() {
        NotificationsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    val paginationState = rememberPaginationState(
        initialItems = MockNotifications.notifications.take(15)
    )
    
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    val listState = rememberLazyListState()
    var isInitialLoading by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(500)
        isInitialLoading = false
    }
    
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            isRefreshing = true
            delay(1000)
            paginationState.refresh(MockNotifications.notifications.take(15))
            isRefreshing = false
            pullToRefreshState.endRefresh()
        }
    }
    
    listState.OnBottomReached {
        scope.launch {
            paginationState.loadNextPage { page ->
                delay(1000)
                MockNotifications.notifications.drop((page + 1) * 15).take(15)
            }
        }
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
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
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
            } else if (paginationState.error != null) {
                ErrorState(
                    error = paginationState.error ?: "Failed to load notifications",
                    onRetry = {
                        scope.launch {
                            paginationState.retry { page ->
                                delay(1000)
                                MockNotifications.notifications.drop((page + 1) * 15).take(15)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (paginationState.items.isEmpty()) {
                        item {
                            EmptyState(
                                title = "No notifications",
                                description = "You're all caught up!",
                                icon = Icons.Default.NotificationsNone,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        items(paginationState.items) { notification ->
                            NotificationCard(notification)
                        }
                    }
                    
                    if (paginationState.isLoading && paginationState.items.isNotEmpty()) {
                        item {
                            PaginationLoadingIndicator()
                        }
                    }
                    
                    if (!paginationState.hasMorePages && paginationState.items.isNotEmpty()) {
                        item {
                            EndOfListIndicator()
                        }
                    }
                }
            }
            
            //             PullToRefreshContainer(
            //                 state = pullToRefreshState,
            //                 modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun NotificationCard(notification: Notification) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                Icon(
                    imageVector = when {
                        notification.type.contains("like", ignoreCase = true) -> Icons.Default.Favorite
                        notification.type.contains("comment", ignoreCase = true) -> Icons.Default.ChatBubble
                        notification.type.contains("follow", ignoreCase = true) -> Icons.Default.PersonAdd
                        else -> Icons.Default.Notifications
                    },
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.bodySmall,
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberPullToRefreshState(): androidx.compose.material3.pulltorefresh.PullToRefreshState {
    return androidx.compose.material3.pulltorefresh.rememberPullToRefreshState()
}
