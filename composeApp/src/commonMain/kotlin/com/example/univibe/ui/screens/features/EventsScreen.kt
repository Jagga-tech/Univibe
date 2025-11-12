package com.example.univibe.ui.screens.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.Spacing
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.data.mock.MockEvents
import com.example.univibe.domain.models.*
import com.example.univibe.ui.screens.detail.EventDetailScreen
import com.example.univibe.ui.screens.create.CreateEventScreen
import com.example.univibe.ui.components.*
import com.example.univibe.ui.utils.UISymbols
import com.example.univibe.ui.utils.OnBottomReached
import com.example.univibe.ui.utils.rememberPaginationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Events Screen - Browse and discover campus events with:
 * - Pull-to-refresh
 * - Infinite scroll pagination
 * - Skeleton loading states
 * - Error handling
 * - Empty state UI
 */
object EventsScreen : Screen {
    @Composable
    override fun Content() {
        EventsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    // State management
    var selectedFilter by remember { mutableStateOf(EventFilter.ALL) }
    var selectedCategory by remember { mutableStateOf<EventCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    
    // Pagination state
    val paginationState = rememberPaginationState(
        initialItems = MockEvents.getEventsByFilter(EventFilter.ALL).take(10)
    )
    
    // Pull-to-refresh state
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    
    val listState = rememberLazyListState()
    
    // Initial loading
    var isInitialLoading by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(500)
        isInitialLoading = false
    }
    
    // Pull-to-refresh placeholder - to be implemented
    LaunchedEffect(Unit) {
        // Future: Implement pull-to-refresh functionality
    }
    
    // Pagination handler
    listState.OnBottomReached {
        scope.launch {
            paginationState.loadNextPage { page ->
                delay(1000)
                var events = MockEvents.getEventsByFilter(selectedFilter)
                if (selectedCategory != null) {
                    events = events.filter { it.category == selectedCategory }
                }
                events.drop((page + 1) * 10).take(10)
            }
        }
    }
    
    // Compute filtered events
    val filteredEvents = remember(selectedFilter, selectedCategory, searchQuery) {
        var filtered = MockEvents.getEventsByFilter(selectedFilter)
        if (selectedCategory != null) {
            filtered = filtered.filter { it.category == selectedCategory }
        }
        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter { event ->
                event.title.contains(searchQuery, ignoreCase = true) ||
                event.description.contains(searchQuery, ignoreCase = true)
            }
        }
        filtered
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Events") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(PlatformIcons.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isSearching = !isSearching }) {
                        Icon(PlatformIcons.Search, contentDescription = "Search")
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
                    items(4) {
                        EventCardSkeleton()
                    }
                }
            } else if (paginationState.error != null) {
                ErrorState(
                    error = paginationState.error ?: "Failed to load events",
                    onRetry = {
                        scope.launch {
                            paginationState.retry { page ->
                                delay(1000)
                                var events = MockEvents.getEventsByFilter(selectedFilter)
                                if (selectedCategory != null) {
                                    events = events.filter { it.category == selectedCategory }
                                }
                                events.drop((page + 1) * 10).take(10)
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
                    // Search bar
                    if (isSearching) {
                        item {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search events...") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    
                    // Events list
                    if (paginationState.items.isEmpty()) {
                        item {
                            EmptyState(
                                title = "No events found",
                                description = "Try adjusting your filters",
                                icon = PlatformIcons.Article,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        items(paginationState.items) { event ->
                            EventCard(
                                event = event,
                                onClick = { navigator.push(EventDetailScreen(event.id)) }
                            )
                        }
                    }
                    
                    // Loading indicator
                    if (paginationState.isLoading && paginationState.items.isNotEmpty()) {
                        item {
                            PaginationLoadingIndicator()
                        }
                    }
                    
                    // End of list
                    if (!paginationState.hasMorePages && paginationState.items.isNotEmpty()) {
                        item {
                            EndOfListIndicator()
                        }
                    }
                }
            }
            
            // Pull-to-refresh indicator
            //             PullToRefreshContainer(
            //                 state = pullToRefreshState,
            //                 modifier = Modifier.align(Alignment.TopCenter)
        }
    }
}

@Composable
private fun EventCard(
    event: Event,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.location.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${event.currentAttendees} interested",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
