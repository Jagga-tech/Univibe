package com.example.univibe.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeTextField
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Discover screen composable - discover new content and communities on UniVibe.
 * Displays trending topics, categories, and content discovery.
 *
 * @param categories List of categories to display
 * @param trendingItems List of trending topics
 * @param onCategoryClick Callback when a category is clicked
 * @param onTrendingClick Callback when a trending item is clicked
 * @param onSearchClick Callback when search is performed
 * @param onFilterClick Callback when filter button is clicked
 * @param onBackClick Callback when back button is clicked
 */
@Composable
fun DiscoverScreen(
    categories: List<CategoryItem> = getDefaultCategories(),
    trendingItems: List<TrendingItem> = getSampleTrendingItems(),
    onCategoryClick: (String) -> Unit = {},
    onTrendingClick: (String) -> Unit = {},
    onSearchClick: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with search and filter
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(Dimensions.Spacing.md)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimensions.Spacing.md),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Discover",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = onFilterClick,
                        modifier = Modifier.size(Dimensions.IconSize.large)
                    ) {
                        TextIcon(
                            symbol = UISymbols.FILTER,
                            contentDescription = "Filter",
                            fontSize = 20,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Search bar
                UniVibeTextField(
                    value = searchQuery,
                    onValueChange = { 
                        searchQuery = it
                        if (it.isNotEmpty()) {
                            onSearchClick(it)
                        }
                    },
                    placeholder = "Search categories, topics...",
                    leadingIcon = UISymbols.SEARCH,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Categories section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimensions.Spacing.lg)
            ) {
                Text(
                    text = "Browse Categories",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(
                        start = Dimensions.Spacing.md,
                        end = Dimensions.Spacing.md,
                        bottom = Dimensions.Spacing.md
                    )
                )

                CategoryGrid(
                    categories = categories,
                    onCategoryClick = onCategoryClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Divider
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = Dimensions.Spacing.md)
            )
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }

        // Trending section
        item {
            Text(
                text = "Trending Now",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    start = Dimensions.Spacing.md,
                    end = Dimensions.Spacing.md,
                    bottom = Dimensions.Spacing.md
                )
            )
        }

        // Trending items
        if (trendingItems.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No trending topics right now",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            items(trendingItems.size) { index ->
                val item = trendingItems[index]
                TrendingCard(
                    item = item,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onTrendingClick(item.id) }
                )
                
                // Add spacing between cards except after last one
                if (index < trendingItems.size - 1) {
                    Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
                }
            }
        }

        // Bottom padding for navigation bar
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Convenience function to create sample/mock trending items specifically for trending lists.
 * These items are displayed in the "Trending Now" section.
 */
fun getSampleTrendingItems(): List<TrendingItem> = listOf(
    TrendingItem(
        id = "trending_1",
        title = "#MidtermPrep",
        category = "Academics",
        postCount = 2504,
        momentum = "↑ 65%"
    ),
    TrendingItem(
        id = "trending_2",
        title = "#FriendsWhoStudyTogether",
        category = "Community",
        postCount = 1856,
        momentum = "↑ 52%"
    ),
    TrendingItem(
        id = "trending_3",
        title = "#CampusBuzz",
        category = "Social",
        postCount = 3241,
        momentum = "↑ 78%"
    ),
    TrendingItem(
        id = "trending_4",
        title = "#NotesForSale",
        category = "Academics",
        postCount = 1167,
        momentum = "↑ 38%"
    ),
    TrendingItem(
        id = "trending_5",
        title = "#WeekendPlans",
        category = "Social",
        postCount = 2743,
        momentum = "↑ 45%"
    ),
    TrendingItem(
        id = "trending_6",
        title = "#LibraryLife",
        category = "Academics",
        postCount = 1543,
        momentum = "↑ 22%"
    ),
    TrendingItem(
        id = "trending_7",
        title = "#CoffeeAndCode",
        category = "Tech",
        postCount = 892,
        momentum = "↑ 15%"
    )
)