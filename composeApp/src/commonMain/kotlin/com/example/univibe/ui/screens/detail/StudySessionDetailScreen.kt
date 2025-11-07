package com.example.univibe.ui.screens.detail

import com.example.univibe.util.getCurrentTimeMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockStudySessions
import com.example.univibe.ui.theme.Dimensions
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.utils.UISymbols
import com.example.univibe.util.ShareHelper

/**
 * Detail screen for a specific study session.
 * Shows full session information, participants, and join/leave functionality.
 */
data class StudySessionDetailScreen(val sessionId: String) : Screen {
    @Composable
    override fun Content() {
        StudySessionDetailScreenContent(sessionId)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StudySessionDetailScreenContent(sessionId: String) {
    val navigator = LocalNavigator.currentOrThrow
    
    val session = remember { MockStudySessions.getSessionById(sessionId) }
    var isJoined by remember { mutableStateOf(session?.isJoined ?: false) }
    
    if (session == null) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Session not found")
        }
        return
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Session Details") },
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
                        val shareText = ShareHelper.shareStudySession(session)
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
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = Dimensions.Spacing.md)
        ) {
            // Session Header
            item {
                SessionHeaderCard(session)
            }
            
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.md))
            }
            
            // Description Section
            item {
                DescriptionSection(session)
            }
            
            // Details Section
            item {
                DetailsSection(session)
            }
            
            // Join/Leave Button
            item {
                JoinButtonSection(
                    isJoined = isJoined,
                    isFull = session.isFull,
                    onJoinClicked = { isJoined = !isJoined }
                )
            }
            
            // Participants Section
            item {
                ParticipantsSection(session)
            }
            
            item {
                Spacer(modifier = Modifier.height(Dimensions.Spacing.lg))
            }
        }
    }
}

/**
 * Session header with title, course, and status.
 */
@Composable
private fun SessionHeaderCard(session: com.example.univibe.domain.models.StudySession) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimensions.Spacing.md),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        session.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        "${session.course} • ${session.subject}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
                
                // Status badge
                Surface(
                    color = if (session.isFull) 
                        MaterialTheme.colorScheme.errorContainer 
                    else 
                        MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier.padding(Dimensions.Spacing.sm),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "${session.currentParticipants}/${session.maxParticipants}",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (session.isFull)
                                MaterialTheme.colorScheme.onErrorContainer
                            else
                                MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Text(
                            if (session.isFull) "Full" else "${session.spotsRemaining} spots",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (session.isFull)
                                MaterialTheme.colorScheme.onErrorContainer
                            else
                                MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            }
        }
    }
}

/**
 * Description section.
 */
@Composable
private fun DescriptionSection(session: com.example.univibe.domain.models.StudySession) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md)
    ) {
        Text(
            "About",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = Dimensions.Spacing.sm)
        )
        
        Text(
            session.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Details section with location, time, and host info.
 */
@Composable
private fun DetailsSection(session: com.example.univibe.domain.models.StudySession) {
    Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md)
    ) {
        Text(
            "Details",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = Dimensions.Spacing.md)
        )
        
        // Location
        DetailRow(
            icon = Icons.Default.LocationOn,
            label = "Location",
            value = session.location.getDisplayString()
        )
        
        // Date and Time
        DetailRow(
            icon = Icons.Default.EventNote,
            label = "Date & Time",
            value = formatDateTime(session.startTime, session.endTime)
        )
        
        // Duration
        DetailRow(
            icon = Icons.Default.AccessTime,
            label = "Duration",
            value = session.getDurationString()
        )
        
        // Location Type
        DetailRow(
            icon = Icons.Default.Place,
            label = "Type",
            value = session.location.type.name.replace("_", " ")
        )
        
        Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.md))
        
        // Host Information
        Text(
            "Hosted by",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = Dimensions.Spacing.md)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        session.host.fullName.split(" ").mapNotNull { it.firstOrNull() }
                            .take(2).joinToString(""),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    session.host.fullName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "@${session.host.username}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Button(
                onClick = { /* Visit profile */ },
                modifier = Modifier.height(40.dp),
                contentPadding = PaddingValues(horizontal = Dimensions.Spacing.md)
            ) {
                Text("Visit", style = MaterialTheme.typography.labelSmall)
            }
        }
        
        // Tags
        if (session.tags.isNotEmpty()) {
            Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.md))
            Text(
                "Tags",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = Dimensions.Spacing.md)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.xs)
            ) {
                session.tags.forEach { tag ->
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            tag,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(
                                horizontal = Dimensions.Spacing.sm,
                                vertical = Dimensions.Spacing.xs
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

/**
 * Join/Leave button section.
 */
@Composable
private fun JoinButtonSection(
    isJoined: Boolean,
    isFull: Boolean,
    onJoinClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.md),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md)
    ) {
        Button(
            onClick = onJoinClicked,
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            enabled = !isFull || isJoined
        ) {
            Text(
                if (isJoined) "Leave Session" else "Join Session",
                style = MaterialTheme.typography.labelLarge
            )
        }
        
        OutlinedButton(
            onClick = { /* Message host */ },
            modifier = Modifier
                .width(56.dp)
                .height(56.dp)
        ) {
            TextIcon(
                symbol = UISymbols.MESSAGE,
                contentDescription = "Message",
                fontSize = 16
            )
        }
    }
}

/**
 * Participants section.
 */
@Composable
private fun ParticipantsSection(session: com.example.univibe.domain.models.StudySession) {
    Divider(modifier = Modifier.padding(vertical = Dimensions.Spacing.sm))
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(Dimensions.Spacing.md)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimensions.Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Participants (${session.currentParticipants})",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "See all",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        session.participants.take(5).forEach { participant ->
            ParticipantItem(participant)
        }
        
        if (session.participants.size > 5) {
            Text(
                "+${session.participants.size - 5} more",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = Dimensions.Spacing.md)
            )
        }
    }
}

/**
 * Individual participant item.
 */
@Composable
private fun ParticipantItem(participant: com.example.univibe.domain.models.User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    participant.fullName.split(" ").mapNotNull { it.firstOrNull() }
                        .take(2).joinToString(""),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                participant.fullName,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                "@${participant.username}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        if (participant.isVerified) {
            Icon(
                Icons.Default.VerifiedUser,
                contentDescription = "Verified",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Detail row component for displaying key-value pairs.
 */
@Composable
private fun DetailRow(
    icon: androidx.compose.material.icons.materialIcon,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimensions.Spacing.sm),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.Spacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Format date and time for display.
 */
private fun formatDateTime(startTime: Long, endTime: Long): String {
    val now = getCurrentTimeMillis()
    val diffDays = (startTime - now) / 86400000L
    
    // For now, return simple format; in real app would use date formatter
    return when {
        diffDays == 0L -> "Today"
        diffDays == 1L -> "Tomorrow"
        else -> "in ${diffDays} days"
    }
}