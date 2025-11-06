package com.example.univibe.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
import com.example.univibe.util.ShareHelper

data class DepartmentDetailScreen(val departmentId: String) : Screen {
    @Composable
    override fun Content() {
        DepartmentDetailScreenContent(departmentId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepartmentDetailScreenContent(departmentId: String) {
    val navigator = LocalNavigator.currentOrThrow
    val department = remember { MockDepartments.getDepartmentById(departmentId) }
    
    if (department == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Department Not Found") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            TextIcon(
                                symbol = UISymbols.BACK,
                                contentDescription = "Back",
                                fontSize = 20
                            )
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
                Text("Department not found")
            }
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(department.name) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(
                            symbol = UISymbols.BACK,
                            contentDescription = "Back",
                            fontSize = 20
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        val shareText = ShareHelper.shareDepartment(department)
                        println("Share: $shareText")
                    }) {
                        TextIcon(
                            symbol = UISymbols.SHARE,
                            contentDescription = "Share",
                            fontSize = 20
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Dimensions.Spacing.default),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.lg)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                department.category.emoji,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimensions.Spacing.default))
                    
                    Text(
                        text = department.name,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                    
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = department.abbreviation,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(
                                horizontal = Dimensions.Spacing.sm,
                                vertical = Dimensions.Spacing.xs
                            )
                        )
                    }
                }
            }
            
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)) {
                    Text(
                        "About",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = department.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            
            item {
                Column(verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)) {
                    Text(
                        "Details",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    DepartmentInfoRow(
                        icon = Icons.Default.Place,
                        label = "Location",
                        value = department.building
                    )
                    
                    DepartmentInfoRow(
                        icon = Icons.Default.Person,
                        label = "Department Chair",
                        value = department.chair.fullName,
                        onClick = { /* TODO: Navigate to user profile */ }
                    )
                    
                    if (department.website != null) {
                        DepartmentInfoRow(
                            icon = Icons.Default.Language,
                            label = "Website",
                            value = department.website,
                            onClick = { /* TODO: Open website */ }
                        )
                    }
                    
                    if (department.email != null) {
                        DepartmentInfoRow(
                            icon = Icons.Default.Email,
                            label = "Email",
                            value = department.email,
                            onClick = { /* TODO: Send email */ }
                        )
                    }
                    
                    if (department.phone != null) {
                        DepartmentInfoRow(
                            icon = Icons.Default.Phone,
                            label = "Phone",
                            value = department.phone,
                            onClick = { /* TODO: Call */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DepartmentInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
        verticalAlignment = Alignment.Top,
        modifier = if (onClick != null) {
            Modifier.clickable { onClick() }
        } else Modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = if (onClick != null) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}