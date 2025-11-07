package com.example.univibe.ui.utils

import androidx.compose.runtime.*
import kotlinx.coroutines.launch

/**
 * Simple pull-to-refresh state holder for common code
 * Note: PullToRefreshContainer rendering is Android-only
 */
@Composable
fun rememberSimplePullToRefreshState(
    onRefresh: suspend () -> Unit = {}
): SimplePullToRefreshState {
    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    
    val state = remember {
        SimplePullToRefreshState(
            onRefresh = {
                scope.launch {
                    isRefreshing = true
                    try {
                        onRefresh()
                    } finally {
                        isRefreshing = false
                    }
                }
            }
        )
    }
    
    state.isRefreshing = isRefreshing
    return state
}

/**
 * Simple state class for pull-to-refresh functionality
 */
class SimplePullToRefreshState(
    val onRefresh: () -> Unit = {}
) {
    var isRefreshing: Boolean = false
}
