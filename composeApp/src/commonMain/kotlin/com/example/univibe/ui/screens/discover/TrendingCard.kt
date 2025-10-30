package com.example.univibe.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a trending topic or hashtag.
 *
 * @param id Unique identifier for the trending item
 * @param title The trending topic or hashtag (e.g., "#CramSession", "Study Tips")
 * @param category Category of the trending topic (e.g., "Academics", "Events")
 * @param postCount Number of posts associated with this trend
 * @param momentum How fast the trend is growing (e.g., "↑ 45%")
 * @param icon Optional icon to display for the trend
 */
data class TrendingItem(
    val id: String,
    val title: String,
    val category: String,
    val postCount: Int,
    val momentum: String? = null,
    val icon: ImageVector? = null
)

/**
 * Individual trending card component.
 * Displays a trending topic/hashtag with metadata and momentum indicator.
 *
 * @param item The trending item data
 * @param onClick Callback when the card is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun TrendingCard(
    item: TrendingItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .then(Modifier),
        onClick = onClick,
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Center
        ) {
            // Icon and left content
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = Dimensions.Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon
                if (item.icon != null) {
                    Box(
                        modifier = Modifier
                            .size(Dimensions.IconSize.large)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                    Dimensions.CornerRadius.medium
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(Dimensions.IconSize.medium),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
                ) {
                    // Title
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )

                    // Category
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )

                    // Post count
                    Text(
                        text = "${item.postCount} posts",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }

            // Right side: Momentum indicator
            if (!item.momentum.isNullOrEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = "Trending",
                        modifier = Modifier.size(Dimensions.IconSize.medium),
                        tint = MaterialTheme.colorScheme.tertiary
                    )

                    Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))

                    Text(
                        text = item.momentum,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Vertical list of trending cards.
 * Used to display multiple trending topics in the Discover screen.
 *
 * @param trendingItems List of trending items to display
 * @param onTrendingClick Callback when a trending item is clicked
 * @param modifier Modifier to apply to the container
 */
@Composable
fun TrendingCardList(
    trendingItems: List<TrendingItem> = emptyList(),
    onTrendingClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        trendingItems.forEach { item ->
            TrendingCard(
                item = item,
                onClick = { onTrendingClick(item.id) }
            )
        }
    }
}

/**
 * Convenience function to create sample/mock trending items for preview and testing.
 */
fun getSampleTrendingItems(): List<TrendingItem> = listOf(
    TrendingItem(
        id = "trending_1",
        title = "#CramSession",
        category = "Academics",
        postCount = 1204,
        momentum = "↑ 45%",
        icon = Icons.Default.TrendingUp
    ),
    TrendingItem(
        id = "trending_2",
        title = "#StudyBuddy",
        category = "Community",
        postCount = 856,
        momentum = "↑ 32%",
        icon = Icons.Default.TrendingUp
    ),
    TrendingItem(
        id = "trending_3",
        title = "#CampusLife",
        category = "Events",
        postCount = 2341,
        momentum = "↑ 58%",
        icon = Icons.Default.TrendingUp
    ),
    TrendingItem(
        id = "trending_4",
        title = "#MidtermsWeek",
        category = "Academics",
        postCount = 1567,
        momentum = "↑ 28%",
        icon = Icons.Default.TrendingUp
    ),
    TrendingItem(
        id = "trending_5",
        title = "#GroupProject",
        category = "Collaboration",
        postCount = 743,
        momentum = "↑ 15%",
        icon = Icons.Default.TrendingUp
    )
)