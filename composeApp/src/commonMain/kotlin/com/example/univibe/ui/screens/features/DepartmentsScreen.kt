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
import com.example.univibe.data.mock.MockDepartments
import com.example.univibe.domain.models.*
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.ListScreen
import com.example.univibe.ui.templates.ListScreenConfig
import com.example.univibe.ui.screens.detail.DepartmentDetailScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Departments Screen using UniVibe Design System
 * Professional academic department discovery with filters and beautiful cards
 */
object DepartmentsScreen : Screen {
    @Composable
    override fun Content() {
        DepartmentsScreenContent()
    }
}

// Department filter types
enum class DepartmentFilter(val displayName: String, val icon: ImageVector) {
    ALL("All Departments", Icons.Default.School),
    STEM("STEM", Icons.Default.Science),
    LIBERAL_ARTS("Liberal Arts", Icons.Default.MenuBook),
    BUSINESS("Business", Icons.Default.Business),
    POPULAR("Popular", Icons.Default.Trending),
    MY_DEPARTMENTS("My Departments", Icons.Default.BookmarkBorder)
}

@Composable
private fun DepartmentsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var departments by remember { mutableStateOf<List<Department>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf(DepartmentFilter.ALL) }
    
    // Load departments
    LaunchedEffect(selectedFilter) {
        isLoading = true
        delay(500) // Simulate network call
        
        departments = when (selectedFilter) {
            DepartmentFilter.ALL -> MockDepartments.getDepartmentsByFilter(selectedFilter)
            DepartmentFilter.STEM -> MockDepartments.getDepartmentsByFilter(selectedFilter).filter { 
                it.name.contains("Engineering") || it.name.contains("Science") || it.name.contains("Math")
            }
            DepartmentFilter.LIBERAL_ARTS -> MockDepartments.getDepartmentsByFilter(selectedFilter).filter {
                it.name.contains("English") || it.name.contains("History") || it.name.contains("Art")
            }
            DepartmentFilter.BUSINESS -> MockDepartments.getDepartmentsByFilter(selectedFilter).filter {
                it.name.contains("Business") || it.name.contains("Economics")
            }
            DepartmentFilter.POPULAR -> MockDepartments.getDepartmentsByFilter(selectedFilter).sortedByDescending { it.studentCount }
            DepartmentFilter.MY_DEPARTMENTS -> MockDepartments.getDepartmentsByFilter(selectedFilter).take(2)
        }
        
        isLoading = false
    }
    
    val config = ListScreenConfig(
        title = "Academic Departments",
        items = departments,
        searchPlaceholder = "Search departments...",
        emptyStateTitle = "No departments found",
        emptyStateDescription = "Try adjusting your filters or explore academic programs",
        showSearch = true,
        showFilters = true
    )
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Custom header with stats
        DepartmentsHeader(
            totalDepartments = departments.size,
            myDepartments = departments.count { /* Mock enrollment */ kotlin.random.Random.nextBoolean() },
            onBack = { navigator.pop() }
        )
        
        // Filter tabs
        DepartmentFilterTabs(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
        )
        
        // Departments content
        if (isLoading) {
            UniVibeDesign.LoadingState(
                modifier = Modifier.weight(1f),
                message = "Loading departments..."
            )
        } else {
            ListScreen(
                config = config,
                onItemClick = { department -> navigator.push(DepartmentDetailScreen(department.id)) },
                itemContent = { department ->
                    DepartmentCard(
                        department = department,
                        onClick = { navigator.push(DepartmentDetailScreen(department.id)) }
                    )
                }
            )
        }
    }
}

/**
 * Custom departments header with stats and actions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepartmentsHeader(
    totalDepartments: Int,
    myDepartments: Int,
    onBack: () -> Unit
) {
    Surface(
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UniVibeDesign.Spacing.md)
        ) {
            // Top row with navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    text = "Academic Departments",
                    style = UniVibeDesign.Text.screenTitle()
                )
            }
            
            // Stats row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = UniVibeDesign.Spacing.xl),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DepartmentStat(
                    number = totalDepartments.toString(),
                    label = "Departments",
                    icon = Icons.Default.School
                )
                
                DepartmentStat(
                    number = myDepartments.toString(),
                    label = "My Programs",
                    icon = Icons.Default.BookmarkBorder
                )
                
                DepartmentStat(
                    number = "${(totalDepartments * 25).toInt()}",
                    label = "Faculty",
                    icon = Icons.Default.People
                )
            }
        }
    }
}

/**
 * Department statistic component
 */
