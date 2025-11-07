package com.example.univibe.ui.accessibility

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.*

/**
 * Semantic modifiers for better accessibility support.
 * These help screen readers understand the structure and purpose of UI elements.
 * Simplified to use only cross-platform Compose APIs.
 */

/**
 * Mark a modifier as a button with semantic information for accessibility.
 * @param label The accessible label/description for the button
 * @param onClick Callback when button is clicked
 */
fun Modifier.semanticButton(
    label: String,
    onClick: () -> Unit
): Modifier = this.semantics {
    contentDescription = label
    role = Role.Button
    onClick {
        onClick()
        true
    }
}

/**
 * Mark a modifier as a clickable element with semantic information.
 * @param label The accessible label for the element
 * @param onClick Callback when element is clicked
 */
fun Modifier.semanticClickable(
    label: String,
    onClick: () -> Unit
): Modifier = this.semantics {
    contentDescription = label
    onClick {
        onClick()
        true
    }
}

/**
 * Mark a modifier as a checkbox for accessibility.
 * @param label The accessible label for the checkbox
 * @param checked Whether the checkbox is checked
 * @param onToggle Callback when checkbox is toggled
 */
fun Modifier.semanticCheckbox(
    label: String,
    checked: Boolean,
    onToggle: () -> Unit
): Modifier = this.semantics {
    contentDescription = label + if (checked) " checked" else " unchecked"
    role = Role.Checkbox
    onClick {
        onToggle()
        true
    }
}

/**
 * Mark a modifier as a switch/toggle for accessibility.
 * @param label The accessible label for the switch
 * @param enabled Whether the switch is enabled
 * @param onToggle Callback when switch is toggled
 */
fun Modifier.semanticSwitch(
    label: String,
    enabled: Boolean,
    onToggle: () -> Unit
): Modifier = this.semantics {
    contentDescription = label + if (enabled) " on" else " off"
    role = Role.Switch
    onClick {
        onToggle()
        true
    }
}

/**
 * Mark a modifier as an image with semantic information.
 * @param description The accessible description of the image
 */
fun Modifier.semanticImage(
    description: String
): Modifier = this.semantics {
    contentDescription = description
    role = Role.Image
}

/**
 * Mark a modifier as a heading for accessibility.
 * Helps screen readers understand document structure.
 */
fun Modifier.semanticHeading(): Modifier = this.semantics {
    heading()
}

/**
 * Mark a modifier as a heading with a specific level.
 * @param level The heading level (1-6, where 1 is most important)
 */
fun Modifier.semanticHeadingWithLevel(level: Int): Modifier = this.semantics {
    heading()
    contentDescription = "Heading level $level"
}

/**
 * Mark a modifier as a disabled element for accessibility.
 * @param label The accessible label for the element
 * @param reason Optional reason why the element is disabled
 */
fun Modifier.semanticDisabled(
    label: String,
    reason: String? = null
): Modifier = this.semantics {
    val message = label + (if (reason != null) " - $reason" else " - disabled")
    contentDescription = message
    disabled()
}

/**
 * Mark a modifier as a progress indicator for accessibility.
 * @param label The accessible label for the progress
 * @param progress The current progress (0f to 1f)
 * @param stateDescription Optional description of the current state
 */
fun Modifier.semanticProgress(
    label: String,
    progress: Float = 0f,
    stateDescription: String? = null
): Modifier = this.semantics {
    contentDescription = label
    progressBarRangeInfo = ProgressBarRangeInfo(
        current = progress,
        range = 0f..1f
    )
    if (stateDescription != null) {
        contentDescription = "$label: $stateDescription"
    }
}

/**
 * Mark a modifier as an error state for accessibility.
 * @param label The accessible label
 * @param errorMessage The error message to be announced
 */
fun Modifier.semanticError(
    label: String,
    errorMessage: String
): Modifier = this.semantics {
    contentDescription = "$label: Error - $errorMessage"
    error(errorMessage)
}

/**
 * Mark a modifier as live region for accessibility.
 * Updates within live regions are announced to screen reader users.
 */
fun Modifier.semanticLiveRegion(): Modifier = this.semantics {
    liveRegion = LiveRegionMode.Assertive
}

/**
 * Mark a modifier as a polite live region for accessibility.
 * Polite announcements won't interrupt the user.
 */
fun Modifier.semanticLiveRegionPolite(): Modifier = this.semantics {
    liveRegion = LiveRegionMode.Polite
}

/**
 * Mark a modifier as selected for accessibility.
 * @param label The accessible label for the element
 * @param selected Whether the element is selected
 */
fun Modifier.semanticSelected(
    label: String,
    selected: Boolean
): Modifier = this.semantics {
    contentDescription = label + if (selected) " selected" else " not selected"
}

/**
 * Add a custom state description for accessibility.
 * @param description The state description to announce
 */
fun Modifier.semanticStateDescription(
    description: String
): Modifier = this.semantics {
    stateDescription = description
}

/**
 * Combine multiple semantic modifiers for convenience.
 * @param label The main accessible label
 * @param description Optional detailed description
 * @param isButton Whether this should be treated as a button
 */
fun Modifier.semanticContent(
    label: String,
    description: String? = null,
    isButton: Boolean = false
): Modifier = this.semantics {
    contentDescription = if (description != null) "$label. $description" else label
    if (isButton) {
        role = Role.Button
    }
}
