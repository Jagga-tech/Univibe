package com.example.univibe.ui.screens.features

import androidx.compose.foundation.background
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
import com.example.univibe.data.mock.MockDepartments
import com.example.univibe.domain.models.Department
import com.example.univibe.ui.animations.fadeInAnimation
import com.example.univibe.ui.animations.scaleInAnimation
import com.example.univibe.ui.animations.slideInFromBottom
import com.example.univibe.ui.components.*
import com.example.univibe.ui.utils.OnBottomReached
import com.example.univibe.ui.utils.rememberPaginationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DepartmentsScreen : Screen {
    @Composable
    override fun Content() {
        DepartmentsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepartmentsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    val paginationState = rememberPaginationState(
        initialItems = MockDepartments.departments.take(10)
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
            paginationState.refresh(MockDepartments.departments.take(10))
            isRefreshing = false
            pullToRefreshState.endRefresh()
        }
    }
    
    listState.OnBottomReached {
        scope.launch {
            paginationState.loadNextPage { page ->
                delay(1000)
                MockDepartments.departments.drop((page + 1) * 10).take(10)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Departments",
                        modifier = Modifier.fadeInAnimation()
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            contentDescription = "Back",
                            modifier = Modifier.scaleInAnimation()
                        )
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
                        .padding(16.dp)
                        .fadeInAnimation(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(4) {
                        EventCardSkeleton()
                    }
                }
            } else if (paginationState.error != null) {
                ErrorState(
                    error = paginationState.error ?: "Failed to load departments",
                    onRetry = {
                        scope.launch {
                            paginationState.retry { page ->
                                delay(1000)
                                MockDepartments.departments.drop((page + 1) * 10).take(10)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .scaleInAnimation()
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .fadeInAnimation(),
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (paginationState.items.isEmpty()) {
                        item {
                            EmptyState(
                                title = "No departments",
                                description = "No departments to display",
                                icon = Icons.Default.SchoolOff,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .scaleInAnimation()
                            )
                        }
                    } else {
                        items(
                            items = paginationState.items,
                            key = { it.id }
                        ) { dept ->
                            DepartmentCardWithAnimation(dept)
                        }
                    }
                    
                    if (paginationState.isLoading && paginationState.items.isNotEmpty()) {
                        item {
                            PaginationLoadingIndicator(
                                modifier = Modifier.slideInFromBottom()
                            )
                        }
                    }
                    
                    if (!paginationState.hasMorePages && paginationState.items.isNotEmpty()) {
                        item {
                            EndOfListIndicator(
                                modifier = Modifier.slideInFromBottom()
                            )
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

@Composable
private fun DepartmentCardWithAnimation(department: Department) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scaleInAnimation()
            .slideInFromBottom(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = department.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Chair: ${department.chairName}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = department.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberPullToRefreshState(): androidx.compose.material3.pulltorefresh.PullToRefreshState {
    return androidx.compose.material3.pulltorefresh.rememberPullToRefreshState()
}
