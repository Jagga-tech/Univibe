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
import com.example.univibe.data.mock.MockClubs
import com.example.univibe.domain.models.*
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.ListScreen
import com.example.univibe.ui.templates.ListScreenConfig
import com.example.univibe.ui.screens.detail.ClubDetailScreen
import com.example.univibe.ui.screens.create.CreateClubScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Clubs Screen using UniVibe Design System
 * Professional club discovery with categories, filters, and beautiful cards
 */
object ClubsScreen : Screen {
    @Composable
    override fun Content() {
        ClubsScreenContent()
    }
}

// Club filter types
enum class ClubFilter(val displayName: String, val icon: ImageVector) {
    ALL("All Clubs", Icons.Default.Groups),
    POPULAR("Popular", Icons.Default.Trending),
    NEW("New", Icons.Default.NewReleases),
    MY_CLUBS("My Clubs", Icons.Default.BookmarkBorder),
    RECOMMENDED("For You", Icons.Default.Recommend)
}

@Composable
private fun ClubsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var clubs by remember { mutableStateOf<List<Club>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf(ClubFilter.ALL) }
    var selectedCategory by remember { mutableStateOf<ClubCategory?>(null) }
    
    // Load clubs
    LaunchedEffect(selectedFilter, selectedCategory) {
        isLoading = true
        delay(500) // Simulate network call
        
        clubs = when (selectedFilter) {
            ClubFilter.ALL -> MockClubs.getClubsByFilter(selectedFilter)
            ClubFilter.POPULAR -> MockClubs.getClubsByFilter(selectedFilter).sortedByDescending { it.memberCount }
            ClubFilter.NEW -> MockClubs.getClubsByFilter(selectedFilter).sortedByDescending { it.createdAt }
            ClubFilter.MY_CLUBS -> MockClubs.getClubsByFilter(selectedFilter).take(3)
            ClubFilter.RECOMMENDED -> MockClubs.getClubsByFilter(selectedFilter).take(5)
        }.let { filtered ->
            selectedCategory?.let { category ->
                filtered.filter { it.category == category }
            } ?: filtered
        }
        
        isLoading = false
    }
    
    val config = ListScreenConfig(
        title = "Campus Clubs",
        items = clubs,
        searchPlaceholder = "Search clubs...",
        emptyStateTitle = "No clubs found",
        emptyStateDescription = "Try adjusting your filters or join a new club to get started",
        showSearch = true,
        showFilters = true
    )
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Custom header with stats
        ClubsHeader(
            totalClubs = clubs.size,
            myClubs = clubs.count { /* Mock membership */ kotlin.random.Random.nextBoolean() },
            onBack = { navigator.pop() },
            onCreateClub = { navigator.push(CreateClubScreen) }
        )
        
        // Filter tabs
        ClubFilterTabs(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
        )
        
        // Category chips
        if (selectedFilter == ClubFilter.ALL) {
            ClubCategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
            )
        }
        
        // Clubs content
        if (isLoading) {
            UniVibeDesign.LoadingState(
                modifier = Modifier.weight(1f),
                message = "Loading clubs..."
            )
        } else {
            ListScreen(
                config = config,
                onItemClick = { club -> navigator.push(ClubDetailScreen(club.id)) },
                itemContent = { club ->
                    ClubCard(
                        club = club,
                        onClick = { navigator.push(ClubDetailScreen(club.id)) }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navigator.push(CreateClubScreen) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Add, "Create Club")
                    }
                }
            )
        }
    }
}

/**
 * Custom clubs header with stats and actions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClubsHeader(
    totalClubs: Int,
    myClubs: Int,
    onBack: () -> Unit,
    onCreateClub: () -> Unit
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
                        text = "Campus Clubs",
                        style = UniVibeDesign.Text.screenTitle()
                    )
                }
                
                IconButton(onClick = onCreateClub) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Create Club",
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
                ClubStat(
                    number = totalClubs.toString(),
                    label = "Total Clubs",
                    icon = Icons.Default.Groups
                )
                
                ClubStat(
                    number = myClubs.toString(),
                    label = "My Clubs",
                    icon = Icons.Default.BookmarkBorder
                )
                
                ClubStat(
                    number = "${(totalClubs * 2.5).toInt()}",
                    label = "Members",
                    icon = Icons.Default.People
                )
            }
        }
    }
}

/**
 * Club statistic component
 */
@Composable
private fun ClubStat(
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
 * Club filter tabs
 */
@Composable
private fun ClubFilterTabs(
    selectedFilter: ClubFilter,
    onFilterSelected: (ClubFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(vertical = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        items(ClubFilter.entries) { filter ->
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
 * Club category chips
 */
@Composable
private fun ClubCategoryChips(
    selectedCategory: ClubCategory?,
    onCategorySelected: (ClubCategory?) -> Unit,
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
        
        items(ClubCategory.entries) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.displayName) }
            )
        }
    }
}

/**
 * Enhanced club card with professional design
 */
@Composable
private fun ClubCard(
    club: Club,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            // Club header with category and status
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
                        text = club.category.displayName,
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
                
                if (club.memberCount > 100) {
                    Surface(
                        shape = UniVibeDesign.Buttons.pillShape,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = UniVibeDesign.Spacing.sm,
                                vertical = UniVibeDesign.Spacing.xs
                            ),
                            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Trending,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Text(
                                text = "Popular",
                                style = UniVibeDesign.Text.caption().copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            }
            
            // Club logo placeholder
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
                        Icons.Default.Groups,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Club details
            Text(
                text = club.name,
                style = UniVibeDesign.Text.cardTitle(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = club.description,
                style = UniVibeDesign.Text.bodySecondary(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Club metadata and actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            text = "${club.memberCount} members",
                            style = UniVibeDesign.Text.caption()
                        )
                    }
                    
                    if (club.isActive) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Circle,
                                contentDescription = null,
                                modifier = Modifier.size(8.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Active",
                                style = UniVibeDesign.Text.caption(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                
                // Join/View button
                val isMember = kotlin.random.Random.nextBoolean() // Mock membership
                Button(
                    onClick = { /* TODO: Handle join/view */ },
                    modifier = Modifier.height(36.dp),
                    colors = if (isMember) {
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
                    Text(
                        if (isMember) "View" else "Join",
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

