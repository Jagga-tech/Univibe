package com.example.univibe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univibe.ui.theme.BurgundyPrimary
import androidx.compose.foundation.horizontalScroll

/**
 * Quick action button for story creation toolbar
 */
@Composable
fun QuickActionButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(36.dp)
            .defaultMinSize(minWidth = 80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BurgundyPrimary.copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * Color palette selector for text and background colors
 */
@Composable
fun ColorPalette(
    selectedColor: String,
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = listOf(
        "#FFFFFF" to Color.White,
        "#FF0000" to Color(0xFFFF0000),
        "#FFFF00" to Color.Yellow,
        "#00FF00" to Color.Green,
        "#0000FF" to Color.Blue,
        "#FF00FF" to Color.Magenta,
        "#FF6B35" to Color(0xFFFF6B35),
        "#004E89" to Color(0xFF004E89)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        colors.forEach { (hexColor, composeColor) ->
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onColorSelected(hexColor) }
                    .background(
                        color = composeColor,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedColor == hexColor) {
                        Text(
                            "✓",
                            color = if (composeColor == Color.White) Color.Black else Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/**
 * Font size selector for text elements
 */
@Composable
fun FontSizeSelector(
    selectedSize: Float,
    onSizeSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val sizes = listOf(16f, 24f, 32f, 40f, 48f, 56f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .horizontalScroll(androidx.compose.foundation.rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sizes.forEach { size ->
            Button(
                onClick = { onSizeSelected(size) },
                modifier = Modifier
                    .height(36.dp)
                    .width(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedSize == size) BurgundyPrimary else Color.Gray.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(size.toInt().toString() + "px", fontSize = 10.sp)
            }
        }
    }
}

/**
 * Text alignment selector
 */
@Composable
fun TextAlignmentSelector(
    selectedAlignment: androidx.compose.ui.text.style.TextAlign,
    onAlignmentSelected: (androidx.compose.ui.text.style.TextAlign) -> Unit,
    modifier: Modifier = Modifier
) {
    val alignments = listOf(
        androidx.compose.ui.text.style.TextAlign.Left to "←",
        androidx.compose.ui.text.style.TextAlign.Center to "↔",
        androidx.compose.ui.text.style.TextAlign.Right to "→"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        alignments.forEach { (alignment, symbol) ->
            Button(
                onClick = { onAlignmentSelected(alignment) },
                modifier = Modifier
                    .height(40.dp)
                    .width(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedAlignment == alignment) BurgundyPrimary else Color.Gray.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(symbol, fontSize = 16.sp)
            }
        }
    }
}

/**
 * Preset story template selector
 */
@Composable
fun StoryTemplateSelector(
    onTemplateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val templates = listOf(
        "Minimal" to "Clean, simple design",
        "Vibrant" to "Bold, colorful theme",
        "Elegant" to "Professional look",
        "Fun" to "Playful, casual style"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        templates.forEach { (name, description) ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { onTemplateSelected(name) }
                    .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        description,
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}
