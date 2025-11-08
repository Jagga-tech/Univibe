package com.example.univibe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.ExtendedColors
import com.example.univibe.ui.utils.UISymbols

/**
 * User card component displaying user profile information.
 *
 * Features:
 * - Avatar with online status
 * - User name and status
 * - Category badges
 * - Follow button
 *
 * @param userName User's name
 * @param userStatus User's status/bio
 * @param userImageUrl Avatar image URL
 * @param isOnline Whether user is online
 * @param categories User's categories/interests
 * @param isFollowing Whether currently following
 * @param onFollowClick Callback when follow button is clicked
 * @param onClick Callback when card is clicked
 * @param modifier Modifier for customization
 */
@Composable
fun UserCard(
    userName: String,
    userStatus: String,
    userImageUrl: String? = null,
    isOnline: Boolean = false,
    categories: List<String> = emptyList(),
    isFollowing: Boolean = false,
    onFollowClick: () -> Unit = {},
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.default)
        ) {
            // Header row with avatar and follow button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.md),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Avatar and name
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(Dimensions.AvatarSize.medium)
                    ) {
                        UserAvatar(
                            imageUrl = userImageUrl,
                            size = Dimensions.AvatarSize.medium,
                            isOnline = isOnline
                        )
                    }
                    
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        Text(
                            text = userStatus,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                // Follow button
                PrimaryButton(
                    text = if (isFollowing) "Following" else "Follow",
                    onClick = onFollowClick,
                    size = ButtonSize.Small
                )
            }
            
            // Categories
            if (categories.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = Dimensions.Spacing.md),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    categories.forEach { category ->
                        Surface(
                            modifier = Modifier.background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                            ),
                            shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                        ) {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(
                                    horizontal = Dimensions.Spacing.sm,
                                    vertical = Dimensions.Spacing.xs
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Event card component displaying event information.
 *
 * Features:
 * - Event image/placeholder
 * - Event title and description
 * - Date and time
 * - Location
 * - Category badge
 * - Attendee count
 *
 * @param eventTitle Event title
 * @param eventDate Event date and time
 * @param eventLocation Event location
 * @param category Event category
 * @param attendeeCount Number of attendees
 * @param description Optional event description
 * @param imageUrl Optional event image URL
 * @param onClick Callback when card is clicked
 * @param modifier Modifier for customization
 */
@Composable
fun EventCard(
    eventTitle: String,
    eventDate: String,
    eventLocation: String,
    category: String,
    attendeeCount: Int = 0,
    description: String? = null,
    imageUrl: String? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        onClick = onClick
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Event image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(
                            topStart = Dimensions.CornerRadius.large,
                            topEnd = Dimensions.CornerRadius.large
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Image",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            // Event content
            Column(
                modifier = Modifier.padding(Dimensions.Spacing.default)
            ) {
                // Title
                Text(
                    text = eventTitle,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = Dimensions.Spacing.sm)
                )
                
                // Category badge
                Surface(
                    modifier = Modifier
                        .background(
                            color = ExtendedColors.Social,
                            shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                        )
                        .padding(bottom = Dimensions.Spacing.sm),
                    shape = RoundedCornerShape(Dimensions.CornerRadius.full)
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(
                            horizontal = Dimensions.Spacing.sm,
                            vertical = Dimensions.Spacing.xs
                        ),
                        color = Color.White
                    )
                }
                
                // Description
                if (!description.isNullOrEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = Dimensions.Spacing.md)
                    )
                }
                
                Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.md))
                
                // Date, Location, Attendees
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                    ) {
                        Text(
                            text = "📅",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = eventDate,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                    ) {
                        Text(
                            text = "📍",
                            style = MaterialTheme.typography.labelSmall
                        )
                        Text(
                            text = eventLocation,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    if (attendeeCount > 0) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                        ) {
                            Text(
                                text = "👥",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = "$attendeeCount attending",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

