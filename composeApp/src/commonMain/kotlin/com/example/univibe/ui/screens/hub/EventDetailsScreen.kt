package com.example.univibe.ui.screens.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.OutlineButton
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons

/**
 * Data class representing an event organizer/host.
 *
 * @param id Unique identifier for the organizer
 * @param name Name of the organizer/organization
 * @param avatarUrl Optional URL for the organizer's avatar
 * @param memberCount Number of members in the organization
 */
data class EventOrganizer(
    val id: String,
    val name: String,
    val avatarUrl: String? = null,
    val memberCount: Int = 0
)

/**
 * Event details screen composable - shows full event information and RSVP options.
 * Displays event poster, details, attendees, and allows user to RSVP.
 *
 * @param event The event to display details for
 * @param attendees List of attendee avatars to display
 * @param organizer Organization hosting the event
 * @param onBackClick Callback when back button is clicked
 * @param onRsvpClick Callback when RSVP button is clicked (provides new attending state)
 * @param onShareClick Callback when share button is clicked
 * @param onSaveClick Callback when save button is clicked (provides new saved state)
 * @param onOrganizerClick Callback when organizer card is clicked
 * @param onAttendeesClick Callback when "View all attendees" is clicked
 */
@Composable
fun EventDetailsScreen(
    event: EventItem = getSampleEvents()[0],
    attendees: List<String> = getSampleAttendeeAvatars(),
    organizer: EventOrganizer = getSampleEventOrganizer(),
    onBackClick: () -> Unit = {},
    onRsvpClick: (Boolean) -> Unit = {},
    onShareClick: () -> Unit = {},
    onSaveClick: (Boolean) -> Unit = {},
    onOrganizerClick: () -> Unit = {},
    onAttendeesClick: () -> Unit = {}
) {
    var isAttending by remember { mutableStateOf(event.isAttending) }
    var isSaved by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with back and action buttons
        EventDetailsHeader(
            event = event,
            isSaved = isSaved,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            onSaveClick = { newSavedState ->
                isSaved = newSavedState
                onSaveClick(newSavedState)
            }
        )

        // Scrollable content
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // Event poster placeholder
            item {
                EventPosterPlaceholder()
            }

            // Event title and category
            item {
                EventTitleSection(event = event)
            }

            // Event details (date, time, location)
            item {
                EventDetailsSection(event = event)
            }

            // Event description
            item {
                EventDescriptionSection(event = event)
            }

            // Organizer card
            item {
                OrganizerCard(
                    organizer = organizer,
                    onClick = onOrganizerClick
                )
            }

            // Attendees section
            item {
                AttendeesSection(
                    attendees = attendees,
                    totalAttendeeCount = event.attendeeCount,
                    onViewAllClick = onAttendeesClick
                )
            }

            // Bottom padding
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }

        // RSVP action buttons
        RSVPActionButtons(
            isAttending = isAttending,
            onRsvpClick = { newAttendingState ->
                isAttending = newAttendingState
                onRsvpClick(newAttendingState)
            }
        )
    }
}

/**
 * Header with back button and action icons.
 */
