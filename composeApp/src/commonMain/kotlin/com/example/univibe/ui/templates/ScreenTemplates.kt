package com.example.univibe.ui.templates

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.components.AsyncImage

/**
 * Screen Templates for UniVibe
 * Standardized patterns for different screen types
 */

// ═══════════════════════════════════════════════════════════════
// LIST SCREEN TEMPLATE - For Events, Clubs, Jobs, etc.
// ═══════════════════════════════════════════════════════════════

data class ListScreenConfig<T>(
    val title: String,
    val items: List<T>,
    val searchPlaceholder: String = "Search...",
    val emptyStateTitle: String = "No items found",
    val emptyStateDescription: String? = null,
    val showSearch: Boolean = true,
    val showFilters: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListScreen(
    config: ListScreenConfig<T>,
    onItemClick: (T) -> Unit = {},
    onBack: (() -> Unit)? = null,
    filters: @Composable () -> Unit = {},
    itemContent: @Composable (T) -> Unit,
    floatingActionButton: @Composable () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = config.title,
                        style = UniVibeDesign.Text.screenTitle()
                    ) 
                },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = floatingActionButton
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            if (config.showSearch) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(config.searchPlaceholder) },
                    leadingIcon = { Icon(Icons.Default.Search, "Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(UniVibeDesign.Spacing.md),
                    shape = UniVibeDesign.Cards.standardShape
                )
            }
            
            // Filters
            if (config.showFilters) {
                filters()
            }
            
            // Content
            if (config.items.isEmpty()) {
                UniVibeDesign.EmptyState(
                    modifier = Modifier.fillMaxSize(),
                    icon = { Icon(Icons.Default.Search, null, modifier = Modifier.size(64.dp)) },
                    title = config.emptyStateTitle,
                    description = config.emptyStateDescription
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(UniVibeDesign.Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
                ) {
                    items(config.items, key = { it.hashCode() }) { item ->
                        Box(modifier = Modifier.fillMaxWidth()) {
                            itemContent(item)
                        }
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// DETAIL SCREEN TEMPLATE - For Event Detail, Club Detail, etc.
// ═══════════════════════════════════════════════════════════════

data class DetailSection(
    val title: String,
    val content: @Composable () -> Unit
)

data class DetailAction(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val isPrimary: Boolean = false
)

data class DetailScreenConfig(
    val title: String,
    val subtitle: String? = null,
    val imageUrl: String? = null,
    val sections: List<DetailSection> = emptyList(),
    val actions: List<DetailAction> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    config: DetailScreenConfig,
    onBack: () -> Unit,
    content: LazyListScope.() -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty - title in content */ },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (config.actions.isNotEmpty()) {
                Surface(
                    tonalElevation = 3.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(UniVibeDesign.Spacing.md),
                        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
                    ) {
                        config.actions.forEach { action ->
                            if (action.isPrimary) {
                                Button(
                                    onClick = action.onClick,
                                    modifier = Modifier.weight(1f),
                                    colors = UniVibeDesign.Buttons.primaryColors()
                                ) {
                                    Icon(action.icon, contentDescription = null)
                                    Spacer(modifier = Modifier.width(UniVibeDesign.Spacing.xs))
                                    Text(action.label)
                                }
                            } else {
                                OutlinedButton(
                                    onClick = action.onClick,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(action.icon, contentDescription = null)
                                    Spacer(modifier = Modifier.width(UniVibeDesign.Spacing.xs))
                                    Text(action.label)
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Hero Image
            if (config.imageUrl != null) {
                item {
                    AsyncImage(
                        model = config.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            
            // Title Section
            item {
                Column(
                    modifier = Modifier.padding(UniVibeDesign.Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
                ) {
                    Text(
                        text = config.title,
                        style = UniVibeDesign.Text.screenTitle()
                    )
                    
                    config.subtitle?.let {
                        Text(
                            text = it,
                            style = UniVibeDesign.Text.bodySecondary()
                        )
                    }
                }
            }
            
            // Sections
            config.sections.forEach { section ->
                item {
                    UniVibeDesign.Section(
                        title = section.title
                    ) {
                        section.content()
                    }
                }
            }
            
            // Custom content
            content()
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// CREATION SCREEN TEMPLATE - For Create Event, Story, etc.
// ═══════════════════════════════════════════════════════════════

data class CreationStep(
    val title: String,
    val content: @Composable () -> Unit
)

data class CreationScreenConfig(
    val title: String,
    val steps: List<CreationStep>,
    val saveButtonText: String = "Create",
    val cancelButtonText: String = "Cancel"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreationScreen(
    config: CreationScreenConfig,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    isSaveEnabled: Boolean = true
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(config.title) },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel")
                    }
                },
                actions = {
                    TextButton(
                        onClick = onSave,
                        enabled = isSaveEnabled
                    ) {
                        Text(
                            text = config.saveButtonText,
                            style = UniVibeDesign.Text.buttonText(),
                            color = if (isSaveEnabled) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = UniVibeDesign.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.lg)
        ) {
            items(config.steps) { step ->
                UniVibeDesign.Section(
                    title = step.title
                ) {
                    step.content()
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// FEED SCREEN TEMPLATE - For Home, Profile Posts, etc.
// ═══════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FeedScreen(
    title: String? = null,
    items: List<T>,
    onRefresh: (() -> Unit)? = null,
    itemContent: @Composable (T) -> Unit,
    emptyStateContent: @Composable () -> Unit = {
        UniVibeDesign.EmptyState(
            icon = { Icon(Icons.Default.Feed, null, modifier = Modifier.size(64.dp)) },
            title = "No posts yet",
            description = "Be the first to share something!"
        )
    },
    floatingActionButton: @Composable () -> Unit = {}
) {
    Scaffold(
        topBar = {
            if (title != null) {
                TopAppBar(
                    title = { 
                        Text(
                            text = title,
                            style = UniVibeDesign.Text.screenTitle()
                        ) 
                    }
                )
            }
        },
        floatingActionButton = floatingActionButton
    ) { paddingValues ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                emptyStateContent()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(UniVibeDesign.Spacing.md),
                verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md)
            ) {
                items(items, key = { it.hashCode() }) { item ->
                    itemContent(item)
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// SETTINGS SCREEN TEMPLATE
// ═══════════════════════════════════════════════════════════════

data class SettingItem(
    val title: String,
    val description: String? = null,
    val icon: ImageVector? = null,
    val onClick: () -> Unit,
    val trailing: @Composable (() -> Unit)? = null
)

data class SettingSection(
    val title: String? = null,
    val items: List<SettingItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    title: String,
    sections: List<SettingSection>,
    onBack: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = title,
                        style = UniVibeDesign.Text.screenTitle()
                    ) 
                },
                navigationIcon = {
                    if (onBack != null) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(UniVibeDesign.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.lg)
        ) {
            sections.forEach { section ->
                item {
                    UniVibeDesign.Section(
                        title = section.title
                    ) {
                        section.items.forEach { item ->
                            UniVibeDesign.ListItemCard(
                                onClick = item.onClick,
                                leading = item.icon?.let { 
                                    { Icon(it, contentDescription = null) } 
                                },
                                title = item.title,
                                subtitle = item.description,
                                trailing = item.trailing
                            )
                        }
                    }
                }
            }
        }
    }
}