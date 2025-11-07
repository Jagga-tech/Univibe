package com.example.univibe.ui.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*

/**
 * Detects when user scrolls near the end of a list to trigger pagination
 *
 * @param buffer Number of items from bottom to trigger load (default: 3)
 * @param onLoadMore Callback when user scrolls near bottom
 */
@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 3,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false
            
            val totalItems = layoutInfo.totalItemsCount
            lastVisibleItem.index >= totalItems - buffer
        }
    }
    
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadMore()
        }
    }
}

/**
 * State holder for paginated data with loading, error, and pagination states
 *
 * @param T Type of items in the paginated list
 * @param initialItems Initial items to load (default: empty)
 */
class PaginationState<T>(
    initialItems: List<T> = emptyList()
) {
    var items by mutableStateOf(initialItems)
        private set
    
    var isLoading by mutableStateOf(false)
        private set
    
    var hasMorePages by mutableStateOf(true)
        private set
    
    var currentPage by mutableStateOf(0)
        private set
    
    var error by mutableStateOf<String?>(null)
        private set
    
    /**
     * Load next page of items
     *
     * @param loader Suspend function that loads items for a given page
     */
    suspend fun loadNextPage(
        loader: suspend (page: Int) -> List<T>
    ) {
        if (isLoading || !hasMorePages) return
        
        isLoading = true
        error = null
        try {
            val newItems = loader(currentPage)
            items = items + newItems
            currentPage++
            hasMorePages = newItems.isNotEmpty()
        } catch (e: Exception) {
            error = e.message ?: "Unknown error occurred"
        } finally {
            isLoading = false
        }
    }
    
    /**
     * Refresh the list with new items (resets pagination)
     *
     * @param newItems Items to refresh with
     */
    fun refresh(newItems: List<T>) {
        items = newItems
        currentPage = 0
        hasMorePages = true
        error = null
    }
    
    /**
     * Clear all data
     */
    fun clear() {
        items = emptyList()
        currentPage = 0
        hasMorePages = true
        error = null
        isLoading = false
    }
    
    /**
     * Retry loading after an error
     */
    suspend fun retry(loader: suspend (page: Int) -> List<T>) {
        loadNextPage(loader)
    }
}

/**
 * Composable state holder for paginated data with lifecycle awareness
 *
 * Usage:
 * ```
 * val paginationState = rememberPaginationState(
 *     initialItems = MockEvents.events.take(10)
 * )
 * ```
 */
@Composable
fun <T> rememberPaginationState(
    initialItems: List<T> = emptyList()
): PaginationState<T> {
    return remember { PaginationState(initialItems) }
}
