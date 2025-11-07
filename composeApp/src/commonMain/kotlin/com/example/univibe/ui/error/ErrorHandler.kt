package com.example.univibe.ui.error

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Sealed class representing different types of errors that can occur in the app.
 */
sealed class AppError {
    data class NetworkError(val message: String = "Network connection failed") : AppError()
    data class ServerError(val code: Int = 500, val message: String = "Server error occurred") : AppError()
    data class ValidationError(val field: String = "", val message: String) : AppError()
    data class AuthError(val message: String = "Authentication failed") : AppError()
    data class NotFoundError(val resource: String = "Resource") : AppError()
    data class TimeoutError(val message: String = "Request timeout") : AppError()
    data class Unknown(val message: String = "An unexpected error occurred") : AppError()
}

/**
 * Handles app errors and displays them to the user via snackbars or custom handlers.
 */
class ErrorHandler(
    private val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope
) {
    
    /**
     * Displays error message based on error type
     */
    fun handleError(error: AppError) {
        val message = getErrorMessage(error)
        val actionLabel = if (error is AppError.NetworkError) "Retry" else null
        
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = androidx.compose.material3.SnackbarDuration.Long
            )
        }
    }
    
    /**
     * Displays custom error message
     */
    fun showMessage(message: String, isError: Boolean = false) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = if (isError) 
                    androidx.compose.material3.SnackbarDuration.Long 
                else 
                    androidx.compose.material3.SnackbarDuration.Short
            )
        }
    }
    
    /**
     * Executes a block with error handling
     */
    suspend fun <T> withErrorHandling(
        onError: ((AppError) -> Unit)? = null,
        block: suspend () -> T
    ): Result<T> {
        return try {
            Result.success(block())
        } catch (e: Exception) {
            val error = mapException(e)
            onError?.invoke(error) ?: handleError(error)
            Result.failure(e)
        }
    }
    
    /**
     * Maps exceptions to AppError types
     */
    private fun mapException(e: Exception): AppError {
        return when (e) {
            is java.net.UnknownHostException -> AppError.NetworkError()
            is java.net.SocketTimeoutException -> AppError.TimeoutError()
            is java.net.ConnectException -> AppError.NetworkError("Cannot connect to server")
            else -> AppError.Unknown(e.message ?: "Unknown error")
        }
    }
    
    /**
     * Gets user-friendly error message
     */
    private fun getErrorMessage(error: AppError): String {
        return when (error) {
            is AppError.NetworkError -> error.message
            is AppError.ServerError -> "Server error (${error.code}): ${error.message}"
            is AppError.ValidationError -> error.message
            is AppError.AuthError -> error.message
            is AppError.NotFoundError -> "${error.resource} not found"
            is AppError.TimeoutError -> error.message
            is AppError.Unknown -> error.message
        }
    }
}

/**
 * Composable function to remember an ErrorHandler
 */
@Composable
fun rememberErrorHandler(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
): ErrorHandler {
    return remember(snackbarHostState, scope) {
        ErrorHandler(snackbarHostState, scope)
    }
}

/**
 * Extension function for Result to handle errors
 */
fun <T> Result<T>.onError(handler: (AppError) -> Unit): Result<T> {
    exceptionOrNull()?.let { exception ->
        val error = when (exception) {
            is java.net.UnknownHostException -> AppError.NetworkError()
            is java.net.SocketTimeoutException -> AppError.TimeoutError()
            is java.net.ConnectException -> AppError.NetworkError("Cannot connect to server")
            else -> AppError.Unknown(exception.message ?: "Unknown error")
        }
        handler(error)
    }
    return this
}

/**
 * Extension function to safely get value or null
 */
fun <T> Result<T>.getOrNull(): T? = getOrNull()

/**
 * Extension function to safely handle errors
 */
fun <T> Result<T>.fold(
    onSuccess: (T) -> Unit,
    onError: (AppError) -> Unit
) {
    onSuccess(getOrNull() ?: run {
        exceptionOrNull()?.let { exception ->
            val error = when (exception) {
                is java.net.UnknownHostException -> AppError.NetworkError()
                is java.net.SocketTimeoutException -> AppError.TimeoutError()
                else -> AppError.Unknown(exception.message ?: "Unknown error")
            }
            onError(error)
        }
        return
    })
}
