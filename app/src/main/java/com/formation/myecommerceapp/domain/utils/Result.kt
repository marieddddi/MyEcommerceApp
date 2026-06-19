package com.formation.myecommerceapp.domain.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T> The type of the expected [Result].
 */
sealed class Result<out T> {
    /**
     * Represents a successful result from a calcul.
     *
     * @param data The data retrieved via the calcul.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents a calcul returning an error.
     *
     * @param exception The exception representing the encountered error.
     */
    data class Error(val exception: Throwable) : Result<Nothing>()

    /**
     * Represents a calcul that is still in progress.
     */
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> "Loading"
        }
    }
}

/**
 * Verify is the [Result] is a [Result.Success].
 */
val <T> Result<T>.isSuccess get() = this is Result.Success<T>

/**
 * Verify is the [Result] is a [Result.Error].
 */
val <T> Result<T>.isError get() = this is Result.Error

/**
 * Verify is the [Result] is a [Result.Loading].
 */
val <T> Result<T>.isLoading get() = this == Result.Loading

/**
 * Returns the data contained in the [Result].
 * If it is a [Result.Success], returns the [Result.Success.data].
 * Otherwise, returns null.
 */
fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Result.Success -> this.data
    else -> null
}

/**
 * Returns the error contained in the [Result].
 * If it is a [Result.Error], returns the [Result.Error.exception].
 * Otherwise, returns null.
 */
fun <T> Result<T>.getExceptionOrNull(): Throwable? = when (this) {
    is Result.Error -> this.exception
    else -> null
}