package com.example.univibe.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.domain.models.StoryGroup

/**
 * Data class representing a user story.
 *
 * @param id Unique identifier for the story
 * @param userName Name of the user who posted the story
 * @param userAvatarUrl URL of the user's avatar image
 * @param isViewed Whether the current user has viewed this story
 */
data class StoryItem(
    val id: String,
    val userName: String,
    val userAvatarUrl: String? = null,
    val isViewed: Boolean = false
)

/**
 * Horizontal scrolling row of stories from other users.
 * First item is "Add Story" button, followed by stories from users.
 *
 * @param stories List of stories to display
 * @param onStoryClick Callback when a story is clicked
 * @param onAddStoryClick Callback when "Add Story" button is clicked
 */
@Composable
fun StoriesRow(
    stories: List<StoryItem> = emptyList(),
    onStoryClick: (String) -> Unit = {},
    onAddStoryClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.Spacing.md)
    ) {
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            // Add Story button
            item {
                AddStoryButton(onClick = onAddStoryClick)
            }

            // User stories
            items(stories.size) { index ->
                val story = stories[index]
                StoryItemView(
                    story = story,
                    onClick = { onStoryClick(story.id) }
                )
            }
        }
    }
}

/**
 * Add Story button shown at the beginning of the stories row.
 */
@Composable
private fun AddStoryButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(Dimensions.AvatarSize.large)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(Dimensions.AvatarSize.large)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add story",
                modifier = Modifier.size(Dimensions.IconSize.large),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))

        Text(
            text = "Your Story",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            modifier = Modifier.width(Dimensions.AvatarSize.large)
        )
    }
}

/**
 * Individual story item in the stories row.
 * Shows a circular avatar with a border indicating view status.
 */
@Composable
private fun StoryItemView(
    story: StoryItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(Dimensions.AvatarSize.large)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Story border: thicker/colored if unviewed, thin if viewed
        Box(
            modifier = Modifier
                .size(Dimensions.AvatarSize.large)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(
                    if (story.isViewed)
                        MaterialTheme.colorScheme.surfaceVariant
                    else
                        MaterialTheme.colorScheme.primary,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .padding(if (story.isViewed) 0.dp else 2.dp),
            contentAlignment = Alignment.Center
        ) {
            UserAvatar(
                imageUrl = story.userAvatarUrl,
                size = Dimensions.AvatarSize.large - (if (story.isViewed) 0.dp else 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))

        Text(
            text = story.userName,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            modifier = Modifier.width(Dimensions.AvatarSize.large)
        )
    }
}

/**
 * StoriesRow overload that accepts StoryGroup domain models.
 * Displays a horizontal scrolling row of stories from the data layer.
 *
 * @param storyGroups List of StoryGroup domain models to display
 * @param onStoryClick Callback when a story group is clicked
 * @param onAddStoryClick Callback when "Add Story" button is clicked
 */
@Composable
fun StoriesRow(
    storyGroups: List<StoryGroup> = emptyList(),
    onStoryClick: (StoryGroup) -> Unit = {},
    onAddStoryClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.Spacing.md)
    ) {
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            // Add Story button
            item {
                AddStoryButton(onClick = onAddStoryClick)
            }

            // Story group circles
            items(storyGroups.size) { index ->
                val storyGroup = storyGroups[index]
                DomainStoryCircle(
                    storyGroup = storyGroup,
                    onClick = { onStoryClick(storyGroup) }
                )
            }
        }
    }
}

/**
 * Story circle for domain StoryGroup model.
 * Shows a circular avatar with a border indicating view status.
 */
@Composable
private fun DomainStoryCircle(
    storyGroup: StoryGroup,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(Dimensions.AvatarSize.large)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Story border: thicker/colored if unviewed, thin if viewed
        Box(
            modifier = Modifier
                .size(Dimensions.AvatarSize.large)
                .clip(androidx.compose.foundation.shape.CircleShape)
                .background(
                    if (storyGroup.hasUnviewedStories)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                .padding(if (storyGroup.hasUnviewedStories) 2.dp else 0.dp),
            contentAlignment = Alignment.Center
        ) {
            UserAvatar(
                imageUrl = storyGroup.user.avatarUrl,
                size = Dimensions.AvatarSize.large - (if (storyGroup.hasUnviewedStories) 4.dp else 0.dp)
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Spacing.xs))

        Text(
            text = storyGroup.user.fullName.split(" ").first(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            modifier = Modifier.width(Dimensions.AvatarSize.large)
        )
    }
}