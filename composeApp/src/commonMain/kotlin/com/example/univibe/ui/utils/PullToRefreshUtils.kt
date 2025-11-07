package com.example.univibe.ui.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.foundation.layout.Box
import kotlinx.coroutines.launch

/**
 * Creates a pull-to-refresh state with automatic handling
 *
 * @param onRefresh Callback when refresh is triggered
 * @return Pair of (pullToRefreshState, isPullRefreshing)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberPullToRefreshState(
    onRefresh: suspend () -> Unit
): Pair<androidx.compose.material3.pulltorefresh.PullToRefreshState, Boolean> {
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    
    LaunchedEffect(pullToRefreshState.isRefreshing) {
        if (pullToRefreshState.isRefreshing) {
            isRefreshing = true
            try {
                onRefresh()
            } finally {
                isRefreshing = false
                pullToRefreshState.endRefresh()
            }
        }
    }
    
    return Pair(pullToRefreshState, isRefreshing)
}

/**
 * Wraps content with pull-to-refresh capability
 *
 * @param modifier Modifier for the box
 * @param pullToRefreshState The pull-to-refresh state
 * @param content Composable content to wrap
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshBox(
    modifier: Modifier = Modifier,
    pullToRefreshState: androidx.compose.material3.pulltorefresh.PullToRefreshState,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        content()
        
        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
