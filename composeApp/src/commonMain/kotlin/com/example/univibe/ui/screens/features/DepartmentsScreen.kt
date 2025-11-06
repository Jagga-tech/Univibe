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
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols
import com.example.univibe.ui.screens.detail.DepartmentDetailScreen

object DepartmentsScreen : Screen {
    @Composable
    override fun Content() {
        DepartmentsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepartmentsScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    var selectedCategory by remember { mutableStateOf<DepartmentCategory?>(null) }
    
    val departments = remember(selectedCategory) {
        if (selectedCategory != null) {
            MockDepartments.getDepartmentsByCategory(selectedCategory!!)
        } else {
            MockDepartments.getAllDepartments()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Departments") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Search departments */ }) {
                        Icon(Icons.Default.Search, "Search")
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
            // Category Filter
            item {
                CategoryFilterRow(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
            
            // Departments List Header
            item {
                Text(
                    "All Departments",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            // Departments List
            if (departments.isEmpty()) {
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
                                Icons.Default.School,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "No departments found",
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
                items(departments) { department ->
                    DepartmentCard(
                        department = department,
                        onClick = { navigator.push(DepartmentDetailScreen(department.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterRow(
    selectedCategory: DepartmentCategory?,
    onCategorySelected: (DepartmentCategory?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
        contentPadding = PaddingValues(horizontal = Dimensions.Spacing.default)
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("All") }
            )
        }
        
        items(DepartmentCategory.values().toList()) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text("${category.emoji} ${category.displayName}") }
            )
        }
    }
}

@Composable
private fun DepartmentCard(
    department: Department,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(Dimensions.Spacing.default),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)
        ) {
            Surface(
                modifier = Modifier.size(60.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        department.category.emoji,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                Text(
                    text = department.name,
                    style = MaterialTheme.typography.titleMedium
                )
                
                Text(
                    text = department.abbreviation,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = department.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        department.building,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
