package com.example.univibe.ui.screens.settings

import com.example.univibe.util.getCurrentTimeMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
 * Screen for creating and editing custom themes.
 * Allows users to pick custom colors for primary, secondary, tertiary, background, surface, and error.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomThemeScreen(
    onBackClick: () -> Unit = {}
) {
    var themeName by remember { mutableStateOf("My Custom Theme") }
    var isDarkMode by remember { mutableStateOf(false) }
    
    // Color pickers - using ARGB Long format
    var primaryColor by remember { mutableStateOf(0xFF6200EE) }
    var secondaryColor by remember { mutableStateOf(0xFF03DAC6) }
    var tertiaryColor by remember { mutableStateOf(0xFF018786) }
    var backgroundColor by remember { mutableStateOf(if (isDarkMode) 0xFF121212 else 0xFFFFFFFF) }
    var surfaceColor by remember { mutableStateOf(if (isDarkMode) 0xFF1E1E1E else 0xFFF5F5F5) }
    var errorColor by remember { mutableStateOf(0xFFB00020) }
    
    val scrollState = rememberScrollState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Custom Theme") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        TextIcon(UISymbols.BACK, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.Spacing.default),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.default)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = {
                        val customTheme = AppTheme(
                            id = "custom_${getCurrentTimeMillis()}",
                            name = themeName,
                            primary = primaryColor,
                            secondary = secondaryColor,
                            tertiary = tertiaryColor,
                            background = backgroundColor,
                            surface = surfaceColor,
                            error = errorColor,
                            isDark = isDarkMode,
                            isCustom = true
                        )
                        ThemePreferences.saveCustomTheme(customTheme)
                        onBackClick()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Text("Save Theme")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(Dimensions.Spacing.default),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.lg)
        ) {
            // Theme name input
            OutlinedTextField(
                value = themeName,
                onValueChange = { themeName = it },
                label = { Text("Theme Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            // Dark mode toggle
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.Spacing.default),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Dark Mode",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = {
                            isDarkMode = it
                            // Update background and surface colors based on mode
                            backgroundColor = if (it) 0xFF121212 else 0xFFFFFFFF
                            surfaceColor = if (it) 0xFF1E1E1E else 0xFFF5F5F5
                        }
                    )
                }
            }
            
            Divider()
            
            Text(
                "Color Palette",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = Dimensions.Spacing.sm)
            )
            
            // Color pickers
            ColorPickerItem(
                label = "Primary Color",
                color = primaryColor,
                onColorChange = { primaryColor = it }
            )
            
            ColorPickerItem(
                label = "Secondary Color",
                color = secondaryColor,
                onColorChange = { secondaryColor = it }
            )
            
            ColorPickerItem(
                label = "Tertiary Color",
                color = tertiaryColor,
                onColorChange = { tertiaryColor = it }
            )
            
            ColorPickerItem(
                label = "Background Color",
                color = backgroundColor,
                onColorChange = { backgroundColor = it }
            )
            
            ColorPickerItem(
                label = "Surface Color",
                color = surfaceColor,
                onColorChange = { surfaceColor = it }
            )
            
            ColorPickerItem(
                label = "Error Color",
                color = errorColor,
                onColorChange = { errorColor = it }
            )
            
            Divider(modifier = Modifier.padding(top = Dimensions.Spacing.lg))
            
            Text(
                "Preview",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = Dimensions.Spacing.sm)
            )
            
            // Preview card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                color = Color(surfaceColor),
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(Dimensions.Spacing.default),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                ) {
                    // Color swatches
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            color = Color(primaryColor)
                        ) {}
                        
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            color = Color(secondaryColor)
                        ) {}
                        
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            color = Color(tertiaryColor)
                        ) {}
                    }
                    
                    // Theme info
                    Column(
                        modifier = Modifier.padding(Dimensions.Spacing.sm),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = themeName,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (isDarkMode) Color.White else Color.Black
                        )
                        Text(
                            text = if (isDarkMode) "Dark Theme" else "Light Theme",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isDarkMode) Color(0xFFB0B0B0) else Color(0xFF808080)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(Dimensions.Spacing.xl))
        }
    }
}

@Composable
private fun ColorPickerItem(
    label: String,
    color: Long,
    onColorChange: (Long) -> Unit
) {
    var showColorPicker by remember { mutableStateOf(false) }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showColorPicker = !showColorPicker }
                .padding(Dimensions.Spacing.sm),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
                color = Color(color),
                shape = RoundedCornerShape(8.dp)
            ) {}
        }
        
        if (showColorPicker) {
            SimpleColorPicker(
                selectedColor = color,
                onColorSelected = {
                    onColorChange(it)
                    showColorPicker = false
                }
            )
        }
    }
}

@Composable
private fun SimpleColorPicker(
    selectedColor: Long,
    onColorSelected: (Long) -> Unit
) {
    val predefinedColors = listOf(
        0xFF6200EE, 0xFF3700B3, 0xFF03DAC6, 0xFF018786,
        0xFFBB86FC, 0xFF6200EE, 0xFF7DD3C0, 0xFFCF6679,
        0xFFB3261E, 0xFF9575CD, 0xFFFF6B35, 0xFFFFB627,
        0xFF2E7D32, 0xFF66BB6A, 0xFF7E57C2, 0xFFBA68C8,
        0xFFD32F2F, 0xFFE57373, 0xFFF9A825, 0xFFFFD54F,
        0xFF0077BE, 0xFF4DD0E1, 0xFF1F1F1F, 0xFFFFFFFF
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.default),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
    ) {
        Text(
            "Select Color",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        // Grid of colors
        repeat((predefinedColors.size + 3) / 4) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm)
            ) {
                repeat(4) { colIndex ->
                    val index = rowIndex * 4 + colIndex
                    if (index < predefinedColors.size) {
                        val colorValue = predefinedColors[index]
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onColorSelected(colorValue) }
                                .then(
                                    if (selectedColor == colorValue) {
                                        Modifier.border(3.dp, Color.Black, RoundedCornerShape(8.dp))
                                    } else Modifier
                                ),
                            color = Color(colorValue),
                            shape = RoundedCornerShape(8.dp),
                            shadowElevation = 2.dp
                        ) {}
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}