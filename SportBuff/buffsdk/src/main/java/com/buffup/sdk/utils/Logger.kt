package com.buffup.sdk.utils

import android.util.Log
import com.buffup.sdk.BuildConfig


/**
 * Utility class for logging just for Debug build config
 * */

object Logger {

    fun logError(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    fun logInfo(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

}