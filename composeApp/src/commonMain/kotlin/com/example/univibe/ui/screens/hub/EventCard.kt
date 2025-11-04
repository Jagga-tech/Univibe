package com.example.univibe.ui.screens.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Data class representing a campus event.
 *
 * @param id Unique identifier for the event
 * @param title Title of the event
 * @param description Brief description of the event
 * @param date Event date (e.g., "Mar 15, 2024")
 * @param time Event time (e.g., "7:00 PM")
 * @param location Physical location of the event
 * @param attendeeCount Number of people attending
 * @param category Category of the event (e.g., "Academic", "Social", "Sports")
 * @param isAttending Whether the current user is attending
 */
data class EventItem(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String,
    val attendeeCount: Int,
    val category: String,
    val isAttending: Boolean = false
)

/**
 * Individual event card component for displaying campus events.
 * Shows event details and attendee count.
 *
 * @param item The event item data
 * @param onClick Callback when the card is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun EventCard(
    item: EventItem,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    UniVibeCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            // Header with date badge and category
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Date badge
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                Dimensions.CornerRadius.medium
                            )
                        )
                        .padding(
                            horizontal = Dimensions.Spacing.sm,
                            vertical = Dimensions.Spacing.xs
                        )
                ) {
                    Text(
                        text = item.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

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
                        )
                ) {
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // Event title
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))

            // Event description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Event details (time, location, attendees)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                EventDetailRow(
                    icon = UISymbols.ACCESS_TIME,
                    label = "Time",
                    value = item.time
                )

                EventDetailRow(
                    icon = UISymbols.LOCATION,
                    label = "Location",
                    value = item.location
                )

                EventDetailRow(
                    icon = UISymbols.PEOPLE,
                    label = "Attending",
                    value = "${item.attendeeCount} people"
                )
            }

            // Attending indicator
            if (item.isAttending) {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                Dimensions.CornerRadius.small
                            )
                        )
                        .padding(Dimensions.Spacing.sm),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓ You're attending",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
    }
}

/**
 * Helper composable to display event detail rows with icons.
 */
@Composable
private fun EventDetailRow(
    icon: String,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextIcon(
            symbol = icon,
            contentDescription = label,
            fontSize = 16,
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Vertical list of event cards.
 * Used to display multiple campus events.
 *
 * @param events List of event items to display
 * @param onEventClick Callback when an event card is clicked
 * @param modifier Modifier to apply to the container
 */
@Composable
fun EventCardList(
    events: List<EventItem> = emptyList(),
    onEventClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        events.forEach { event ->
            EventCard(
                item = event,
                onClick = { onEventClick(event.id) }
            )
        }
    }
}

/**
 * Convenience function to create sample/mock events for preview and testing.
 */
fun getSampleEvents(): List<EventItem> = listOf(
    EventItem(
        id = "event_1",
        title = "Campus Career Fair 2024",
        description = "Meet top companies and explore internship opportunities. Over 50 companies attending!",
        date = "Mar 22",
        time = "10:00 AM - 4:00 PM",
        location = "Student Center Auditorium",
        attendeeCount = 287,
        category = "Career",
        isAttending = true
    ),
    EventItem(
        id = "event_2",
        title = "Friday Night Trivia Night",
        description = "Teams of 4, prizes for top 3 teams. Grab your friends and test your knowledge!",
        date = "Mar 24",
        time = "7:00 PM - 9:00 PM",
        location = "Dining Hall Room B",
        attendeeCount = 156,
        category = "Social",
        isAttending = false
    ),
    EventItem(
        id = "event_3",
        title = "Spring Concert featuring Local Bands",
        description = "Four amazing local bands performing live. Free admission with student ID!",
        date = "Mar 29",
        time = "6:00 PM - 10:00 PM",
        location = "Outdoor Amphitheater",
        attendeeCount = 542,
        category = "Entertainment",
        isAttending = false
    ),
    EventItem(
        id = "event_4",
        title = "Yoga & Wellness Workshop",
        description = "Learn mindfulness and basic yoga poses. Mats provided. All levels welcome!",
        date = "Mar 20",
        time = "5:00 PM - 6:00 PM",
        location = "Recreation Center Studio A",
        attendeeCount = 43,
        category = "Wellness",
        isAttending = true
    ),
    EventItem(
        id = "event_5",
        title = "Hackathon: Build Something Amazing",
        description = "24-hour hackathon. Teams compete for prizes. Great for networking and learning!",
        date = "Apr 5-6",
        time = "12:00 PM Friday - 12:00 PM Saturday",
        location = "Tech Building Labs",
        attendeeCount = 89,
        category = "Academic",
        isAttending = false
    )
)