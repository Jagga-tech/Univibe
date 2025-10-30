package com.example.univibe.ui.screens.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.UniVibeTextField
import com.example.univibe.ui.theme.Dimensions

/**
 * Hub screen composable - discover and join campus groups and events.
 * Displays campus groups/clubs and upcoming campus events.
 *
 * @param groups List of campus groups to display
 * @param events List of campus events to display
 * @param onGroupJoinClick Callback when join/leave button is clicked on a group
 * @param onEventClick Callback when an event is clicked
 * @param onGroupClick Callback when a group item is clicked for navigation
 * @param onSearchClick Callback when search is performed
 * @param onFilterClick Callback when filter button is clicked
 * @param onBackClick Callback when back button is clicked
 */
@Composable
fun HubScreen(
    groups: List<GroupItem> = getSampleGroups(),
    events: List<EventItem> = getSampleEvents(),
    onGroupJoinClick: (String, Boolean) -> Unit = { _, _ -> },
    onEventClick: (String) -> Unit = {},
    onGroupClick: (String) -> Unit = {},
    onSearchClick: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Groups", "Events")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with search and filter
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
                    text = "Hub",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onFilterClick,
                    modifier = Modifier.size(Dimensions.IconSize.large)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter",
                        modifier = Modifier.size(Dimensions.IconSize.medium),
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
                placeholder = "Search groups, events...",
                leadingIcon = Icons.Default.Search,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                )
            }
        }

        // Content based on selected tab
        when (selectedTab) {
            0 -> GroupsTabContent(
                groups = groups,
                onGroupJoinClick = onGroupJoinClick
            )
            1 -> EventsTabContent(
                events = events,
                onEventClick = onEventClick
            )
        }
    }
}

/**
 * Groups tab content showing all campus groups.
 */
@Composable
private fun GroupsTabContent(
    groups: List<GroupItem>,
    onGroupJoinClick: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (groups.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No groups found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            item {
                GroupCardList(
                    groups = groups,
                    onJoinClick = onGroupJoinClick
                )
            }
        }

        // Bottom padding for navigation bar
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}

/**
 * Events tab content showing all campus events.
 */
@Composable
private fun EventsTabContent(
    events: List<EventItem>,
    onEventClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (events.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No events scheduled",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            item {
                EventCardList(
                    events = events,
                    onEventClick = onEventClick
                )
            }
        }

        // Bottom padding for navigation bar
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}