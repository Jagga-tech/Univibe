package com.example.univibe.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.ui.design.UniVibeDesign

/**
 * University selection screen with search functionality
 * Helps users find and select their academic institution
 */
object UniversitySelectScreen : Screen {
    @Composable
    override fun Content() {
        UniversitySelectContent()
    }
}

// University data model
data class University(
    val id: String,
    val name: String,
    val location: String,
    val studentCount: String,
    val logoUrl: String? = null
)

// Mock universities for demonstration
private val mockUniversities = listOf(
    University("1", "Stanford University", "Stanford, CA", "17,000 students"),
    University("2", "UC Berkeley", "Berkeley, CA", "45,000 students"),
    University("3", "MIT", "Cambridge, MA", "11,000 students"),
    University("4", "Harvard University", "Cambridge, MA", "23,000 students"),
    University("5", "UCLA", "Los Angeles, CA", "46,000 students"),
    University("6", "USC", "Los Angeles, CA", "47,000 students"),
    University("7", "NYU", "New York, NY", "51,000 students"),
    University("8", "Columbia University", "New York, NY", "33,000 students"),
    University("9", "University of Washington", "Seattle, WA", "48,000 students"),
    University("10", "Georgia Tech", "Atlanta, GA", "36,000 students"),
    University("11", "Carnegie Mellon University", "Pittsburgh, PA", "15,000 students"),
    University("12", "Cal Tech", "Pasadena, CA", "2,200 students")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UniversitySelectContent() {
    val navigator = LocalNavigator.currentOrThrow
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredUniversities = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            mockUniversities
        } else {
            mockUniversities.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.location.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Select Your University",
                        style = UniVibeDesign.Text.screenTitle()
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            "Back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(UniVibeDesign.Spacing.md),
                placeholder = { 
                    Text(
                        "Search universities...",
                        style = UniVibeDesign.Text.bodySecondary()
                    ) 
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                Icons.Default.Clear,
                                "Clear",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true,
                shape = UniVibeDesign.Cards.standardShape
            )
            
            // Results header
            if (filteredUniversities.isNotEmpty()) {
                Text(
                    text = "${filteredUniversities.size} universities found",
                    style = UniVibeDesign.Text.caption(),
                    modifier = Modifier.padding(
                        horizontal = UniVibeDesign.Spacing.md,
                        vertical = UniVibeDesign.Spacing.xs
                    )
                )
            }
            
            // Universities list
            if (filteredUniversities.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    UniVibeDesign.EmptyState(
                        icon = {
                            Icon(
                                Icons.Default.School,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        title = "No universities found",
                        description = "Try adjusting your search terms"
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(UniVibeDesign.Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.sm)
                ) {
                    items(filteredUniversities) { university ->
                        UniversityCard(
                            university = university,
                            onClick = {
                                // Navigate to registration with selected university
                                navigator.push(RegisterScreen(university))
                            }
                        )
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(UniVibeDesign.Spacing.lg))
                    }
                }
            }
        }
    }
}

/**
 * Individual university card component
 */
@Composable
private fun UniversityCard(
    university: University,
    onClick: () -> Unit
) {
    UniVibeDesign.StandardCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // University logo placeholder
                Surface(
                    modifier = Modifier.size(56.dp),
                    shape = UniVibeDesign.Cards.standardShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                
                // University info
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xxs)
                ) {
                    Text(
                        text = university.name,
                        style = UniVibeDesign.Text.cardTitle()
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xxs),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = university.location,
                                style = UniVibeDesign.Text.caption()
                            )
                        }
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(UniVibeDesign.Spacing.xxs),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.People,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = university.studentCount,
                                style = UniVibeDesign.Text.caption()
                            )
                        }
                    }
                }
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Select",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}