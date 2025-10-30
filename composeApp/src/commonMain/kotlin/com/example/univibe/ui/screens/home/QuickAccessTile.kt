package com.example.univibe.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing a quick access tile.
 *
 * @param id Unique identifier for the tile
 * @param title Title text to display on the tile
 * @param icon Icon to display on the tile
 * @param description Optional description text
 * @param backgroundColor Background color for the tile
 * @param iconTint Tint color for the icon
 */
data class QuickAccessItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val description: String? = null,
    val backgroundColor: androidx.compose.ui.graphics.Color? = null,
    val iconTint: androidx.compose.ui.graphics.Color? = null
)

/**
 * Grid of quick access tiles for common actions in the home screen.
 * Displays 2 tiles per row.
 *
 * @param items List of quick access items to display
 * @param onTileClick Callback when a tile is clicked
 */
@Composable
fun QuickAccessGrid(
    items: List<QuickAccessItem> = emptyList(),
    onTileClick: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md)
    ) {
        // Display tiles in a 2-column grid
        for (i in items.indices step 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
            ) {
                // First tile in row
                QuickAccessTile(
                    item = items[i],
                    modifier = Modifier.weight(1f),
                    onClick = { onTileClick(items[i].id) }
                )

                // Second tile in row (if exists)
                if (i + 1 < items.size) {
                    QuickAccessTile(
                        item = items[i + 1],
                        modifier = Modifier.weight(1f),
                        onClick = { onTileClick(items[i + 1].id) }
                    )
                } else {
                    // Empty space if no second tile
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Individual quick access tile component.
 * Shows an icon and title that can be clicked to perform an action.
 *
 * @param item The quick access item data
 * @param modifier Modifier to apply to the tile
 * @param onClick Callback when the tile is clicked
 */
@Composable
fun QuickAccessTile(
    item: QuickAccessItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable(onClick = onClick),
        elevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = item.backgroundColor ?: MaterialTheme.colorScheme.primaryContainer
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimensions.Spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    modifier = Modifier.size(Dimensions.IconSize.large),
                    tint = item.iconTint ?: MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))

                // Title
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                // Optional description
                if (!item.description.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Convenience function to create default quick access items for the home screen.
 */
fun getDefaultQuickAccessItems(): List<QuickAccessItem> = listOf(
    QuickAccessItem(
        id = "study_sessions",
        title = "Study Sessions",
        icon = Icons.Default.Timer,
        description = "Find study groups"
    ),
    QuickAccessItem(
        id = "find_buddy",
        title = "Find Study Buddy",
        icon = Icons.Default.Group,
        description = "Connect with peers"
    ),
    QuickAccessItem(
        id = "class_notes",
        title = "Class Notes",
        icon = Icons.Default.Book,
        description = "Share & browse notes"
    ),
    QuickAccessItem(
        id = "campus_events",
        title = "Campus Events",
        icon = Icons.Default.DateRange,
        description = "Upcoming events"
    )
)