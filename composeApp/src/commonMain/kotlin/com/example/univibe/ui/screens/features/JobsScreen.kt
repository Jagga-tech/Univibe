package com.example.univibe.ui.screens.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.theme.*
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.data.mock.*
import com.example.univibe.domain.models.*
import com.example.univibe.ui.screens.detail.JobDetailScreen

object JobsScreen : Screen {
    @Composable
    override fun Content() {
        JobsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var selectedType by remember { mutableStateOf<JobType?>(null) }
    var selectedCategory by remember { mutableStateOf<JobCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    
    val jobs = remember(selectedType, selectedCategory, searchQuery) {
        var filtered = MockJobs.getActiveJobs()
        if (selectedType != null) {
            filtered = filtered.filter { it.type == selectedType }
        }
        if (selectedCategory != null) {
            filtered = filtered.filter { it.category == selectedCategory }
        }
        if (searchQuery.isNotBlank()) {
            filtered = filtered.filter { job ->
                job.title.contains(searchQuery, ignoreCase = true) ||
                job.company.contains(searchQuery, ignoreCase = true) ||
                job.description.contains(searchQuery, ignoreCase = true)
            }
        }
        filtered
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campus Jobs") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isSearching = !isSearching }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
            contentPadding = PaddingValues(Dimensions.Spacing.default)
        ) {
            // Search Bar Section
            if (isSearching) {
                item {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search jobs...") },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { searchQuery = "" }
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimensions.Spacing.default),
                        singleLine = true
                    )
                }
            }
            
            // Type Filter
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                    Text(
                        "Job Type",
                        style = MaterialTheme.typography.labelLarge
                    )
                    TypeFilterRow(
                        selectedType = selectedType,
                        onTypeSelected = { selectedType = it }
                    )
                }
            }
            
            // Category Filter
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                    Text(
                        "Category",
                        style = MaterialTheme.typography.labelLarge
                    )
                    CategoryFilterRow(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it }
                    )
                }
            }
            
            // Jobs List Header
            item {
                Text(
                    "${jobs.size} job${if (jobs.size != 1) "s" else ""} available",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            // Jobs List
            if (jobs.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.Spacing.xl),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
                        ) {
                            Icon(
                                Icons.Default.Work,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "No jobs found",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "Try adjusting your filters",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(jobs) { job ->
                    JobCard(
                        job = job,
                        onClick = { navigator.push(JobDetailScreen(job.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TypeFilterRow(
    selectedType: JobType?,
    onTypeSelected: (JobType?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
    ) {
        // All types chip
        item {
            FilterChip(
                selected = selectedType == null,
                onClick = { onTypeSelected(null) },
                label = { Text("All") }
            )
        }
        
        // Type chips
        items(JobType.values().toList()) { type ->
            FilterChip(
                selected = type == selectedType,
                onClick = { onTypeSelected(type) },
                label = { Text(type.displayName) }
            )
        }
    }
}

@Composable
private fun CategoryFilterRow(
    selectedCategory: JobCategory?,
    onCategorySelected: (JobCategory?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
    ) {
        // All categories chip
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") }
            )
        }
        
        // Category chips
        items(JobCategory.values().toList()) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text("${category.emoji} ${category.displayName}") }
            )
        }
    }
}

@Composable
private fun JobCard(
    job: Job,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(Dimensions.Spacing.default),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
        ) {
            // Company and Type
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = job.company,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = job.type.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(
                            horizontal = Dimensions.Spacing.sm,
                            vertical = Dimensions.Spacing.xs
                        )
                    )
                }
            }
            
            // Job Title
            Text(
                text = job.title,
                style = MaterialTheme.typography.titleMedium
            )
            
            // Description
            Text(
                text = job.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            // Details Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)
            ) {
                // Location
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        if (job.isRemote) Icons.Default.Laptop else Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        if (job.isRemote) "Remote" else job.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Salary (if available)
                if (job.salary != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            Icons.Default.AttachMoney,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            job.salary,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            
            // Posted Date
            Text(
                "Posted ${formatJobDate(job.postedDate)}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatJobDate(timestamp: Long): String {
    val now = 1700000000000L // Use fixed time for consistency
    val diff = now - timestamp
    val days = diff / 86400000L
    
    return when {
        days == 0L -> "today"
        days == 1L -> "yesterday"
        days < 7 -> "$days days ago"
        days < 30 -> "${days / 7} weeks ago"
        else -> "${days / 30} months ago"
    }
}