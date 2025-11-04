package com.example.univibe.ui.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.univibe.data.mock.MockEvents
import com.example.univibe.domain.models.EventCategory
import com.example.univibe.ui.components.PrimaryButton
import com.example.univibe.ui.components.TextIcon
import com.example.univibe.ui.theme.Spacing
import com.example.univibe.ui.utils.UISymbols
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create Event Screen - Form for creating new campus events
 *
 * Features:
 * - Event title and description input
 * - Category selection
 * - Time picker for start/end times
 * - Location selection (virtual or physical)
 * - Capacity settings
 * - Tags/topics for discoverability
 * - Form validation
 * - Create button with mock API integration
 */
object CreateEventScreen : Screen {
    @Composable
    override fun Content() {
        CreateEventScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateEventScreenContent() {
    val navigator = LocalNavigator.currentOrThrow
    
    // Form state
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<EventCategory?>(null) }
    var startDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var isVirtual by remember { mutableStateOf(false) }
    var maxAttendees by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var isCreating by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Event") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        TextIcon(
                            symbol = UISymbols.CLOSE,
                            contentDescription = "Close",
                            fontSize = 20
                        )
                    }
                },
                actions = {
                    if (!isCreating) {
                        IconButton(
                            onClick = {
                                if (isFormValid(title, selectedCategory, startDate, startTime, location)) {
                                    isCreating = true
                                    // TODO: Create event via MockEvents.createEvent()
                                    // After success, navigate back
                                    navigator.pop()
                                }
                            }
                        ) {
                            TextIcon(
                                symbol = UISymbols.CHECK,
                                contentDescription = "Create",
                                fontSize = 20
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(Spacing.default),
            contentPadding = PaddingValues(horizontal = Spacing.default, vertical = Spacing.default)
        ) {
            // Title Section
            item {
                FormSection("Event Title") {
                    TextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("Summer Music Festival") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 56.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
            
            // Description Section
            item {
                FormSection("Description") {
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Tell students about this amazing event...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        maxLines = 4,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
            
            // Category Selection Section
            item {
                FormSection("Category") {
                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
                        EventCategory.values().forEach { category ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                            ) {
                                RadioButton(
                                    selected = category == selectedCategory,
                                    onClick = { selectedCategory = category }
                                )
                                Text("${category.emoji} ${category.displayName}")
                            }
                        }
                    }
                }
            }
            
            // Date and Time Section
            item {
                FormSection("Date & Time") {
                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.default)) {
                        // Date picker (simplified text input)
                        TextField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            label = { Text("Start Date") },
                            placeholder = { Text("MM/DD/YYYY") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                        
                        // Time row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Spacing.default)
                        ) {
                            TextField(
                                value = startTime,
                                onValueChange = { startTime = it },
                                label = { Text("Start") },
                                placeholder = { Text("HH:mm") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                            
                            TextField(
                                value = endTime,
                                onValueChange = { endTime = it },
                                label = { Text("End") },
                                placeholder = { Text("HH:mm") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                    }
                }
            }
            
            // Location Section
            item {
                FormSection("Location") {
                    Column(verticalArrangement = Arrangement.spacedBy(Spacing.default)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            Checkbox(
                                checked = isVirtual,
                                onCheckedChange = { isVirtual = it }
                            )
                            Text("Virtual Event")
                        }
                        
                        TextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text(if (isVirtual) "Video Link (Zoom, Teams, etc.)" else "Location") },
                            placeholder = { Text(if (isVirtual) "https://zoom.us/meeting" else "Student Center, Room 301") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    if (isVirtual) Icons.Default.VideoCall else Icons.Default.LocationOn,
                                    contentDescription = null
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )
                    }
                }
            }
            
            // Capacity Section (Optional)
            item {
                FormSection("Capacity (Optional)") {
                    TextField(
                        value = maxAttendees,
                        onValueChange = { maxAttendees = it },
                        label = { Text("Max Attendees") },
                        placeholder = { Text("Leave empty for unlimited") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardType = KeyboardType.Number,
                        leadingIcon = {
                            TextIcon(
                                symbol = UISymbols.GROUP,
                                contentDescription = null,
                                fontSize = 16
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
            
            // Tags Section (Optional)
            item {
                FormSection("Topics/Tags (Optional)") {
                    TextField(
                        value = tags,
                        onValueChange = { tags = it },
                        label = { Text("Separate with commas") },
                        placeholder = { Text("music, outdoor, free, networking") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 80.dp),
                        maxLines = 2,
                        leadingIcon = {
                            TextIcon(
                                symbol = UISymbols.LABEL,
                                contentDescription = null,
                                fontSize = 16
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
            
            // Create button
            item {
                PrimaryButton(
                    text = "Create Event",
                    onClick = {
                        if (isFormValid(title, selectedCategory, startDate, startTime, location)) {
                            isCreating = true
                            // TODO: Create event via API
                            navigator.pop()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    loading = isCreating,
                    leadingIcon = Icons.Default.Add
                )
            }
            
            // Help text
            item {
                Text(
                    "After creating your event, it will appear in the events list and be visible to all students.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(Spacing.default))
            }
        }
    }
}

/**
 * Reusable form section with title
 */
@Composable
private fun FormSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall
        )
        content()
    }
}

/**
 * Validate that required fields are filled
 */
private fun isFormValid(
    title: String,
    category: EventCategory?,
    startDate: String,
    startTime: String,
    location: String
): Boolean {
    return title.isNotBlank() &&
            category != null &&
            startDate.isNotBlank() &&
            startTime.isNotBlank() &&
            location.isNotBlank()
}