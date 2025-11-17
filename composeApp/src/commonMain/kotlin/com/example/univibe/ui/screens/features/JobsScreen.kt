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
import com.example.univibe.data.mock.MockJobs
import com.example.univibe.domain.models.*
import com.example.univibe.ui.design.UniVibeDesign
import com.example.univibe.ui.templates.ListScreen
import com.example.univibe.ui.templates.ListScreenConfig
import com.example.univibe.ui.screens.detail.JobDetailScreen
import com.example.univibe.ui.screens.create.CreateJobScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modern Jobs Screen using UniVibe Design System
 * Professional job discovery with filters, salary ranges, and beautiful cards
 */
object JobsScreen : Screen {
    @Composable
    override fun Content() {
        JobsScreenContent()
    }
}

// Job filter types
enum class JobFilter(val displayName: String, val icon: ImageVector) {
    ALL("All Jobs", Icons.Default.Work),
    RECENT("Recent", Icons.Default.NewReleases),
    INTERNSHIPS("Internships", Icons.Default.School),
    PART_TIME("Part-time", Icons.Default.Schedule),
    FULL_TIME("Full-time", Icons.Default.BusinessCenter),
    REMOTE("Remote", Icons.Default.Home)
}

@Composable
private fun JobsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()
    
    var jobs by remember { mutableStateOf<List<Job>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedFilter by remember { mutableStateOf(JobFilter.ALL) }
    var selectedSalaryRange by remember { mutableStateOf<SalaryRange?>(null) }
    
    // Load jobs
    LaunchedEffect(selectedFilter, selectedSalaryRange) {
        isLoading = true
        delay(500) // Simulate network call
        
        jobs = when (selectedFilter) {
            JobFilter.ALL -> MockJobs.getJobsByFilter(selectedFilter)
            JobFilter.RECENT -> MockJobs.getJobsByFilter(selectedFilter).sortedByDescending { it.postedDate }
            JobFilter.INTERNSHIPS -> MockJobs.getJobsByFilter(selectedFilter).filter { it.type == JobType.INTERNSHIP }
            JobFilter.PART_TIME -> MockJobs.getJobsByFilter(selectedFilter).filter { it.type == JobType.PART_TIME }
            JobFilter.FULL_TIME -> MockJobs.getJobsByFilter(selectedFilter).filter { it.type == JobType.FULL_TIME }
            JobFilter.REMOTE -> MockJobs.getJobsByFilter(selectedFilter).filter { it.isRemote }
        }.let { filtered ->
            selectedSalaryRange?.let { range ->
                filtered.filter { it.salary in range.min..range.max }
            } ?: filtered
        }
        
        isLoading = false
    }
    
    val config = ListScreenConfig(
        title = "Campus Jobs",
        items = jobs,
        searchPlaceholder = "Search jobs...",
        emptyStateTitle = "No jobs available",
        emptyStateDescription = "Check back soon for new career opportunities",
        showSearch = true,
        showFilters = true
    )
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Custom header with stats
        JobsHeader(
            totalJobs = jobs.size,
            activeApplications = jobs.count { /* Mock application */ kotlin.random.Random.nextBoolean() },
            onBack = { navigator.pop() },
            onCreateJob = { navigator.push(CreateJobScreen) }
        )
        
        // Filter tabs
        JobFilterTabs(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it },
            modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
        )
        
        // Salary range chips
        if (selectedFilter == JobFilter.ALL) {
            SalaryRangeChips(
                selectedRange = selectedSalaryRange,
                onRangeSelected = { selectedSalaryRange = it },
                modifier = Modifier.padding(horizontal = UniVibeDesign.Spacing.md)
            )
        }
        
        // Jobs content
        if (isLoading) {
            UniVibeDesign.LoadingState(
                modifier = Modifier.weight(1f),
                message = "Loading jobs..."
            )
        } else {
            ListScreen(
                config = config,
                onItemClick = { job -> navigator.push(JobDetailScreen(job.id)) },
                itemContent = { job ->
                    JobCard(
                        job = job,
                        onClick = { navigator.push(JobDetailScreen(job.id)) }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navigator.push(CreateJobScreen) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Add, "Post Job")
                    }
                }
            )
        }
    }
}

// Salary ranges for filtering
data class SalaryRange(val min: Int, val max: Int, val displayName: String)

val salaryRanges = listOf(
    SalaryRange(0, 25, "Under $25/hr"),
    SalaryRange(25, 40, "$25-40/hr"),
    SalaryRange(40, 60, "$40-60/hr"),
    SalaryRange(60, Int.MAX_VALUE, "$60+/hr")
)

