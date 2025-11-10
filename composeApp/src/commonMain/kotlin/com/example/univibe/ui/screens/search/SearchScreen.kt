package com.example.univibe.ui.screens.search

import com.example.univibe.ui.theme.PlatformIcons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockPosts
import com.example.univibe.ui.components.*
import com.example.univibe.ui.utils.OnBottomReached
import com.example.univibe.ui.utils.rememberPaginationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object SearchScreen : Screen {
    @Composable
    override fun Content() {
        SearchScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var searchQuery by remember { mutableStateOf("") }
    val paginationState = rememberPaginationState(
        initialItems = if (searchQuery.isEmpty()) emptyList() else MockPosts.posts.take(10)
    )
    
    var isRefreshing by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    var isInitialLoading by remember { mutableStateOf(false) }
    
    listState.OnBottomReached {
        scope.launch {
            paginationState.loadNextPage { page ->
                delay(1000)
                MockPosts.posts.drop((page + 1) * 10).take(10)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(PlatformIcons.ArrowBack, contentDescription = "Back")
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search posts, people, topics...") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(PlatformIcons.Search, null) },
                        singleLine = true
                    )
                }
                
                if (searchQuery.isEmpty()) {
                    item {
                        EmptyState(
                            title = "Search for content",
                            description = "Enter keywords to find posts, people, and more",
                            icon = PlatformIcons.SearchOff,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    if (paginationState.items.isEmpty() && !isInitialLoading) {
                        item {
                            EmptyState(
                                title = "No results found",
                                description = "Try different keywords",
                                icon = PlatformIcons.SearchOff,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        items(paginationState.items) { post ->
                            // Render search result items
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "Search Result",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberPullToRefreshState(): androidx.compose.material3.pulltorefresh.PullToRefreshState {
    return androidx.compose.material3.pulltorefresh.rememberPullToRefreshState()
}
