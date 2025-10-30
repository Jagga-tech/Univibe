package com.example.univibe.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
 * Messages screen composable - view and manage conversations.
 * Displays list of conversations with search and filtering capabilities.
 *
 * @param conversations List of conversations to display
 * @param onConversationClick Callback when a conversation is clicked
 * @param onSearchClick Callback when search is performed
 * @param onComposeClick Callback when compose/new message button is clicked
 * @param onBackClick Callback when back button is clicked
 */
@Composable
fun MessagesScreen(
    conversations: List<ConversationItem> = getSampleConversations(),
    onConversationClick: (String) -> Unit = {},
    onSearchClick: (String) -> Unit = {},
    onComposeClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("All", "Unread")

    // Filter conversations based on selected tab
    val filteredConversations = when (selectedTab) {
        0 -> conversations // All conversations
        1 -> conversations.filter { it.unreadCount > 0 } // Only unread
        else -> conversations
    }

    // Further filter by search query
    val displayConversations = if (searchQuery.isNotEmpty()) {
        filteredConversations.filter {
            it.userName.contains(searchQuery, ignoreCase = true) ||
            it.lastMessage.contains(searchQuery, ignoreCase = true)
        }
    } else {
        filteredConversations
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with search and compose button
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
                    text = "Messages",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onComposeClick,
                    modifier = Modifier.size(Dimensions.IconSize.large)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Compose message",
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
                placeholder = "Search conversations...",
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

        // Conversations list
        ConversationsTabContent(
            conversations = displayConversations,
            onConversationClick = onConversationClick
        )
    }
}

/**
 * Tab content showing list of conversations.
 */
@Composable
private fun ConversationsTabContent(
    conversations: List<ConversationItem>,
    onConversationClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (conversations.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.lg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No conversations found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            item {
                ConversationCardList(
                    conversations = conversations,
                    onConversationClick = onConversationClick
                )
            }
        }

        // Bottom padding for navigation bar
        item {
            Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
        }
    }
}