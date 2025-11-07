package com.example.univibe.ui.performance

import com.example.univibe.util.getCurrentTimeMillis
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

/**
 * Stable wrapper for lambda functions to prevent unnecessary recompositions.
 * Use this to memoize callback functions passed to composables.
 */
@Stable
class StableLambda<T>(val lambda: (T) -> Unit) {
    operator fun invoke(value: T) = lambda(value)
}

/**
 * Remember a stable lambda to prevent recompositions when the lambda is passed to other composables.
 */
@Composable
fun <T> rememberStableLambda(lambda: (T) -> Unit): StableLambda<T> {
    return remember { StableLambda(lambda) }
}

/**
 * Debounced value that updates after a delay.
 * Useful for search fields and input fields to prevent too many recompositions.
 * 
 * @param value The value to debounce
 * @param delayMillis The delay before updating (default 300ms)
 * @return The debounced value
 */
@Composable
fun <T> rememberDebounced(
    value: T,
    delayMillis: Long = 300L
): T {
    var debouncedValue by remember { mutableStateOf(value) }
    
    LaunchedEffect(value) {
        delay(delayMillis)
        debouncedValue = value
    }
    
    return debouncedValue
}

/**
 * Throttled function that only executes once per specified interval.
 * Useful for rapid click events or scroll events.
 * 
 * @param intervalMillis The minimum time between executions (default 300ms)
 * @param block The function to execute
 */
class ThrottleFunctionJob(
    private val intervalMillis: Long = 300L
) {
    private var lastExecutionTime = 0L
    
    suspend fun execute(block: suspend () -> Unit) {
        val now = getCurrentTimeMillis()
        if (now - lastExecutionTime >= intervalMillis) {
            lastExecutionTime = now
            block()
        }
    }
}

/**
 * Remember a throttled function.
 */
@Composable
fun rememberThrottledFunction(intervalMillis: Long = 300L): ThrottleFunctionJob {
    return remember { ThrottleFunctionJob(intervalMillis) }
}

/**
 * Image loading strategy for optimizing memory and performance.
 * Provides suggestions for image sizes based on display dimensions.
 */
object ImageLoadingStrategy {
    const val THUMBNAIL_SIZE = 200      // For small thumbnails
    const val SMALL_SIZE = 400          // For list items
    const val MEDIUM_SIZE = 600         // For cards
    const val LARGE_SIZE = 1200         // For full-screen images
    
    /**
     * Get the optimal image size based on display size.
     * Factors in pixel density to avoid loading oversized images.
     * 
     * @param displayWidthDp Display width in DP
     * @param displayHeightDp Display height in DP
     * @return The recommended image size in pixels
     */
    fun getOptimalSize(
        displayWidthDp: Int,
        displayHeightDp: Int,
        densityFactor: Float = 3f
    ): Int {
        val maxPixels = maxOf(
            displayWidthDp * densityFactor,
            displayHeightDp * densityFactor
        ).toInt()
        
        return when {
            maxPixels <= THUMBNAIL_SIZE -> THUMBNAIL_SIZE
            maxPixels <= SMALL_SIZE -> SMALL_SIZE
            maxPixels <= MEDIUM_SIZE -> MEDIUM_SIZE
            else -> LARGE_SIZE
        }
    }
}

/**
 * Derived state for computed values that should only recompute when dependencies change.
 * Prevents unnecessary recompositions of observers.
 * 
 * Usage:
 * ```
 * val filteredItems = derivedStateOf { items.filter { it.matches(query) } }
 * ```
 */
@Composable
inline fun <T, R> rememberDerivedState(
    value: T,
    crossinline transform: (T) -> R
): State<R> {
    return remember(value) {
        derivedStateOf { transform(value) }
    }
}

/**
 * Memoized selector for complex list transformations.
 * Only recomputes when the input list actually changes.
 */
@Composable
fun <T, R> rememberMemoizedSelector(
    list: List<T>,
    selector: (List<T>) -> List<R>
): List<R> {
    return remember(list) { selector(list) }
}

/**
 * Keep track of the previous value to detect changes.
 * Useful for animations based on value changes.
 */
@Composable
fun <T> rememberPrevious(value: T): T? {
    val ref = remember { mutableStateOf<T?>(null) }
    LaunchedEffect(value) {
        ref.value = value
    }
    return ref.value
}

/**
 * Perform an action only once on first composition.
 * Unlike LaunchedEffect(Unit), this is safe from double-invocation in strict mode.
 */
@Composable
fun rememberOnceOnly(block: () -> Unit) {
    remember {
        block()
    }
}

/**
 * Cache computation results based on input.
 * 
 * @param input The input to use as cache key
 * @param compute The computation to cache
 */
@Composable
fun <T, R> rememberComputed(
    input: T,
    compute: (T) -> R
): R {
    return remember(input) { compute(input) }
}

/**
 * Performance metrics for monitoring recomposition.
 * Use only for debugging, disable in production.
 */
object PerformanceMetrics {
    private val recompositionCounts = mutableMapOf<String, Int>()
    
    fun recordRecomposition(composableName: String) {
        recompositionCounts[composableName] = 
            (recompositionCounts[composableName] ?: 0) + 1
    }
    
    fun getRecompositionCount(composableName: String): Int {
        return recompositionCounts[composableName] ?: 0
    }
    
    fun reset() {
        recompositionCounts.clear()
    }
    
    fun getReport(): String {
        return recompositionCounts
            .toList()
            .sortedByDescending { it.second }
            .joinToString("\n") { (name, count) ->
                "$name: $count recompositions"
            }
    }
}

/**
 * Track recompositions in a composable (debug only).
 * Remove this in production builds.
 */
@Composable
fun TrackRecomposition(name: String) {
    remember {
        PerformanceMetrics.recordRecomposition(name)
    }
}