@Composable
private fun EventDetailsHeader(
    event: EventItem,
    isSaved: Boolean,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onSaveClick: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(Dimensions.IconSize.large)
        ) {
            Icon(
                imageVector = PlatformIcons.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
        ) {
            IconButton(
                onClick = onShareClick,
                modifier = Modifier.size(Dimensions.IconSize.large)
            ) {
                Icon(
                    imageVector = PlatformIcons.Share,
                    contentDescription = "Share event",
                    modifier = Modifier.size(Dimensions.IconSize.medium),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = { onSaveClick(!isSaved) },
                modifier = Modifier.size(Dimensions.IconSize.large)
            ) {
                Icon(
                    imageVector = if (isSaved) PlatformIcons.BookmarkFilled else PlatformIcons.BookmarkBorder,
                    contentDescription = if (isSaved) "Saved" else "Save event",
                    modifier = Modifier.size(Dimensions.IconSize.medium),
                    tint = if (isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Event poster placeholder.
 */
@Composable
private fun EventPosterPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "📸 Event Poster",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Event title section with category badge.
 */
@Composable
private fun EventTitleSection(event: EventItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        // Category badge
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(
                        Dimensions.CornerRadius.small
                    )
                )
                .padding(
                    horizontal = Dimensions.Spacing.sm,
                    vertical = Dimensions.Spacing.xs
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = event.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        // Event title
        Text(
            text = event.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Event details section showing date, time, and location.
 */
@Composable
private fun EventDetailsSection(event: EventItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        EventDetailRowLarge(
            icon = PlatformIcons.AccessTime,
            label = "Date & Time",
            value = "${event.date} • ${event.time}"
        )

        EventDetailRowLarge(
            icon = PlatformIcons.LocationOn,
            label = "Location",
            value = event.location
        )

        EventDetailRowLarge(
            icon = PlatformIcons.People,
            label = "Attendees",
            value = "${event.attendeeCount} people attending"
        )
    }
}

/**
 * Helper composable for larger event detail rows.
 */
@Composable
private fun EventDetailRowLarge(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier
                .size(Dimensions.IconSize.large)
                .padding(top = 2.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Event description section.
 */
@Composable
private fun EventDescriptionSection(event: EventItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Text(
            text = "About this event",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = (MaterialTheme.typography.bodyMedium.lineHeight * 1.2)
        )
    }
}

/**
 * Event organizer card.
 */
@Composable
private fun OrganizerCard(
    organizer: EventOrganizer,
    onClick: () -> Unit = {}
) {
    UniVibeCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md, vertical = Dimensions.Spacing.md),
        onClick = onClick,
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                imageUrl = organizer.avatarUrl,
                size = Dimensions.AvatarSize.large
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                Text(
                    text = "Organized by",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = organizer.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (organizer.memberCount > 0) {
                    Text(
                        text = "${organizer.memberCount} members",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = PlatformIcons.People,
                contentDescription = "View organizer",
                modifier = Modifier.size(Dimensions.IconSize.medium),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Attendees section showing preview of attendees.
 */
@Composable
private fun AttendeesSection(
    attendees: List<String> = emptyList(),
    totalAttendeeCount: Int = 0,
    onViewAllClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Attendees",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (attendees.isNotEmpty()) {
                OutlineButton(
                    text = "View all ($totalAttendeeCount)",
                    onClick = onViewAllClick,
                    modifier = Modifier.height(36.dp)
                )
            }
        }

        if (attendees.isEmpty()) {
            Text(
                text = "No one attending yet. Be the first!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = Dimensions.Spacing.md)
            )
        } else {
            // Show avatar stack for first 5 attendees
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.spacedBy((-12).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                attendees.take(5).forEachIndexed { index, _ ->
                    UserAvatar(
                        imageUrl = null,
                        size = Dimensions.AvatarSize.medium,
                        modifier = Modifier
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(2.dp)
                            .zIndex((5 - index).toFloat())
                    )
                }

                if (attendees.size > 5) {
                    Box(
                        modifier = Modifier
                            .size(Dimensions.AvatarSize.medium)
                            .clip(androidx.compose.foundation.shape.CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+${attendees.size - 5}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

/**
 * RSVP action buttons at the bottom of the screen.
 */
@Composable
private fun RSVPActionButtons(
    isAttending: Boolean,
    onRsvpClick: (Boolean) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md)
    ) {
        PrimaryButton(
            text = if (isAttending) "✓ You're attending" else "RSVP - I'm attending",
            onClick = { onRsvpClick(!isAttending) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Convenience functions for sample data.
 */
fun getSampleEventOrganizer(): EventOrganizer = EventOrganizer(
    id = "org_1",
    name = "Campus Activities Board",
    avatarUrl = null,
    memberCount = 45
)

fun getSampleAttendeeAvatars(): List<String> = listOf(
    "attendee_1",
    "attendee_2",
    "attendee_3",
    "attendee_4",
    "attendee_5",
    "attendee_6",
    "attendee_7",
    "attendee_8",
    "attendee_9",
    "attendee_10"
)