/**
 * Custom jobs header with stats and actions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobsHeader(
    totalJobs: Int,
    activeApplications: Int,
    onBack: () -> Unit,
    onCreateJob: () -> Unit
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
                        text = "Campus Jobs",
                        style = UniVibeDesign.Text.screenTitle()
                    )
                }
                
                IconButton(onClick = onCreateJob) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Post Job",
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
                JobStat(
                    number = totalJobs.toString(),
                    label = "Open Jobs",
                    icon = Icons.Default.Work
                )
                
                JobStat(
                    number = activeApplications.toString(),
                    label = "Applications",
                    icon = Icons.Default.Assignment
                )
                
                JobStat(
                    number = "${(totalJobs * 0.8).toInt()}",
                    label = "Hiring Now",
                    icon = Icons.Default.TrendingUp
                )
            }
        }
    }
}

/**
 * Job statistic component
 */
@Composable
private fun JobStat(
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
 * Job filter tabs
 */
@Composable
private fun JobFilterTabs(
    selectedFilter: JobFilter,
    onFilterSelected: (JobFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(vertical = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        items(JobFilter.entries) { filter ->
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
 * Salary range filter chips
 */
@Composable
private fun SalaryRangeChips(
    selectedRange: SalaryRange?,
    onRangeSelected: (SalaryRange?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(bottom = UniVibeDesign.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
        contentPadding = PaddingValues(horizontal = UniVibeDesign.Spacing.sm)
    ) {
        // All ranges option
        item {
            FilterChip(
                selected = selectedRange == null,
                onClick = { onRangeSelected(null) },
                label = { Text("All Salaries") }
            )
        }
        
        items(salaryRanges) { range ->
            FilterChip(
                selected = selectedRange == range,
                onClick = { onRangeSelected(range) },
                label = { Text(range.displayName) }
            )
        }
    }
}

/**
 * Enhanced job card with professional design
 */
@Composable
private fun JobCard(
    job: Job,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
        ) {
            // Job header with type and posted date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = UniVibeDesign.Buttons.pillShape,
                    color = when (job.type) {
                        JobType.FULL_TIME -> MaterialTheme.colorScheme.primaryContainer
                        JobType.PART_TIME -> MaterialTheme.colorScheme.secondaryContainer
                        JobType.INTERNSHIP -> MaterialTheme.colorScheme.tertiaryContainer
                        JobType.CONTRACT -> MaterialTheme.colorScheme.errorContainer
                    }
                ) {
                    Text(
                        text = job.type.displayName,
                        style = UniVibeDesign.Text.caption().copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = when (job.type) {
                            JobType.FULL_TIME -> MaterialTheme.colorScheme.onPrimaryContainer
                            JobType.PART_TIME -> MaterialTheme.colorScheme.onSecondaryContainer
                            JobType.INTERNSHIP -> MaterialTheme.colorScheme.onTertiaryContainer
                            JobType.CONTRACT -> MaterialTheme.colorScheme.onErrorContainer
                        },
                        modifier = Modifier.padding(
                            horizontal = UniVibeDesign.Spacing.sm,
                            vertical = UniVibeDesign.Spacing.xs
                        )
                    )
                }
                
                Text(
                    text = formatJobDate(job.postedDate),
                    style = UniVibeDesign.Text.caption(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Company logo placeholder
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = UniVibeDesign.Cards.smallShape,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Business,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
            
            // Job details
            Text(
                text = job.title,
                style = UniVibeDesign.Text.cardTitle(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = job.company,
                style = UniVibeDesign.Text.bodySecondary().copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = job.description,
                style = UniVibeDesign.Text.bodySecondary(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Job metadata
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
                            Icons.Default.AttachMoney,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "$${job.salary}/hr",
                            style = UniVibeDesign.Text.caption().copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xs),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (job.isRemote) Icons.Default.Home else Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = if (job.isRemote) "Remote" else job.location,
                            style = UniVibeDesign.Text.caption()
                        )
                    }
                }
                
                // Apply button
                val hasApplied = kotlin.random.Random.nextBoolean() // Mock application state
                Button(
                    onClick = { /* TODO: Handle apply */ },
                    modifier = Modifier.height(36.dp),
                    colors = if (hasApplied) {
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
                            if (hasApplied) Icons.Default.Check else Icons.Default.Send,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            if (hasApplied) "Applied" else "Apply",
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
 * Helper function to format job posting date
 */
private fun formatJobDate(timestamp: Long): String {
    // Mock implementation - replace with actual date formatting
    return "2 days ago"
}

