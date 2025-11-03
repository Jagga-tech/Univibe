package com.example.univibe.ui.screens.features

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
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.Spacing
import com.example.univibe.data.mock.MockEvents
import com.example.univibe.domain.models.*
import com.example.univibe.ui.screens.detail.EventDetailScreen
import com.example.univibe.ui.screens.create.CreateEventScreen
import java.text.SimpleDateFormat
import java.util.*

/**
 * Events Screen - Browse and discover campus events
 *
 * Features:
 * - Browse all upcoming events
 * - Filter by time (today, this week, this month)
 * - Filter by event category (social, academic, sports, etc.)
 * - View featured events carousel
 * - Search for specific events
 * - RSVP to events
 *
 * UI Components:
 * - Featured Events Carousel (horizontal scrolling)
 * - Event Filter Chips (time-based filtering)
 * - Category Filter Row (category-based filtering)
 * - Event Cards (event list items)
 * - Empty State (when no events match filters)
 *
 * State Management:
 * - selectedFilter: Currently selected time filter
 * - selectedCategory: Currently selected category filter
 * - events: Computed from current filters
 * - featuredEvents: Separate list for carousel
 */
object EventsScreen : Screen {
    @Composable
    override fun Content() {
        EventsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    // State management
    var selectedFilter by remember { mutableStateOf(EventFilter.ALL) }
    var selectedCategory by remember { mutableStateOf<EventCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    
    // Compute filtered events whenever filters change
    val events = remember(selectedFilter, selectedCategory, searchQuery) {
        var filtered = MockEvents.getEventsByFilter(selectedFilter)
        if (selectedCategory != null) {
            filtered = filtered.filter { it.category == selectedCategory }
        }
        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter { event ->
                event.title.contains(searchQuery, ignoreCase = true) ||
                event.description.contains(searchQuery, ignoreCase = true) ||
                event.location.name.contains(searchQuery, ignoreCase = true)
            }
        }
        filtered
    }
    
    // Get featured events for carousel
    val featuredEvents = remember { MockEvents.getFeaturedEvents() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Events") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.pop() }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { isSearching = !isSearching }
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(
                        onClick = { /* TODO: Calendar view */ }
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "Calendar View")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.push(CreateEventScreen) },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Event")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(Spacing.default)
        ) {
            // Search Bar Section
            if (isSearching) {
                item {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search events...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { searchQuery = "" }
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.default),
                        singleLine = true
                    )
                }
            }
            
            // Filter Chips Section
            item {
                EventFilterChips(
                    selectedFilter = selectedFilter,
                    onFilterSelected = { selectedFilter = it }
                )
            }
            
            // Category Filter Section
            item {
                CategoryFilterRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
            
            // Featured Events Section (only show when ALL filter selected and no category filter)
            if (selectedFilter == EventFilter.ALL && selectedCategory == null && featuredEvents.isNotEmpty()) {
                item {
                    Column {
                        Text(
                            "Featured Events",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = Spacing.default)
                        )
                        Spacer(modifier = Modifier.height(Spacing.sm))
                        FeaturedEventsCarousel(
                            events = featuredEvents,
                            onEventClick = { event ->
                                navigator.push(EventDetailScreen(event.id))
                            }
                        )
                    }
                }
            }
            
            // Events List Header
            item {
                Text(
                    if (selectedFilter == EventFilter.ALL && selectedCategory == null) 
                        "Upcoming Events" 
                    else 
                        "Filtered Events",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = Spacing.default)
                )
            }
            
            // Events List or Empty State
            if (events.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.xl),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Spacing.default)
                        ) {
                            Icon(
                                Icons.Default.Event,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                "No events found",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "Try adjusting your filters",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(events) { event ->
                    EventCard(
                        event = event,
                        onClick = { navigator.push(EventDetailScreen(event.id)) },
                        modifier = Modifier.padding(horizontal = Spacing.default)
                    )
                }
            }
            
            // Bottom padding
            item {
                Spacer(modifier = Modifier.height(Spacing.default))
            }
        }
    }
}

/**
 * Filter chips for time-based filtering (TODAY, THIS_WEEK, etc.)
 */
@Composable
private fun EventFilterChips(
    selectedFilter: EventFilter,
    onFilterSelected: (EventFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    ScrollableTabRow(
        selectedTabIndex = selectedFilter.ordinal,
        modifier = modifier.fillMaxWidth(),
        edgePadding = Spacing.default
    ) {
        EventFilter.values().forEach { filter ->
            Tab(
                selected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) },
                text = {
                    Text(
                        filter.name
                            .lowercase()
                            .replace("_", " ")
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                    )
                }
            )
        }
    }
}

/**
 * Category filter row with FilterChip components
 */
@Composable
private fun CategoryFilterRow(
    selectedCategory: EventCategory?,
    onCategorySelected: (EventCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        contentPadding = PaddingValues(horizontal = Spacing.default)
    ) {
        // "All" chip
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") }
            )
        }
        
        // Category chips
        items(EventCategory.values().toList()) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text("${category.emoji} ${category.displayName}") }
            )
        }
    }
}

/**
 * Horizontal carousel of featured events
 */
@Composable
private fun FeaturedEventsCarousel(
    events: List<Event>,
    onEventClick: (Event) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.default),
        contentPadding = PaddingValues(horizontal = Spacing.default)
    ) {
        items(events) { event ->
            FeaturedEventCard(
                event = event,
                onClick = { onEventClick(event) }
            )
        }
    }
}

/**
 * Featured event card for carousel
 *
 * Shows event with prominent display:
 * - Large category emoji
 * - Title (2 lines max)
 * - Time
 * - Location
 * - Category badge
 */
@Composable
private fun FeaturedEventCard(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(300.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column {
            // Image/emoji area
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        event.category.emoji,
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            }
            
            // Content area
            Column(
                modifier = Modifier.padding(Spacing.default),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                // Category badge
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = event.category.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs)
                    )
                }
                
                // Title
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                
                // Time
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatEventTime(event.startTime),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Location
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = event.location.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Event card for list display
 *
 * Shows event with:
 * - Date badge (month/day)
 * - Category emoji and name
 * - RSVP indicator (if attended)
 * - Title
 * - Time
 * - Attendee count
 */
@Composable
private fun EventCard(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(Spacing.default),
            horizontalArrangement = Arrangement.spacedBy(Spacing.default)
        ) {
            // Date badge
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.size(60.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = SimpleDateFormat("MMM", Locale.getDefault()).format(Date(event.startTime)),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = SimpleDateFormat("dd", Locale.getDefault()).format(Date(event.startTime)),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            // Event details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                // Category row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.category.emoji,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = event.category.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    // RSVP indicator
                    if (event.isRSVPed) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "RSVP'd",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
                
                // Title
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium
                )
                
                // Time
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatEventTime(event.startTime),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Attendee count
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Group,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${event.currentAttendees} attending",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Format event time for display
 *
 * Shows:
 * - "In Xm" for events in the next hour
 * - "Today at HH:mm a" for events today
 * - "Tomorrow at HH:mm a" for events tomorrow
 * - "EEE, MMM d 'at' HH:mm a" for future events
 */
private fun formatEventTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = timestamp - now
    
    return when {
        diff < 0 -> "Ended"
        diff < 3600000 -> "In ${diff / 60000}m"
        diff < 86400000 -> {
            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
            "Today at ${sdf.format(Date(timestamp))}"
        }
        diff < 172800000 -> {
            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
            "Tomorrow at ${sdf.format(Date(timestamp))}"
        }
        else -> {
            val sdf = SimpleDateFormat("EEE, MMM d 'at' h:mm a", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}