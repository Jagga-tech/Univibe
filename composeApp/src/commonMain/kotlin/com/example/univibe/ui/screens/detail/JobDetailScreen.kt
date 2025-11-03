package com.example.univibe.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.univibe.util.ShareHelper

data class JobDetailScreen(val jobId: String) : Screen {
    @Composable
    override fun Content() {
        JobDetailScreenContent(jobId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobDetailScreenContent(jobId: String) {
    val navigator = LocalNavigator.currentOrThrow
    val job = remember { MockJobs.getJobById(jobId) }
    var isApplied by remember { mutableStateOf(false) }
    
    if (job == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Job Not Found") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Job not found")
            }
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(job.company) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        val shareText = ShareHelper.shareJob(job)
                        println("Share: $shareText")
                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 2.dp
            ) {
                Button(
                    onClick = {
                        MockJobs.applyForJob(job.id, "current_user")
                        isApplied = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.default),
                    enabled = !isApplied
                ) {
                    Text(if (isApplied) "Applied ✓" else "Apply Now")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Dimensions.Spacing.default),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)
        ) {
            // Job Header
            item {
                JobHeader(job = job)
            }
            
            // Quick Info
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.default),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)
                ) {
                    // Location
                    QuickInfoItem(
                        icon = if (job.isRemote) Icons.Default.Laptop else Icons.Default.Place,
                        label = if (job.isRemote) "Remote" else "Location",
                        value = if (job.isRemote) "Work from anywhere" else job.location,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Type
                    QuickInfoItem(
                        icon = Icons.Default.Work,
                        label = "Type",
                        value = job.type.displayName,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Salary
            if (job.salary != null) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                        modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                    ) {
                        Icon(
                            Icons.Default.AttachMoney,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Column {
                            Text("Salary", style = MaterialTheme.typography.labelMedium)
                            Text(job.salary, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
            
            // Description
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                    Text(
                        "About this job",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                    )
                    Text(
                        job.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                    )
                }
            }
            
            // Requirements
            if (job.requirements.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                        Text(
                            "Requirements",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                        )
                        job.requirements.forEach { requirement ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                                modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                            ) {
                                Text("•", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    requirement,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            
            // Responsibilities
            if (job.responsibilities.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                        Text(
                            "Responsibilities",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                        )
                        job.responsibilities.forEach { responsibility ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                                modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                            ) {
                                Text("•", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    responsibility,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            
            // Deadline
            if (job.deadline != null) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                        modifier = Modifier.padding(horizontal = Dimensions.Spacing.default)
                    ) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Column {
                            Text("Application Deadline", style = MaterialTheme.typography.labelMedium)
                            Text(
                                formatDeadline(job.deadline),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
            
            // Contact Info
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                        modifier = Modifier.padding(Dimensions.Spacing.default)
                    ) {
                        Text(
                            "Contact",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                job.contactEmail,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            
            // Additional spacing
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.xl))
            }
        }
    }
}

@Composable
private fun JobHeader(job: Job) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
    ) {
        // Company Logo
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.size(64.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(job.companyLogo ?: "🏢", style = MaterialTheme.typography.displaySmall)
            }
        }
        
        // Job Title
        Text(
            job.title,
            style = MaterialTheme.typography.headlineSmall
        )
        
        // Company
        Text(
            job.company,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
        // Category Chip
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                "${job.category.emoji} ${job.category.displayName}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(
                    horizontal = Dimensions.Spacing.sm,
                    vertical = Dimensions.Spacing.xs
                )
            )
        }
        
        // Posted Date
        Text(
            "Posted ${formatJobPostedDate(job.postedDate)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QuickInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
        modifier = modifier
            .padding(Dimensions.Spacing.default)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(label, style = MaterialTheme.typography.labelSmall)
        }
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

private fun formatJobPostedDate(timestamp: Long): String {
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

private fun formatDeadline(deadline: Long): String {
    // Simple date formatting without Java dependencies
    val daysFromEpoch = deadline / 86400000L
    return "In ${(daysFromEpoch - 19696) / 30} weeks"
}