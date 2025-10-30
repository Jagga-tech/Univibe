package com.example.univibe.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.theme.Dimensions

/**
 * Data class representing an item in a profile section.
 *
 * @param id Unique identifier
 * @param title Display title
 * @param subtitle Optional subtitle or additional info
 */
data class ProfileSectionItem(
    val id: String,
    val title: String,
    val subtitle: String? = null
)

/**
 * Profile section card component.
 * Displays a section title, list of items, and optional edit button.
 *
 * @param title Section title
 * @param items List of section items to display
 * @param isEditable Whether the section can be edited
 * @param onEditClick Callback when edit button is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun ProfileSection(
    title: String,
    items: List<ProfileSectionItem> = emptyList(),
    isEditable: Boolean = false,
    onEditClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            // Header with title and edit button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                if (isEditable) {
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.size(Dimensions.IconSize.large)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit section",
                            modifier = Modifier.size(Dimensions.IconSize.medium),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

                // Display items as chips/tags in horizontal scrolling row
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    items(items.size) { index ->
                        ProfileSectionItemChip(item = items[index])
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
                Text(
                    text = "No items yet",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Individual section item displayed as a chip/tag.
 */
@Composable
private fun ProfileSectionItemChip(
    item: ProfileSectionItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                    Dimensions.CornerRadius.full
                )
            )
            .padding(
                horizontal = Dimensions.Spacing.md,
                vertical = Dimensions.Spacing.xs
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (!item.subtitle.isNullOrEmpty()) {
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Profile sections layout combining multiple profile sections.
 *
 * @param sections List of sections to display
 * @param isOwnProfile Whether this is the current user's profile
 * @param onSectionEditClick Callback when edit button is clicked for a section
 * @param modifier Modifier to apply to the container
 */
@Composable
fun ProfileSectionsGrid(
    sections: List<Pair<String, List<ProfileSectionItem>>> = emptyList(),
    isOwnProfile: Boolean = true,
    onSectionEditClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        sections.forEach { (sectionTitle, items) ->
            ProfileSection(
                title = sectionTitle,
                items = items,
                isEditable = isOwnProfile,
                onEditClick = { onSectionEditClick(sectionTitle) }
            )
        }
    }
}

/**
 * Convenience function to create sample profile sections for preview and testing.
 */
fun getSampleProfileSections(): List<Pair<String, List<ProfileSectionItem>>> = listOf(
    "Interests" to listOf(
        ProfileSectionItem(id = "int_1", title = "Web Development"),
        ProfileSectionItem(id = "int_2", title = "Machine Learning"),
        ProfileSectionItem(id = "int_3", title = "Design")
    ),
    "Groups" to listOf(
        ProfileSectionItem(id = "grp_1", title = "CS Club"),
        ProfileSectionItem(id = "grp_2", title = "Tennis Team"),
        ProfileSectionItem(id = "grp_3", title = "Debate Club")
    ),
    "Skills" to listOf(
        ProfileSectionItem(id = "skl_1", title = "Kotlin"),
        ProfileSectionItem(id = "skl_2", title = "Python"),
        ProfileSectionItem(id = "skl_3", title = "Public Speaking")
    )
)