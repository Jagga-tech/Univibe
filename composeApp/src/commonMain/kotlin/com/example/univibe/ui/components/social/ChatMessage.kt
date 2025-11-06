package com.example.univibe.ui.components.social

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.univibe.domain.models.Message
import com.example.univibe.domain.models.User
import com.example.univibe.ui.theme.Dimensions

@Composable
fun ChatMessage(
    message: Message,
    isCurrentUser: Boolean,
    modifier: Modifier = Modifier
) {
    val bubbleShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if (isCurrentUser) 16.dp else 4.dp,
        bottomEnd = if (isCurrentUser) 4.dp else 16.dp
    )
    
    val bubbleColor = if (isCurrentUser)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.surfaceVariant
    
    val textColor = if (isCurrentUser)
        MaterialTheme.colorScheme.onPrimary
    else
        MaterialTheme.colorScheme.onSurface
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md, vertical = 4.dp),
        contentAlignment = if (isCurrentUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Column {
            Surface(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .background(bubbleColor, shape = bubbleShape),
                color = bubbleColor,
                shape = bubbleShape
            ) {
                Text(
                    text = message.content,
                    modifier = Modifier.padding(Dimensions.Spacing.md),
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
            
            Text(
                text = formatMessageTime(message.createdAt),
                modifier = Modifier
                    .padding(horizontal = Dimensions.Spacing.md, vertical = 4.dp)
                    .align(if (isCurrentUser) Alignment.End else Alignment.Start),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatMessageTime(createdAt: Long): String {
    val currentTime = System.currentTimeMillis()
    val differenceInMillis = currentTime - createdAt
    
    return when {
        differenceInMillis < 60000 -> "now"
        differenceInMillis < 3600000 -> "${differenceInMillis / 60000}m ago"
        differenceInMillis < 86400000 -> "${differenceInMillis / 3600000}h ago"
        else -> formatDateAsMonthDayForMessage(createdAt)
    }
}

private fun formatDateAsMonthDayForMessage(timestamp: Long): String {
    val totalSeconds = timestamp / 1000
    val totalMinutes = totalSeconds / 60
    val totalHours = totalMinutes / 60
    val totalDays = totalHours / 24
    
    val daysPerYear = 365
    val yearsSinceEpoch = totalDays / daysPerYear
    val year = 1970 + yearsSinceEpoch.toInt()
    
    val dayOfYear = (totalDays % daysPerYear).toInt() + 1
    
    val isLeapYear = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    val daysInMonth = intArrayOf(31, if (isLeapYear) 29 else 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    
    var monthOfYear = 1
    var dayOfMonth = dayOfYear
    for (i in daysInMonth.indices) {
        if (dayOfMonth <= daysInMonth[i]) {
            monthOfYear = i + 1
            break
        }
        dayOfMonth -= daysInMonth[i]
    }
    
    val month = monthOfYear.toString().padStart(2, '0')
    val day = dayOfMonth.toString().padStart(2, '0')
    return "$month/$day"
}

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surface,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(0.dp),
    content: @Composable () -> Unit
) {
    androidx.compose.material3.Surface(
        modifier = modifier,
        color = color,
        shape = shape,
        content = content
    )
}