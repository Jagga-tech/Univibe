package com.example.univibe.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.Spacing
import com.example.univibe.ui.utils.UISymbols
import com.example.univibe.util.ShareHelper

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
                            TextIcon(
                                symbol = UISymbols.BACK,
                                contentDescription = "Back",
                                fontSize = 20
                            )
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
                        TextIcon(
                            symbol = UISymbols.BACK,
                            contentDescription = "Back",
                            fontSize = 20
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        val shareText = ShareHelper.shareEvent(event)
                        println("Share: $shareText")
                    }) {
                        TextIcon(
                            symbol = UISymbols.SHARE,
                            contentDescription = "Share",
                            fontSize = 20
                        )
                    }
                    IconButton(onClick = { /* TODO: More options */ }) {
                        TextIcon(
                            symbol = UISymbols.MORE_VERT,
                            contentDescription = "More",
                            fontSize = 20
                        )
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
                        leadingIcon = if (isRSVPed) UISymbols.CHECK_CIRCLE else UISymbols.EVENT
                    )
                    
                    IconButton(
                        onClick = { /* TODO: Add to calendar */ },
                        modifier = Modifier.size(48.dp)
                    ) {
                        TextIcon(
                            symbol = UISymbols.CALENDAR,
                            contentDescription = "Add to Calendar",
                            fontSize = 20
                        )
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
                        icon = UISymbols.GROUP,
                        value = currentAttendees.toString(),
                        label = "Attending"
                    )
                    
                    if (event.maxAttendees != null) {
                        EventStat(
                            icon = UISymbols.EVENT_SEAT,
                            value = event.maxAttendees.toString(),
                            label = "Capacity"
                        )
                    }
                    
                    EventStat(
                        icon = UISymbols.VISIBILITY,
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
                        icon = UISymbols.SCHEDULE,
                        label = "Date & Time",
                        value = formatFullEventTime(event.startTime, event.endTime)
                    )
                    
                    // Location
                    EventDetailRow(
                        icon = if (event.location.isVirtual) UISymbols.LAPTOP else UISymbols.LOCATION,
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
    icon: String,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        TextIcon(
            symbol = icon,
            contentDescription = null,
            fontSize = 20
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
    icon: String,
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
        TextIcon(
            symbol = icon,
            contentDescription = null,
            fontSize = 16
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
    val startCal = getCalendarFromEventTimestamp(startTime)
    val endCal = getCalendarFromEventTimestamp(endTime)
    
    val isSameDay = startCal.dayOfMonth == endCal.dayOfMonth && 
                    startCal.monthOfYear == endCal.monthOfYear && 
                    startCal.year == endCal.year
    
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    
    fun formatTime(cal: EventCalendarData): String {
        val hour = cal.hour % 12
        val displayHour = if (hour == 0) 12 else hour
        val minute = cal.minute.toString().padStart(2, '0')
        val period = if (cal.hour < 12) "AM" else "PM"
        return "$displayHour:$minute $period"
    }
    
    fun formatDateLong(cal: EventCalendarData): String {
        val dayName = days.getOrNull(cal.dayOfWeek - 1) ?: "Mon"
        val monthName = months.getOrNull(cal.monthOfYear - 1) ?: "Jan"
        val day = cal.dayOfMonth
        return "$dayName, $monthName $day at ${formatTime(cal)}"
    }
    
    return if (isSameDay) {
        "${formatDateLong(startCal)} - ${formatTime(endCal)}"
    } else {
        "${formatDateLong(startCal)} - ${formatDateLong(endCal)}"
    }
}

private fun getCalendarFromEventTimestamp(timestamp: Long): EventCalendarData {
    val totalSeconds = timestamp / 1000
    val totalMinutes = totalSeconds / 60
    val totalHours = totalMinutes / 60
    val totalDays = totalHours / 24
    
    val daysPerYear = 365
    val yearsSinceEpoch = totalDays / daysPerYear
    val year = 1970 + yearsSinceEpoch.toInt()
    
    val dayOfYear = (totalDays % daysPerYear).toInt() + 1
    val hour = ((totalHours % 24).toInt())
    val minute = (totalMinutes % 60).toInt()
    val second = (totalSeconds % 60).toInt()
    
    val isLeapYear = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    val daysInMonth = intArrayOf(31, if (isLeapYear) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    
    var monthOfYear = 1
    var dayOfMonth = dayOfYear
    for (i in daysInMonth.indices) {
        if (dayOfMonth <= daysInMonth[i]) {
            monthOfYear = i + 1
            break
        }
        dayOfMonth -= daysInMonth[i]
    }
    
    val dayOfWeek = ((totalDays + 4) % 7).toInt() + 1
    
    return EventCalendarData(year, monthOfYear, dayOfMonth, hour, minute, second, dayOfWeek)
}

data class EventCalendarData(
    val year: Int,
    val monthOfYear: Int,
    val dayOfMonth: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val dayOfWeek: Int
)