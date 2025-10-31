package com.example.univibe.ui.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions

/**
 * Card displaying user profile statistics.
 */
@Composable
fun ProfileStatsCard(
    postsCount: Int = 0,
    followersCount: Int = 0,
    followingCount: Int = 0,
    modifier: Modifier = Modifier,
    onStatClick: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.lg),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem(
            count = postsCount,
            label = "Posts",
            onClick = { onStatClick("posts") }
        )

        StatItem(
            count = followersCount,
            label = "Followers",
            onClick = { onStatClick("followers") }
        )

        StatItem(
            count = followingCount,
            label = "Following",
            onClick = { onStatClick("following") }
        )
    }
}

/**
 * Individual stat item with count and label.
 */
@Composable
fun StatItem(
    count: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
        modifier = modifier
    ) {
        Text(
            text = formatCount(count),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Format large numbers (1K, 1M, etc.)
 */
private fun formatCount(count: Int): String {
    return when {
        count >= 1_000_000 -> "${(count / 1_000_000)}M"
        count >= 1_000 -> "${(count / 1_000)}K"
        else -> count.toString()
    }
}