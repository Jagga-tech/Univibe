package com.example.univibe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import com.example.univibe.ui.theme.BrandColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReelQuickActionButton(
    text: String,
    icon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandColors.Burgundy,
            disabledContainerColor = Color.Gray
        ),
        enabled = enabled
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SpeedSelector(
    selectedSpeed: Float,
    onSpeedSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val speeds = listOf(0.5f, 0.75f, 1f, 1.25f, 1.5f, 2f)
    
    Column(modifier = modifier) {
        Text(
            "Speed",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(speeds) { speed ->
                SpeedButton(
                    speed = speed,
                    isSelected = selectedSpeed == speed,
                    onClick = { onSpeedSelected(speed) }
                )
            }
        }
    }
}

@Composable
private fun SpeedButton(
    speed: Float,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(56.dp)
            .height(40.dp)
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) BrandColors.Burgundy else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            ),
        color = if (isSelected) BrandColors.Burgundy.copy(alpha = 0.2f) else Color.DarkGray,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                "${speed}x",
                color = Color.White,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun FilterSelector(
    selectedFilters: List<String>,
    availableFilters: List<String>,
    onFilterSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Filters",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(availableFilters) { filter ->
                FilterButton(
                    name = filter,
                    isSelected = filter in selectedFilters,
                    onClick = { onFilterSelected(filter) }
                )
            }
        }
    }
}

@Composable
private fun FilterButton(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(40.dp)
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) BrandColors.Burgundy else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            ),
        color = if (isSelected) BrandColors.Burgundy.copy(alpha = 0.2f) else Color.DarkGray,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    name,
                    color = Color.White,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 12.sp
                )
                if (isSelected) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AdjustmentSlider(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange = -100..100,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(
                value.toString(),
                color = BrandColors.Burgundy,
                fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TextStylePicker(
    selectedStyle: String,
    onStyleSelected: (String) -> Unit,
    availableStyles: List<String> = listOf("MODERN", "BOLD", "ITALIC", "OUTLINED", "SHADOW", "CLASSIC"),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Text Style",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(availableStyles) { style ->
                TextStyleButton(
                    name = style,
                    isSelected = selectedStyle == style,
                    onClick = { onStyleSelected(style) }
                )
            }
        }
    }
}

@Composable
private fun TextStyleButton(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(40.dp)
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) BrandColors.Burgundy else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            ),
        color = if (isSelected) BrandColors.Burgundy.copy(alpha = 0.2f) else Color.DarkGray,
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                name,
                color = Color.White,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun TrimmingSlider(
    totalDuration: Int,
    trimStart: Float,
    trimEnd: Float,
    onTrimStartChanged: (Float) -> Unit,
    onTrimEndChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Trim Video",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Start: ${formatTime(trimStart.toInt())}s",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
            Text(
                "End: ${formatTime(trimEnd.toInt())}s",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
        
        Slider(
            value = trimStart,
            onValueChange = { if (it < trimEnd) onTrimStartChanged(it) },
            valueRange = 0f..totalDuration.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
        
        Slider(
            value = trimEnd,
            onValueChange = { if (it > trimStart) onTrimEndChanged(it) },
            valueRange = trimStart..totalDuration.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return if (mins > 0) {
        String.format("%d:%02d", mins, secs)
    } else {
        String.format("0:%02d", secs)
    }
}
