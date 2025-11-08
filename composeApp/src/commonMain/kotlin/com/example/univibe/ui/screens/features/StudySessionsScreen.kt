package com.example.univibe.ui.screens.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockStudySessions
import com.example.univibe.domain.models.SessionFilter
import com.example.univibe.domain.models.LocationType
import com.example.univibe.ui.screens.detail.StudySessionDetailScreen
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols
import kotlin.system.getTimeMillis

/**
 * Browse and filter study sessions screen.
 * Shows upcoming study sessions with filtering by various criteria.
 */
object StudySessionsScreen : Screen {
    @Composable
    override fun Content() {
        StudySessionsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudySessionsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var selectedFilter by remember { mutableStateOf(SessionFilter.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    
    // Get filtered sessions
    var displayedSessions by remember {
        mutableStateOf(MockStudySessions.getUpcomingSessions())
    }
    
    // Update displayed sessions when filter or search changes
    LaunchedEffect(selectedFilter, searchQuery) {
        displayedSessions = if (searchQuery.isNotEmpty()) {
            MockStudySessions.searchSessions(searchQuery)
        } else {
            MockStudySessions.getSessionsByFilter(selectedFilter)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Study Sessions") },
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
                    IconButton(onClick = { showFilters = !showFilters }) {
                        TextIcon(
                            symbol = UISymbols.FILTER_LIST,
                            contentDescription = "Filters",
                            fontSize = 20
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = Dimensions.Spacing.md)
        ) {
            // Search Bar
            item {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimensions.Spacing.md)
                )
            }
            
            // Filter Pills
            if (showFilters) {
                item {
                    FilterPills(
                        selectedFilter = selectedFilter,
                        onFilterSelected = { 
                            selectedFilter = it
                            showFilters = false
                        }
                    )
                }
            }
            
            // Sessions List
            if (displayedSessions.isEmpty()) {
                item {
                    EmptyState()
                }
            } else {
                items(displayedSessions) { session ->
                    StudySessionCard(
                        session = session,
                        onClick = {
                            navigator.push(StudySessionDetailScreen(sessionId = session.id))
                        }
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Search bar component for session search.
 */
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        placeholder = { Text("Search sessions...") },
        leadingIcon = { 
            TextIcon(
                symbol = UISymbols.SEARCH,
                fontSize = 16
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    TextIcon(
                        symbol = UISymbols.CLOSE,
                        fontSize = 16
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    )
}

/**
 * Filter pill selection component.
 */
@Composable
private fun FilterPills(
    selectedFilter: SessionFilter,
    onFilterSelected: (SessionFilter) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md)
    ) {
        Text(
            "Filters",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = Dimensions.Spacing.sm)
        )
        
        Wrap {
            SessionFilter.entries.forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { onFilterSelected(filter) },
                    label = { Text(filter.name.replace("_", " ")) },
                    modifier = Modifier.padding(end = Dimensions.Spacing.sm, bottom = Dimensions.Spacing.sm)
                )
            }
        }
    }
}

/**
 * Wrapper component for chip layout since FlowRow might not be available.
 */
@Composable
private fun Wrap(content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

/**
 * Study Session card for list display.
 */
@Composable
private fun StudySessionCard(
    session: com.example.univibe.domain.models.StudySession,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md, vertical = Dimensions.Spacing.sm)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            // Header: Title and Status Badge
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = session.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Capacity badge
                Surface(
                    color = if (session.isFull) 
                        MaterialTheme.colorScheme.errorContainer 
                    else 
                        MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        "${session.currentParticipants}/${session.maxParticipants}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(
                            horizontal = Dimensions.Spacing.sm,
                            vertical = Dimensions.Spacing.xs
                        ),
                        color = if (session.isFull)
                            MaterialTheme.colorScheme.onErrorContainer
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
            
            // Course and Subject
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    session.course,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "•",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    session.subject,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))
            
            // Description
            Text(
                session.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            
            // Location and Time Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Location
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    TextIcon(
                        symbol = UISymbols.LOCATION,
                        fontSize = 14,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        session.location.name,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Time
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextIcon(
                        symbol = UISymbols.ACCESS_TIME,
                        fontSize = 14,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        session.getDurationString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Host info
            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.extraSmall,
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        session.host.fullName.split(" ").mapNotNull { it.firstOrNull() }
                            .take(2).joinToString(""),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Hosted by",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        session.host.fullName,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Button(
                    onClick = { /* Join action */ },
                    modifier = Modifier.height(32.dp),
                    enabled = !session.isFull,
                    contentPadding = PaddingValues(horizontal = Dimensions.Spacing.md)
                ) {
                    Text(if (session.isJoined) "Joined" else "Join", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

/**
 * Empty state when no sessions found.
 */
@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            PlatformIcons.EventBusy,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = Dimensions.Spacing.md),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            "No Sessions Found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            "Try adjusting your filters or search",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}