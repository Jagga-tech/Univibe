package com.example.univibe.ui.screens.studysessions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.ChipGroup
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.components.UniVibeTextField
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.theme.PlatformIcons
import com.example.univibe.ui.utils.UISymbols

/**
 * Study Sessions screen composable - find and join group study sessions.
 * Displays available study sessions with filtering by subject and search.
 *
 * @param sessions List of study sessions to display
 * @param onSessionJoinClick Callback when join/leave button is clicked on a session
 * @param onSearchClick Callback when search is performed
 * @param onCreateSessionClick Callback when create session button is clicked
 * @param onBackClick Callback when back button is clicked
 */
@Composable
fun StudySessionsScreen(
    sessions: List<StudySessionItem> = getSampleStudySessions(),
    onSessionJoinClick: (String, Boolean) -> Unit = { _, _ -> },
    onSearchClick: (String) -> Unit = {},
    onCreateSessionClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf("All Subjects") }
    val availableSubjects = getAvailableSubjects()

    // Filter sessions based on subject and search query
    val displaySessions = sessions.filter { session ->
        val matchesSubject = selectedSubject == "All Subjects" || session.subject == selectedSubject
        val matchesSearch = if (searchQuery.isEmpty()) {
            true
        } else {
            session.title.contains(searchQuery, ignoreCase = true) ||
            session.subject.contains(searchQuery, ignoreCase = true) ||
            session.description.contains(searchQuery, ignoreCase = true)
        }
        matchesSubject && matchesSearch
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with search and create button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(Dimensions.Spacing.md)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.Spacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Study Sessions",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onCreateSessionClick,
                    modifier = Modifier.size(Dimensions.IconSize.large)
                ) {
                    TextIcon(
                        symbol = UISymbols.ADD,
                        contentDescription = "Create study session",
                        fontSize = 20,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Search bar
            UniVibeTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    if (it.isNotEmpty()) {
                        onSearchClick(it)
                    }
                },
                placeholder = "Search sessions...",
                leadingIcon = PlatformIcons.Search,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Subject filter chips
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(Dimensions.Spacing.md)
        ) {
            Text(
                text = "Filter by Subject",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = Dimensions.Spacing.sm)
            )

            ChipGroup(
                items = availableSubjects,
                selectedItems = setOf(selectedSubject),
                onItemClick = { subject ->
                    selectedSubject = subject
                }
            )
        }

        // Study sessions list
        StudySessionsTabContent(
            sessions = displaySessions,
            onSessionJoinClick = onSessionJoinClick
        )
    }
}

/**
 * Tab content showing list of study sessions.
 */
@Composable
private fun StudySessionsTabContent(
    sessions: List<StudySessionItem>,
    onSessionJoinClick: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (sessions.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No study sessions found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            item {
                StudySessionCardList(
                    sessions = sessions,
                    onJoinClick = onSessionJoinClick
                )
            }
        }

        // Bottom padding for navigation bar
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}