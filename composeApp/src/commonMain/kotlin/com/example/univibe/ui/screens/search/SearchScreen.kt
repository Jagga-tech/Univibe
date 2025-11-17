package com.example.univibe.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.domain.models.Post
import com.example.univibe.domain.models.User
import com.example.univibe.data.mock.MockPosts
import com.example.univibe.data.mock.MockUsers
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.components.UserAvatar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Search Screen using UniVibe Design System
 * Comprehensive search with categories, filters, and professional results display
 */
object SearchScreen : Screen {
    @Composable
    override fun Content() {
        SearchScreenContent()
    }
}

@Composable
private fun SearchScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(SearchCategory.ALL) }
    var searchResults by remember { mutableStateOf<List<SearchResult>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    
    // Perform search when query changes
    LaunchedEffect(searchQuery, selectedCategory) {
        if (searchQuery.isNotBlank()) {
            isSearching = true
            delay(500) // Debounce search
            searchResults = performSearch(searchQuery, selectedCategory)
            isSearching = false
        } else {
            searchResults = emptyList()
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Custom search header
        SearchHeader(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onBack = { navigator.pop() },
            isSearching = isSearching
        )
        
        // Category filter chips
        SearchCategoryFilter(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            modifier = Modifier.padding(
                horizontal = UniVibeDesign.Spacing.md,
                vertical = UniVibeDesign.Spacing.sm
            )
        )
        
        // Search results
        when {
            searchQuery.isEmpty() -> {
                SearchSuggestions(
                    onSuggestionClick = { searchQuery = it },
                    modifier = Modifier.weight(1f)
                )
            }
            isSearching -> {
                UniVibeDesign.LoadingState(
                    modifier = Modifier.weight(1f),
                    message = "Searching..."
                )
            }
            searchResults.isEmpty() -> {
                UniVibeDesign.EmptyState(
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            Icons.Outlined.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    title = "No results found",
                    description = "Try adjusting your search terms or category"
                )
            }
            else -> {
                SearchResultsList(
                    results = searchResults,
                    onResultClick = { result ->
                        // TODO: Handle navigation to result details
                        when (result) {
                            is SearchResult.PostResult -> {
                                // Navigate to post detail
                            }
                            is SearchResult.UserResult -> {
                                // Navigate to user profile
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Search category enumeration
 */
enum class SearchCategory(val displayName: String, val icon: ImageVector) {
    ALL("All", Icons.Default.Search),
    POSTS("Posts", Icons.Outlined.Article),
    PEOPLE("People", Icons.Outlined.Person),
    EVENTS("Events", Icons.Outlined.Event),
    CLUBS("Clubs", Icons.Outlined.Groups)
}

/**
 * Search result sealed class for different result types
 */
sealed class SearchResult {
    data class PostResult(
        val post: Post,
        val matchedText: String
    ) : SearchResult()
    
    data class UserResult(
        val user: User,
        val matchType: String // "name", "username", "bio"
    ) : SearchResult()
}

/**
 * Search header with input and back navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchHeader(
    query: String,
    onQueryChange: (String) -> Unit,
    onBack: () -> Unit,
    isSearching: Boolean
) {
    Surface(
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UniVibeDesign.Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { 
                    Text(
                        "Search posts, people, events...",
                        style = UniVibeDesign.Text.bodySecondary()
                    ) 
                },
                leadingIcon = {
                    if (isSearching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(Icons.Default.Search, "Search")
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(Icons.Default.Clear, "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = UniVibeDesign.Cards.standardShape,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Category filter chips
 */
@Composable
private fun SearchCategoryFilter(
    selectedCategory: SearchCategory,
    onCategorySelected: (SearchCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.md)
    ) {
        items(SearchCategory.entries) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { 
                    Text(
                        text = category.displayName,
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        )
                    ) 
                },
                leadingIcon = {
                    Icon(
                        category.icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                shape = UniVibeDesign.Buttons.pillShape
            )
        }
    }
}

/**
 * Search suggestions when no query entered
 */
@Composable
private fun SearchSuggestions(
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val suggestions = listOf(
        "Study groups",
        "Campus events", 
        "Computer science",
        "Basketball club",
        "Career fair",
        "Research opportunities"
    )
    
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(UniVibeDesign.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
    ) {
        item {
            Text(
                text = "Popular searches",
                style = UniVibeDesign.Text.sectionTitle(),
                modifier = Modifier.padding(bottom = UniVibeDesign.Spacing.sm)
            )
        }
        
        items(suggestions) { suggestion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(UniVibeDesign.Cards.smallShape)
                    .clickable { onSuggestionClick(suggestion) }
                    .padding(UniVibeDesign.Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.TrendingUp,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = suggestion,
                    style = UniVibeDesign.Text.body(),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Default.NorthWest,
                    contentDescription = "Search",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Search results list
 */
@Composable
private fun SearchResultsList(
    results: List<SearchResult>,
    onResultClick: (SearchResult) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(UniVibeDesign.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
    ) {
        items(results) { result ->
            when (result) {
                is SearchResult.PostResult -> {
                    PostSearchResultCard(
                        result = result,
                        onClick = { onResultClick(result) }
                    )
                }
                is SearchResult.UserResult -> {
                    UserSearchResultCard(
                        result = result,
                        onClick = { onResultClick(result) }
                    )
                }
            }
        }
    }
}

/**
 * Post search result card
 */
@Composable
private fun PostSearchResultCard(
    result: SearchResult.PostResult,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            // Result type indicator
            Text(
                text = "POST",
                style = UniVibeDesign.Text.overline(),
                color = MaterialTheme.colorScheme.primary
            )
            
            // Author info
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(
                    imageUrl = null,
                    size = 32.dp
                )
                Text(
                    text = result.post.author.fullName,
                    style = UniVibeDesign.Text.cardTitle()
                )
            }
            
            // Post preview
            Text(
                text = result.post.content,
                style = UniVibeDesign.Text.body(),
                maxLines = 2
            )
            
            // Engagement stats
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
            ) {
                if (result.post.likeCount > 0) {
                    Text(
                        text = "${result.post.likeCount} ${if (result.post.likeCount == 1) "like" else "likes"}",
                        style = UniVibeDesign.Text.caption()
                    )
                }
                if (result.post.commentCount > 0) {
                    Text(
                        text = "${result.post.commentCount} ${if (result.post.commentCount == 1) "comment" else "comments"}",
                        style = UniVibeDesign.Text.caption()
                    )
                }
            }
        }
    }
}

/**
 * User search result card
 */
@Composable
private fun UserSearchResultCard(
    result: SearchResult.UserResult,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            // Result type indicator
            Text(
                text = "PERSON",
                style = UniVibeDesign.Text.overline(),
                color = MaterialTheme.colorScheme.secondary
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserAvatar(
                    imageUrl = null,
                    size = 48.dp
                )
                
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xxs)
                ) {
                    Text(
                        text = result.user.fullName,
                        style = UniVibeDesign.Text.cardTitle()
                    )
                    Text(
                        text = "@${result.user.username}",
                        style = UniVibeDesign.Text.caption()
                    )
                    Text(
                        text = "Matched ${result.matchType}",
                        style = UniVibeDesign.Text.caption().copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "View profile",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Mock search functionality
 */
private fun performSearch(query: String, category: SearchCategory): List<SearchResult> {
    val results = mutableListOf<SearchResult>()
    
    // Search posts
    if (category == SearchCategory.ALL || category == SearchCategory.POSTS) {
        MockPosts.posts
            .filter { it.content.contains(query, ignoreCase = true) }
            .take(5)
            .forEach { post ->
                results.add(
                    SearchResult.PostResult(
                        post = post,
                        matchedText = query
                    )
                )
            }
    }
    
    // Search users
    if (category == SearchCategory.ALL || category == SearchCategory.PEOPLE) {
        MockUsers.users
            .filter { 
                it.fullName.contains(query, ignoreCase = true) || 
                it.username.contains(query, ignoreCase = true)
            }
            .take(5)
            .forEach { user ->
                val matchType = when {
                    user.fullName.contains(query, ignoreCase = true) -> "name"
                    user.username.contains(query, ignoreCase = true) -> "username"
                    else -> "bio"
                }
                results.add(
                    SearchResult.UserResult(
                        user = user,
                        matchType = matchType
                    )
                )
            }
    }
    
    return results
}