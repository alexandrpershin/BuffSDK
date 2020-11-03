package com.buffup.sdk.api


import com.buffup.sdk.R
import com.buffup.sdk.utils.Logger
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.UnknownHostException


/**
 * Handles retrofit response. Depends on result code returns expected result if success and [ErrorType] otherwise
 * */

 suspend fun <T> executeAsyncRequest(request: Deferred<Response<T>>): TaskResult<T> {

    return try {
        val result = request.await()

        if (result.isSuccessful) {
            Logger.logInfo(TAG, "Result is successful")
            TaskResult.SuccessResult(data = result.body() as T)
        } else {
            Logger.logError(TAG, "Result is not successful, code: ${result.code()} ")
            parseBackendError(result)
        }
    } catch (ex: Exception) {
        Logger.logError(TAG, "Exception message: ${ex.message}")
        if (ex is UnknownHostException) {
            TaskResult.ErrorResult(ErrorType.InternetError(R.string.message_internet_error))
        } else {
            TaskResult.ErrorResult(ErrorType.GenericError(message = ex.localizedMessage))
        }
    }
}

private fun <T> parseBackendError(result: Response<T>): TaskResult<T> {
    Logger.logError(TAG, "parseBackendError")
    return when (result.code()) {
        CODE__SERVER_ERROR -> {
            TaskResult.ErrorResult(ErrorType.GenericError(resId = R.string.error_message__server_error))
        }
        else -> TaskResult.ErrorResult(ErrorType.GenericError(resId = R.string.error_message_unexpected_server_error))
    }
}

private const val TAG = "ApiExecutor"
private const val CODE__SERVER_ERROR = 500
