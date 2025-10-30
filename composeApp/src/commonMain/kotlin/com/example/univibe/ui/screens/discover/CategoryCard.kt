package com.example.univibe.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.BrandColors
import com.example.univibe.ui.theme.ExtendedColors

/**
 * Data class representing a category of content (e.g., Academics, Social, Sports).
 *
 * @param id Unique identifier for the category
 * @param title Display name of the category
 * @param icon Icon representing the category
 * @param backgroundColor Background color for the category card
 * @param iconTint Tint color for the icon
 * @param description Brief description of the category
 * @param memberCount Optional number of members in this category
 */
data class CategoryItem(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val iconTint: Color,
    val description: String? = null,
    val memberCount: Int? = null
)

/**
 * Individual category card component.
 * Displays a category with icon, title, and optional member count.
 * Designed for grid layout.
 *
 * @param item The category item data
 * @param onClick Callback when the card is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun CategoryCard(
    item: CategoryItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(Dimensions.CornerRadius.large))
            .background(item.backgroundColor)
            .clickable(onClick = onClick),
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
                tint = item.iconTint
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Title
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge,
                color = item.iconTint,
                maxLines = 2
            )

            // Member count (optional)
            if (item.memberCount != null && item.memberCount > 0) {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))
                Text(
                    text = "${item.memberCount}K members",
                    style = MaterialTheme.typography.labelSmall,
                    color = item.iconTint,
                    maxLines = 1
                )
            }
        }
    }
}

/**
 * Grid layout for category cards.
 * Displays categories in a 2-column grid.
 *
 * @param categories List of category items to display
 * @param onCategoryClick Callback when a category is clicked
 * @param modifier Modifier to apply to the container
 */
@Composable
fun CategoryGrid(
    categories: List<CategoryItem> = emptyList(),
    onCategoryClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md)
    ) {
        // Display categories in a 2-column grid
        for (i in categories.indices step 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
            ) {
                // First category
                CategoryCard(
                    item = categories[i],
                    modifier = Modifier.weight(1f),
                    onClick = { onCategoryClick(categories[i].id) }
                )

                // Second category (if exists)
                if (i + 1 < categories.size) {
                    CategoryCard(
                        item = categories[i + 1],
                        modifier = Modifier.weight(1f),
                        onClick = { onCategoryClick(categories[i + 1].id) }
                    )
                } else {
                    // Empty space if no second category
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

/**
 * Convenience function to create default category items for the Discover screen.
 * Includes predefined colors and icons for each category using design system tokens.
 */
fun getDefaultCategories(): List<CategoryItem> {
    return listOf(
        CategoryItem(
            id = "academics",
            title = "Academics",
            icon = Icons.Default.School,
            backgroundColor = Color(0xFFF8DEDB),
            iconTint = ExtendedColors.Academic,
            description = "Study groups & notes",
            memberCount = 45
        ),
        CategoryItem(
            id = "social",
            title = "Social",
            icon = Icons.Default.Group,
            backgroundColor = Color(0xFFFFEDD9),
            iconTint = ExtendedColors.Social,
            description = "Meet & connect",
            memberCount = 32
        ),
        CategoryItem(
            id = "events",
            title = "Events",
            icon = Icons.Default.DateRange,
            backgroundColor = Color(0xFFA3EEEA),
            iconTint = BrandColors.Teal,
            description = "Campus events",
            memberCount = 28
        ),
        CategoryItem(
            id = "sports",
            title = "Sports",
            icon = Icons.Default.EmojiEvents,
            backgroundColor = Color(0xFFFFCDD2),
            iconTint = ExtendedColors.Sports,
            description = "Teams & games",
            memberCount = 18
        ),
        CategoryItem(
            id = "arts",
            title = "Arts & Culture",
            icon = Icons.Default.Palette,
            backgroundColor = Color(0xFFF0F4C3),
            iconTint = ExtendedColors.Arts,
            description = "Creative pursuits",
            memberCount = 22
        ),
        CategoryItem(
            id = "clubs",
            title = "Clubs & Orgs",
            icon = Icons.Default.Favorite,
            backgroundColor = Color(0xFFE1BEE7),
            iconTint = ExtendedColors.Arts,
            description = "Join communities",
            memberCount = 56
        )
    )
}