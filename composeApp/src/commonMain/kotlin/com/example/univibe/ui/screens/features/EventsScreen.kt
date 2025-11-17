package com.example.univibe.ui.screens.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockEvents
import com.example.univibe.domain.models.*
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.ListScreen
import com.example.univibe.ui.templates.ListScreenConfig
import com.example.univibe.ui.screens.detail.EventDetailScreen
import com.example.univibe.ui.screens.create.CreateEventScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Events Screen using UniVibe Design System
 * Professional event discovery with filters, search, and beautiful cards
 */
object EventsScreen : Screen {
    @Composable
    override fun Content() {
        EventsScreenContent()
    }
}

// Event filter types
enum class EventFilter(val displayName: String, val icon: ImageVector) {
    ALL("All Events", Icons.Default.Event),
    UPCOMING("Upcoming", Icons.Default.Schedule),
    TODAY("Today", Icons.Default.Today),
    THIS_WEEK("This Week", Icons.Default.DateRange),
    POPULAR("Popular", Icons.Default.Trending),
    MY_EVENTS("My Events", Icons.Default.BookmarkBorder)
}

@Composable
private fun EventsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf(EventFilter.ALL) }
    var selectedCategory by remember { mutableStateOf<EventCategory?>(null) }
    
    // Load events
    LaunchedEffect(selectedFilter, selectedCategory) {
        isLoading = true
        delay(500) // Simulate network call
        
        events = when (selectedFilter) {
            EventFilter.ALL -> MockEvents.getEventsByFilter(selectedFilter)
            EventFilter.UPCOMING -> MockEvents.getEventsByFilter(selectedFilter).sortedBy { it.startDate }
            EventFilter.TODAY -> MockEvents.getEventsByFilter(selectedFilter).filter { 
                // Mock: show all events as "today" for demo
                true
            }
            EventFilter.THIS_WEEK -> MockEvents.getEventsByFilter(selectedFilter)
            EventFilter.POPULAR -> MockEvents.getEventsByFilter(selectedFilter).sortedByDescending { it.currentAttendees }
            EventFilter.MY_EVENTS -> MockEvents.getEventsByFilter(selectedFilter).take(3)
        }.let { filtered ->
            selectedCategory?.let { category ->
                filtered.filter { it.category == category }
            } ?: filtered
        }
        
        isLoading = false
    }
    
    val config = ListScreenConfig(
        title = "Campus Events",
        items = events,
        searchPlaceholder = "Search events...",
        emptyStateTitle = "No events found",
        emptyStateDescription = "Try adjusting your filters or check back later for new events",
        showSearch = true,
        showFilters = true
    )
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Custom header with stats
        EventsHeader(
            totalEvents = events.size,
            todayEvents = events.count { /* Mock today filter */ true },
            onBack = { navigator.pop() },
            onCreateEvent = { navigator.push(CreateEventScreen) }
        )
        
        // Filter tabs
        EventFilterTabs(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
        )
        
        // Category chips
        if (selectedFilter == EventFilter.ALL) {
            EventCategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
            )
        }
        
        // Events content
        if (isLoading) {
            UniVibeDesign.LoadingState(
                modifier = Modifier.weight(1f),
                message = "Loading events..."
            )
        } else {
            ListScreen(
                config = config,
                onItemClick = { event -> navigator.push(EventDetailScreen(event.id)) },
                itemContent = { event ->
                    EventCard(
                        event = event,
                        onClick = { navigator.push(EventDetailScreen(event.id)) }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navigator.push(CreateEventScreen) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Add, "Create Event")
                    }
                }
            )
        }
    }
}

/**
 * Custom events header with stats and actions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventsHeader(
    totalEvents: Int,
    todayEvents: Int,
    onBack: () -> Unit,
    onCreateEvent: () -> Unit
) {
    Surface(
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UniVibeDesign.Spacing.md)
        ) {
            // Top row with navigation and action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Text(
                        text = "Campus Events",
                        style = UniVibeDesign.Text.screenTitle()
                    )
                }
                
                IconButton(onClick = onCreateEvent) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Create Event",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Stats row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = UniVibeDesign.Spacing.xl),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EventStat(
                    number = totalEvents.toString(),
                    label = "Total Events",
                    icon = Icons.Default.Event
                )
                
                EventStat(
                    number = todayEvents.toString(),
                    label = "Today",
                    icon = Icons.Default.Today
                )
                
                EventStat(
                    number = "${(totalEvents * 0.3).toInt()}",
                    label = "Attending",
                    icon = Icons.Default.People
                )
            }
        }
    }
}

/**
 * Event statistic component
 */
@Composable
private fun EventStat(
    number: String,
    label: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = number,
            style = UniVibeDesign.Text.cardTitle().copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = UniVibeDesign.Text.caption()
        )
    }
}

/**
 * Event filter tabs
 */
@Composable
private fun EventFilterTabs(
    selectedFilter: EventFilter,
    onFilterSelected: (EventFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(vertical = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        items(EventFilter.entries) { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterSelected(filter) },
                label = { 
                    Text(
                        text = filter.displayName,
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        )
                    ) 
                },
                leadingIcon = {
                    Icon(
                        filter.icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                shape = UniVibeDesign.Buttons.pillShape
            )
        }
    }
}

/**
 * Event category chips
 */
@Composable
private fun EventCategoryChips(
    selectedCategory: EventCategory?,
    onCategorySelected: (EventCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(bottom = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        // All categories option
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All Categories") }
            )
        }
        
        items(EventCategory.entries) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.displayName) }
            )
        }
    }
}

/**
 * Enhanced event card with professional design
 */
@Composable
private fun EventCard(
    event: Event,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            // Event header with category and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = UniVibeDesign.Buttons.pillShape,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = event.category.displayName,
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(
                            horizontal = UniVibeDesign.Spacing.sm,
                            vertical = UniVibeDesign.Spacing.xs
                        )
                    )
                }
                
                Text(
                    text = formatEventDate(event.startDate),
                    style = UniVibeDesign.Text.caption(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Event image placeholder
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = UniVibeDesign.Cards.smallShape,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Event details
            Text(
                text = event.title,
                style = UniVibeDesign.Text.cardTitle(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = event.description,
                style = UniVibeDesign.Text.bodySecondary(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Event metadata
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = event.location.name,
                        style = UniVibeDesign.Text.caption()
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.People,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${event.currentAttendees} attending",
                        style = UniVibeDesign.Text.caption()
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Interest button
                OutlinedButton(
                    onClick = { /* TODO: Handle interest */ },
                    modifier = Modifier.height(32.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "Interested",
                        style = UniVibeDesign.Text.caption()
                    )
                }
            }
        }
    }
}

/**
 * Helper function to format event date
 */
private fun formatEventDate(timestamp: Long): String {
    // Mock implementation - replace with actual date formatting
    return "Today, 6:00 PM"
}