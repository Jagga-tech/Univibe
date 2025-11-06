package com.example.univibe.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * A composable that displays a Unicode symbol as an icon.
 * Replaces Material Icons for cross-platform KMP compatibility.
 */
@Composable
fun TextIcon(
    symbol: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = Color.Black,
    fontSize: Int = 24
) {
    Text(
        text = symbol,
        modifier = modifier,
        color = tint,
        style = TextStyle(fontSize = fontSize.sp),
        maxLines = 1
    )
}

/**
 * A larger text icon for primary buttons or important actions
 */
@Composable
fun LargeTextIcon(
    symbol: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = Color.Black
) {
    TextIcon(
        symbol = symbol,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
        fontSize = 32
    )
}

/**
 * A smaller text icon for secondary actions
 */
@Composable
fun SmallTextIcon(
    symbol: String,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    tint: Color = Color.Black
) {
    TextIcon(
        symbol = symbol,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
        fontSize = 18
    )
}