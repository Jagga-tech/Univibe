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
import com.example.univibe.data.mock.MockStudySessions
import com.example.univibe.domain.models.*
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.ListScreen
import com.example.univibe.ui.templates.ListScreenConfig
import com.example.univibe.ui.screens.detail.StudySessionDetailScreen
import com.example.univibe.ui.screens.create.CreateStudySessionScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Study Sessions Screen using UniVibe Design System
 * Professional study session discovery with filters, time slots, and beautiful cards
 */
object StudySessionsScreen : Screen {
    @Composable
    override fun Content() {
        StudySessionsScreenContent()
    }
}

// Study session filter types
enum class StudySessionFilter(val displayName: String, val icon: ImageVector) {
    ALL("All Sessions", Icons.Default.MenuBook),
    TODAY("Today", Icons.Default.Today),
    THIS_WEEK("This Week", Icons.Default.DateRange),
    MY_SESSIONS("My Sessions", Icons.Default.BookmarkBorder),
    AVAILABLE("Available", Icons.Default.EventAvailable),
    POPULAR("Popular", Icons.Default.Trending)
}

@Composable
private fun StudySessionsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var studySessions by remember { mutableStateOf<List<StudySession>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf(StudySessionFilter.ALL) }
    var selectedSubject by remember { mutableStateOf<String?>(null) }
    
    // Load study sessions
    LaunchedEffect(selectedFilter, selectedSubject) {
        isLoading = true
        delay(500) // Simulate network call
        
        studySessions = when (selectedFilter) {
            StudySessionFilter.ALL -> MockStudySessions.getSessionsByFilter(selectedFilter)
            StudySessionFilter.TODAY -> MockStudySessions.getSessionsByFilter(selectedFilter).filter { 
                // Mock: show sessions as "today" for demo
                true
            }
            StudySessionFilter.THIS_WEEK -> MockStudySessions.getSessionsByFilter(selectedFilter)
            StudySessionFilter.MY_SESSIONS -> MockStudySessions.getSessionsByFilter(selectedFilter).filter { it.isJoined }
            StudySessionFilter.AVAILABLE -> MockStudySessions.getSessionsByFilter(selectedFilter).filter { !it.isFull }
            StudySessionFilter.POPULAR -> MockStudySessions.getSessionsByFilter(selectedFilter).sortedByDescending { it.currentParticipants }
        }.let { filtered ->
            selectedSubject?.let { subject ->
                filtered.filter { it.subject == subject }
            } ?: filtered
        }
        
        isLoading = false
    }
    
    val config = ListScreenConfig(
        title = "Study Sessions",
        items = studySessions,
        searchPlaceholder = "Search sessions...",
        emptyStateTitle = "No study sessions found",
        emptyStateDescription = "Try adjusting your filters or create a new session",
        showSearch = true,
        showFilters = true
    )
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Custom header with stats
        StudySessionsHeader(
            totalSessions = studySessions.size,
            activeSessions = studySessions.count { !it.isFull },
            onBack = { navigator.pop() },
            onCreateSession = { navigator.push(CreateStudySessionScreen) }
        )
        
        // Filter tabs
        StudySessionFilterTabs(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
        )
        
        // Subject chips
        if (selectedFilter == StudySessionFilter.ALL) {
            SubjectChips(
                selectedSubject = selectedSubject,
                onSubjectSelected = { selectedSubject = it },
                modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
            )
        }
        
        // Study sessions content
        if (isLoading) {
            UniVibeDesign.LoadingState(
                modifier = Modifier.weight(1f),
                message = "Loading study sessions..."
            )
        } else {
            ListScreen(
                config = config,
                onItemClick = { session -> navigator.push(StudySessionDetailScreen(sessionId = session.id)) },
                itemContent = { session ->
                    StudySessionCard(
                        session = session,
                        onClick = { navigator.push(StudySessionDetailScreen(sessionId = session.id)) }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navigator.push(CreateStudySessionScreen) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Add, "Create Session")
                    }
                }
            )
        }
    }
}

/**
 * Custom study sessions header with stats and actions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudySessionsHeader(
    totalSessions: Int,
    activeSessions: Int,
    onBack: () -> Unit,
    onCreateSession: () -> Unit
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
                        text = "Study Sessions",
                        style = UniVibeDesign.Text.screenTitle()
                    )
                }
                
                IconButton(onClick = onCreateSession) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Create Session",
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
                StudySessionStat(
                    number = totalSessions.toString(),
                    label = "Total Sessions",
                    icon = Icons.Default.MenuBook
                )
                
                StudySessionStat(
                    number = activeSessions.toString(),
                    label = "Available",
                    icon = Icons.Default.EventAvailable
                )
                
                StudySessionStat(
                    number = "${(totalSessions * 1.8).toInt()}",
                    label = "Participants",
                    icon = Icons.Default.People
                )
            }
        }
    }
}

/**
 * Study session statistic component
 */
