package com.example.univibe.ui.screens.studysessions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.components.UniVibeCard
import com.example.univibe.ui.components.UserAvatar
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.utils.UISymbols

/**
 * Data class representing a study session.
 *
 * @param id Unique identifier for the session
 * @param title Title/name of the study session
 * @param subject Subject or course being studied
 * @param hostName Name of the person hosting the session
 * @param hostAvatarUrl Optional URL for host's avatar
 * @param description Description of what will be studied
 * @param time Time of the session (e.g., "3:00 PM - 5:00 PM")
 * @param location Physical or virtual location (e.g., "Library Room 202", "Zoom")
 * @param difficulty Difficulty level (e.g., "Beginner", "Intermediate", "Advanced")
 * @param participantCount Current number of participants
 * @param maxParticipants Maximum participants allowed (null for unlimited)
 * @param isJoined Whether the current user has joined this session
 */
data class StudySessionItem(
    val id: String,
    val title: String,
    val subject: String,
    val hostName: String,
    val hostAvatarUrl: String? = null,
    val description: String,
    val time: String,
    val location: String,
    val difficulty: String,
    val participantCount: Int,
    val maxParticipants: Int? = null,
    val isJoined: Boolean = false
)

/**
 * Individual study session card component.
 * Shows session info, host details, time/location, and join button.
 *
 * @param item The study session item data
 * @param onJoinClick Callback when join/leave button is clicked
 * @param modifier Modifier to apply to the card
 */
@Composable
fun StudySessionCard(
    item: StudySessionItem,
    onJoinClick: (String, Boolean) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    var isJoined by remember { mutableStateOf(item.isJoined) }

    UniVibeCard(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            // Header with host info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                verticalAlignment = Alignment.Top
            ) {
                // Host avatar
                UserAvatar(
                    imageUrl = item.hostAvatarUrl,
                    size = Dimensions.AvatarSize.medium
                )

                // Host and session info
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
                ) {
                    // Session title
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Subject
                    Text(
                        text = item.subject,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Host name
                    Text(
                        text = "Hosted by ${item.hostName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Session details (time, location)
            SessionDetailRow(
                icon = UISymbols.ACCESS_TIME,
                label = item.time
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.sm))

            SessionDetailRow(
                icon = UISymbols.LOCATION,
                label = item.location
            )

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Difficulty and participants info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Difficulty badge
                Box(
                    modifier = Modifier
                        .background(
                            color = getDifficultyColor(item.difficulty),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                Dimensions.CornerRadius.small
                            )
                        )
                        .padding(
                            horizontal = Dimensions.Spacing.sm,
                            vertical = Dimensions.Spacing.xs
                        )
                ) {
                    Text(
                        text = item.difficulty,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                // Participants count
                Text(
                    text = if (item.maxParticipants != null) {
                        "${item.participantCount}/${item.maxParticipants} joined"
                    } else {
                        "${item.participantCount} joined"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.Spacing.md))

            // Join/Leave button
            PrimaryButton(
                text = if (isJoined) "✓ Joined" else "+ Join Session",
                onClick = {
                    isJoined = !isJoined
                    onJoinClick(item.id, isJoined)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            )
        }
    }
}

/**
 * Helper composable to display session details with icon.
 */
@Composable
private fun SessionDetailRow(
    icon: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextIcon(
            symbol = icon,
            contentDescription = null,
            fontSize = 16,
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * Get background color for difficulty badge.
 */
@Composable
private fun getDifficultyColor(difficulty: String) = when (difficulty) {
    "Beginner" -> MaterialTheme.colorScheme.secondaryContainer
    "Intermediate" -> MaterialTheme.colorScheme.tertiaryContainer
    "Advanced" -> MaterialTheme.colorScheme.errorContainer
    else -> MaterialTheme.colorScheme.surfaceVariant
}

/**
 * Vertical list of study session cards.
 *
 * @param sessions List of study session items to display
 * @param onJoinClick Callback when join/leave button is clicked
 * @param modifier Modifier to apply to the container
 */
@Composable
fun StudySessionCardList(
    sessions: List<StudySessionItem> = emptyList(),
    onJoinClick: (String, Boolean) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        sessions.forEach { session ->
            StudySessionCard(
                item = session,
                onJoinClick = onJoinClick
            )
        }
    }
}

/**
 * Convenience function to create sample study sessions for preview and testing.
 */
fun getSampleStudySessions(): List<StudySessionItem> = listOf(
    StudySessionItem(
        id = "session_1",
        title = "Calculus Exam Prep",
        subject = "MATH 101",
        hostName = "Sarah Johnson",
        description = "Final exam review focusing on integration and differentiation problems.",
        time = "3:00 PM - 5:00 PM",
        location = "Library Room 202",
        difficulty = "Intermediate",
        participantCount = 8,
        maxParticipants = 12,
        isJoined = false
    ),
    StudySessionItem(
        id = "session_2",
        title = "Organic Chemistry Discussion",
        subject = "CHEM 205",
        hostName = "Michael Chen",
        description = "Working through mechanism problems and reaction synthesis.",
        time = "4:30 PM - 6:00 PM",
        location = "Science Building Lab A",
        difficulty = "Advanced",
        participantCount = 5,
        maxParticipants = 8,
        isJoined = true
    ),
    StudySessionItem(
        id = "session_3",
        title = "Spanish Conversation Practice",
        subject = "SPAN 102",
        hostName = "Emma Rodriguez",
        description = "Casual conversation practice for intermediate Spanish learners.",
        time = "2:00 PM - 3:30 PM",
        location = "Zoom",
        difficulty = "Beginner",
        participantCount = 12,
        maxParticipants = null,
        isJoined = false
    ),
    StudySessionItem(
        id = "session_4",
        title = "Data Structures Deep Dive",
        subject = "CS 201",
        hostName = "Alex Morgan",
        description = "Exploring binary trees, heaps, and graph algorithms with code examples.",
        time = "6:00 PM - 7:30 PM",
        location = "Computer Lab C207",
        difficulty = "Advanced",
        participantCount = 6,
        maxParticipants = 10,
        isJoined = false
    ),
    StudySessionItem(
        id = "session_5",
        title = "Psychology Essay Writing",
        subject = "PSYC 110",
        hostName = "Jessica Lee",
        description = "Tips for writing effective psychology research essays.",
        time = "5:00 PM - 6:30 PM",
        location = "Student Center Room 305",
        difficulty = "Beginner",
        participantCount = 10,
        maxParticipants = 15,
        isJoined = false
    )
)

/**
 * Get list of all available subjects from sample sessions.
 */
fun getAvailableSubjects(): List<String> = listOf(
    "All Subjects",
    "MATH 101",
    "CHEM 205",
    "SPAN 102",
    "CS 201",
    "PSYC 110",
    "PHYS 150",
    "BIO 200"
)