@Composable
private fun DepartmentStat(
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
 * Department filter tabs
 */
@Composable
private fun DepartmentFilterTabs(
    selectedFilter: DepartmentFilter,
    onFilterSelected: (DepartmentFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(vertical = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        items(DepartmentFilter.entries) { filter ->
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
 * Enhanced department card with professional design
 */
@Composable
private fun DepartmentCard(
    department: Department,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            // Department header with category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = UniVibeDesign.Buttons.pillShape,
                    color = getDepartmentColor(department)
                ) {
                    Text(
                        text = getDepartmentCategory(department),
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = getDepartmentOnColor(department),
                        modifier = Modifier.padding(
                            horizontal = UniVibeDesign.Spacing.sm,
                            vertical = UniVibeDesign.Spacing.xs
                        )
                    )
                }
                
                if (department.studentCount > 500) {
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
            
            // Department icon placeholder
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
                        getDepartmentIcon(department),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Department details
            Text(
                text = department.name,
                style = UniVibeDesign.Text.cardTitle(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = department.description,
                style = UniVibeDesign.Text.bodySecondary(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Department metadata
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs)
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
                            text = "${department.studentCount} students",
                            style = UniVibeDesign.Text.caption()
                        )
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${department.facultyCount} faculty",
                            style = UniVibeDesign.Text.caption()
                        )
                    }
                }
                
                // Explore button
                OutlinedButton(
                    onClick = { /* TODO: Handle explore */ },
                    modifier = Modifier.height(36.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(
                        horizontal = UniVibeDesign.Spacing.md,
                        vertical = UniVibeDesign.Spacing.xs
                    )
                ) {
                    Text(
                        "Explore",
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

/**
 * Helper functions for department categorization
 */
@Composable
private fun getDepartmentColor(department: Department) = when {
    department.name.contains("Engineering") || department.name.contains("Science") -> 
        MaterialTheme.colorScheme.primaryContainer
    department.name.contains("Business") || department.name.contains("Economics") -> 
        MaterialTheme.colorScheme.secondaryContainer
    department.name.contains("Art") || department.name.contains("English") || department.name.contains("History") -> 
        MaterialTheme.colorScheme.tertiaryContainer
    else -> MaterialTheme.colorScheme.surfaceVariant
}

@Composable
private fun getDepartmentOnColor(department: Department) = when {
    department.name.contains("Engineering") || department.name.contains("Science") -> 
        MaterialTheme.colorScheme.onPrimaryContainer
    department.name.contains("Business") || department.name.contains("Economics") -> 
        MaterialTheme.colorScheme.onSecondaryContainer
    department.name.contains("Art") || department.name.contains("English") || department.name.contains("History") -> 
        MaterialTheme.colorScheme.onTertiaryContainer
    else -> MaterialTheme.colorScheme.onSurfaceVariant
}

private fun getDepartmentCategory(department: Department) = when {
    department.name.contains("Engineering") || department.name.contains("Science") || department.name.contains("Math") -> "STEM"
    department.name.contains("Business") || department.name.contains("Economics") -> "Business"
    department.name.contains("Art") || department.name.contains("English") || department.name.contains("History") -> "Liberal Arts"
    else -> "Academic"
}

private fun getDepartmentIcon(department: Department) = when {
    department.name.contains("Engineering") -> Icons.Default.Engineering
    department.name.contains("Science") -> Icons.Default.Science
    department.name.contains("Business") -> Icons.Default.Business
    department.name.contains("Art") -> Icons.Default.Palette
    department.name.contains("Math") -> Icons.Default.Functions
    department.name.contains("English") -> Icons.Default.MenuBook
    department.name.contains("History") -> Icons.Default.HistoryEdu
    else -> Icons.Default.School
}