@Composable
private fun StudySessionStat(
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
 * Study session filter tabs
 */
@Composable
private fun StudySessionFilterTabs(
    selectedFilter: StudySessionFilter,
    onFilterSelected: (StudySessionFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(vertical = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        items(StudySessionFilter.entries) { filter ->
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
 * Subject filter chips
 */
@Composable
private fun SubjectChips(
    selectedSubject: String?,
    onSubjectSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val subjects = listOf("Mathematics", "Computer Science", "Physics", "Chemistry", "Biology", "Economics")
    
    LazyRow(
        modifier = modifier.padding(bottom = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        // All subjects option
        item {
            FilterChip(
                selected = selectedSubject == null,
                onClick = { onSubjectSelected(null) },
                label = { Text("All Subjects") }
            )
        }
        
        items(subjects) { subject ->
            FilterChip(
                selected = selectedSubject == subject,
                onClick = { onSubjectSelected(subject) },
                label = { Text(subject) }
            )
        }
    }
}


/**
 * Enhanced study session card with professional design
 */
@Composable
private fun StudySessionCard(
    session: StudySession,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            // Session header with status and time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = UniVibeDesign.Buttons.pillShape,
                    color = if (session.isFull) 
                        MaterialTheme.colorScheme.errorContainer 
                    else 
                        MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "${session.currentParticipants}/${session.maxParticipants} spots",
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = if (session.isFull)
                            MaterialTheme.colorScheme.onErrorContainer
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(
                            horizontal = UniVibeDesign.Spacing.sm,
                            vertical = UniVibeDesign.Spacing.xs
                        )
                    )
                }
                
                Text(
                    text = formatSessionTime(session.startDate),
                    style = UniVibeDesign.Text.caption(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            // Subject icon placeholder
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = UniVibeDesign.Cards.smallShape,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        getSubjectIcon(session.subject),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Session details
            Text(
                text = session.title,
                style = UniVibeDesign.Text.cardTitle(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = session.course,
                    style = UniVibeDesign.Text.bodySecondary().copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "•",
                    style = UniVibeDesign.Text.bodySecondary()
                )
                Text(
                    text = session.subject,
                    style = UniVibeDesign.Text.bodySecondary()
                )
            }
            
            Text(
                text = session.description,
                style = UniVibeDesign.Text.bodySecondary(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Session metadata
            Column(
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
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
                        text = session.location.name,
                        style = UniVibeDesign.Text.caption()
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = session.getDurationString(),
                        style = UniVibeDesign.Text.caption()
                    )
                }
            }
            
            // Host and join section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = MaterialTheme.shapes.extraSmall,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                session.host.fullName.split(" ").mapNotNull { it.firstOrNull() }
                                    .take(2).joinToString(""),
                                style = UniVibeDesign.Text.caption().copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    
                    Column {
                        Text(
                            text = "Hosted by",
                            style = UniVibeDesign.Text.caption()
                        )
                        Text(
                            text = session.host.fullName,
                            style = UniVibeDesign.Text.caption().copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
                
                // Join button
                Button(
                    onClick = { /* TODO: Handle join */ },
                    modifier = Modifier.height(36.dp),
                    enabled = !session.isFull,
                    colors = if (session.isJoined) {
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    } else {
                        UniVibeDesign.Buttons.primaryColors()
                    },
                    contentPadding = PaddingValues(
                        horizontal = UniVibeDesign.Spacing.md,
                        vertical = UniVibeDesign.Spacing.xs
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (session.isJoined) Icons.Default.Check else if (session.isFull) Icons.Default.Block else Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            when {
                                session.isFull -> "Full"
                                session.isJoined -> "Joined"
                                else -> "Join"
                            },
                            style = UniVibeDesign.Text.caption().copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Helper functions for study sessions
 */
private fun getSubjectIcon(subject: String) = when {
    subject.contains("Math") -> Icons.Default.Functions
    subject.contains("Computer") || subject.contains("Programming") -> Icons.Default.Computer
    subject.contains("Physics") -> Icons.Default.Science
    subject.contains("Chemistry") -> Icons.Default.Science
    subject.contains("Biology") -> Icons.Default.Biotech
    subject.contains("Economics") || subject.contains("Business") -> Icons.Default.TrendingUp
    subject.contains("History") -> Icons.Default.HistoryEdu
    subject.contains("English") || subject.contains("Literature") -> Icons.Default.MenuBook
    else -> Icons.Default.School
}

private fun formatSessionTime(timestamp: Long): String {
    // Mock implementation - replace with actual time formatting
    return "Today, 3:00 PM"
}

