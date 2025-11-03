package com.example.univibe.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockEvents
import com.example.univibe.domain.models.Event
import com.example.univibe.domain.models.User
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.SecondaryButton
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.Spacing
import com.example.univibe.util.ShareHelper
import java.text.SimpleDateFormat
import java.util.*

data class EventDetailScreen(val eventId: String) : Screen {
    @Composable
    override fun Content() {
        EventDetailScreenContent(eventId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventDetailScreenContent(eventId: String) {
    val navigator = LocalNavigator.currentOrThrow
    val event = remember { MockEvents.getEventById(eventId) }
    var isRSVPed by remember { mutableStateOf(event?.isRSVPed ?: false) }
    var currentAttendees by remember { mutableStateOf(event?.currentAttendees ?: 0) }
    
    if (event == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Event Not Found") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Event not found")
            }
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        val shareText = ShareHelper.shareEvent(event)
                        println("Share: $shareText")
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = { /* TODO: More options */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.default),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    PrimaryButton(
                        text = if (isRSVPed) "Cancel RSVP" else "RSVP",
                        onClick = {
                            if (isRSVPed) {
                                MockEvents.cancelRSVP(eventId)
                                currentAttendees--
                            } else {
                                if (event.maxAttendees == null || currentAttendees < event.maxAttendees!!) {
                                    MockEvents.rsvpToEvent(eventId)
                                    currentAttendees++
                                }
                            }
                            isRSVPed = !isRSVPed
                        },
                        modifier = Modifier.weight(1f),
                        leadingIcon = if (isRSVPed) Icons.Default.CheckCircle else Icons.Default.Event
                    )
                    
                    IconButton(
                        onClick = { /* TODO: Add to calendar */ },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "Add to Calendar")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Spacing.default),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Event Image/Banner with category emoji
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.large
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            Text(
                                event.category.emoji,
                                style = MaterialTheme.typography.displayLarge
                            )
                            Surface(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = event.category.displayName,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(
                                        horizontal = Spacing.default,
                                        vertical = Spacing.sm
                                    )
                                )
                            }
                        }
                    }
                }
            }
            
            // Title
            item {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            
            // Organizer Info
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        // Navigate to organizer profile
                        // navigator.push(UserProfileScreen(event.organizerId))
                    }
                ) {
                    UserAvatar(
                        imageUrl = event.organizer.avatarUrl,
                        name = event.organizer.fullName,
                        size = Dimensions.AvatarSize.small
                    )
                    Column {
                        Text(
                            text = "Organized by ${event.organizer.fullName}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = event.organizer.major ?: "Student",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Quick Stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    EventStat(
                        icon = Icons.Default.Group,
                        value = currentAttendees.toString(),
                        label = "Attending"
                    )
                    
                    if (event.maxAttendees != null) {
                        EventStat(
                            icon = Icons.Default.EventSeat,
                            value = event.maxAttendees.toString(),
                            label = "Capacity"
                        )
                    }
                    
                    EventStat(
                        icon = Icons.Default.Visibility,
                        value = "${(currentAttendees * 3.5).toInt()}",
                        label = "Views"
                    )
                }
            }
            
            item {
                Divider()
            }
            
            // Description
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    Text(
                        "About This Event",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            // Details Section
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.default)) {
                    Text(
                        "Event Details",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    // Date & Time
                    EventDetailRow(
                        icon = Icons.Default.Schedule,
                        label = "Date & Time",
                        value = formatFullEventTime(event.startTime, event.endTime)
                    )
                    
                    // Location
                    EventDetailRow(
                        icon = if (event.location.isVirtual) Icons.Default.Laptop else Icons.Default.Place,
                        label = "Location",
                        value = buildString {
                            append(event.location.name)
                            if (event.location.building != null) {
                                append("\n${event.location.building}")
                            }
                            if (event.location.room != null) {
                                append(", ${event.location.room}")
                            }
                            if (event.location.address != null) {
                                append("\n${event.location.address}")
                            }
                            if (event.location.isVirtual && event.location.virtualLink != null) {
                                append("\nLink will be shared with attendees")
                            }
                        },
                        isClickable = event.location.isVirtual && event.location.virtualLink != null && isRSVPed
                    )
                }
            }
            
            // Attendees Section
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Attendees (${event.attendees.size})",
                            style = MaterialTheme.typography.titleMedium
                        )
                        TextButton(onClick = { /* TODO: See all */ }) {
                            Text("See All")
                        }
                    }
                    
                    if (event.attendees.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            items(event.attendees.take(10)) { attendee ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.clickable {
                                        // Navigate to attendee profile
                                    }
                                ) {
                                    UserAvatar(
                                        imageUrl = attendee.avatarUrl,
                                        name = attendee.fullName,
                                        size = Dimensions.AvatarSize.small
                                    )
                                    Text(
                                        text = attendee.fullName.split(" ").first(),
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(top = Spacing.xs)
                                    )
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Spacing.xl),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Be the first to RSVP!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(Spacing.default))
            }
        }
    }
}

/**
 * Event stat card showing icon, value, and label
 */
@Composable
private fun EventStat(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Event detail row showing icon, label, and value
 */
@Composable
private fun EventDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    isClickable: Boolean = false,
    onValueClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isClickable) Modifier.clickable { onValueClick?.invoke() }
                else Modifier
            ),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Spacing.default)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * Format event date and time range for display
 */
private fun formatFullEventTime(startTime: Long, endTime: Long): String {
    val startFormat = SimpleDateFormat("EEE, MMM d 'at' h:mm a", Locale.getDefault())
    val endFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    
    val startDate = Date(startTime)
    val endDate = Date(endTime)
    
    val isSameDay = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(startDate) ==
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(endDate)
    
    return if (isSameDay) {
        "${startFormat.format(startDate)} - ${endFormat.format(endDate)}"
    } else {
        "${startFormat.format(startDate)} - ${startFormat.format(endDate)}"
    }
}