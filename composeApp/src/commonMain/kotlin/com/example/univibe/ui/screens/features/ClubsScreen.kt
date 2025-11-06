package com.example.univibe.ui.screens.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.univibe.data.mock.MockClubs
import com.example.univibe.domain.models.Club
import com.example.univibe.ui.components.*
import com.example.univibe.ui.utils.OnBottomReached
import com.example.univibe.ui.utils.rememberPaginationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ClubsScreen : Screen {
    @Composable
    override fun Content() {
        ClubsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClubsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    val paginationState = rememberPaginationState(
        initialItems = MockClubs.clubs.take(10)
    )
    
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    val listState = rememberLazyListState()
    var isInitialLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }
    
    LaunchedEffect(Unit) {
        delay(500)
        isInitialLoading = false
    }
    
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            isRefreshing = true
            delay(1000)
            paginationState.refresh(MockClubs.clubs.take(10))
            isRefreshing = false
            pullToRefreshState.endRefresh()
        }
    }
    
    listState.OnBottomReached {
        scope.launch {
            paginationState.loadNextPage { page ->
                delay(1000)
                MockClubs.clubs.drop((page + 1) * 10).take(10)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clubs") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                    items(4) {
                        EventCardSkeleton()
                    }
                }
            } else if (paginationState.error != null) {
                ErrorState(
                    error = paginationState.error ?: "Failed to load clubs",
                    onRetry = {
                        scope.launch {
                            paginationState.retry { page ->
                                delay(1000)
                                MockClubs.clubs.drop((page + 1) * 10).take(10)
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
                                title = "No clubs found",
                                description = "Join a club to get started",
                                icon = Icons.Default.Groups,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        items(paginationState.items) { club ->
                            ClubCard(club)
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
            
            PullToRefreshContainer(
                state = pullToRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
private fun ClubCard(club: Club) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = club.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${club.memberCount} members",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(32.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                Text("Join", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberPullToRefreshState(): androidx.compose.material3.pulltorefresh.PullToRefreshState {
    return androidx.compose.material3.pulltorefresh.rememberPullToRefreshState()
}
