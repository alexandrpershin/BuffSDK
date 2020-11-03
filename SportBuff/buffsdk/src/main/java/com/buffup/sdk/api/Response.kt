package com.buffup.sdk.api

import androidx.annotation.StringRes
import com.buffup.sdk.R

/**
 * Comfortable way to wrap data and errors
* */

 sealed class TaskResult<out T> {
    data class ErrorResult(val errorType: ErrorType) : TaskResult<Nothing>()
    data class SuccessResult<T>(val data: T) : TaskResult<T>()
}

 inline fun <T> TaskResult<T>.doOnSuccess(callback: (T) -> Unit) {
    if (this is TaskResult.SuccessResult) {
        callback(data)
    }
}

 inline fun <T> TaskResult<T>.doOnError(callback: (ErrorType) -> Unit) {
    if (this is TaskResult.ErrorResult) {
        callback(errorType)
    }
}

sealed class ErrorType {
    data class GenericError(
        @StringRes val resId: Int = R.string.error_message_unexpected_error,
        val message: String? = null
    ) : ErrorType()

    data class InternetError(@StringRes val resId: Int) : ErrorType()
}
