package com.example.univibe.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.univibe.data.preferences.ThemePreferences
import com.example.univibe.domain.models.AppTheme
import com.example.univibe.domain.models.ThemePresets
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Screen for selecting and previewing themes.
 * Allows users to choose from preset themes or create custom themes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemePickerScreen(
    onBackClick: () -> Unit = {},
    onCustomThemeClick: () -> Unit = {}
) {
    val currentTheme by ThemePreferences.currentTheme.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose Theme") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        TextIcon(UISymbols.BACK, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onCustomThemeClick) {
                        TextIcon(UISymbols.PALETTE, contentDescription = "Create Custom Theme")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(Dimensions.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)
        ) {
            item {
                Text(
                    "Light Themes",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = Dimensions.Spacing.sm)
                )
            }
            
            items(ThemePresets.allPresets.filter { !it.isDark }) { theme ->
                ThemePreviewCard(
                    theme = theme,
                    isSelected = currentTheme.id == theme.id,
                    onClick = {
                        ThemePreferences.setTheme(theme)
                    }
                )
            }
            
            item {
                Text(
                    "Dark Themes",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = Dimensions.Spacing.default, bottom = Dimensions.Spacing.sm)
                )
            }
            
            items(ThemePresets.allPresets.filter { it.isDark }) { theme ->
                ThemePreviewCard(
                    theme = theme,
                    isSelected = currentTheme.id == theme.id,
                    onClick = {
                        ThemePreferences.setTheme(theme)
                    }
                )
            }
            
            if (ThemePreferences.hasCustomTheme()) {
                item {
                    Text(
                        "Custom Theme",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = Dimensions.Spacing.default, bottom = Dimensions.Spacing.sm)
                    )
                }
                
                item {
                    ThemePreferences.getCustomTheme()?.let { customTheme ->
                        ThemePreviewCard(
                            theme = customTheme,
                            isSelected = currentTheme.id == customTheme.id,
                            onClick = {
                                ThemePreferences.setTheme(customTheme)
                            },
                            showEditButton = true,
                            onEdit = onCustomThemeClick
                        )
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

@Composable
private fun ThemePreviewCard(
    theme: AppTheme,
    isSelected: Boolean,
    onClick: () -> Unit,
    showEditButton: Boolean = false,
    onEdit: (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 3.dp,
                        color = Color(theme.primary),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else Modifier
            ),
        color = Color(theme.surface),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = if (isSelected) 8.dp else 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.default),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color preview swatches
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    // Primary color
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(6.dp)),
                        color = Color(theme.primary)
                    ) {}
                    
                    // Secondary color
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(6.dp)),
                        color = Color(theme.secondary)
                    ) {}
                    
                    // Tertiary color
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(6.dp)),
                        color = Color(theme.tertiary)
                    ) {}
                }
                
                // Theme name
                Text(
                    text = theme.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (theme.isDark) Color.White else Color.Black,
                    modifier = Modifier.padding(horizontal = Dimensions.Spacing.sm)
                )
            }
            
            // Selection indicator or edit button
            if (showEditButton && onEdit != null) {
                IconButton(onClick = onEdit) {
                    TextIcon(
                        symbol = UISymbols.PALETTE,
                        contentDescription = "Edit Theme",
                        fontSize = 24,
                        tint = Color(theme.primary)
                    )
                }
            } else if (isSelected) {
                TextIcon(
                    symbol = UISymbols.CHECK,
                    contentDescription = "Selected",
                    fontSize = 24,
                    tint = Color(theme.primary)
                )
            }
        }
    }
}