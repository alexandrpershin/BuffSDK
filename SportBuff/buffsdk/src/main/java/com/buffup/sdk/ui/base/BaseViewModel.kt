package com.buffup.sdk.ui.base

import androidx.annotation.UiThread
import androidx.lifecycle.ViewModel
import com.buffup.sdk.api.ErrorType
import com.buffup.sdk.utils.SingleLiveEvent

/**
 * Base class for ViewModel classes of current sdk
 * */

abstract class BaseViewModel : ViewModel() {

    protected val TAG: String = this::class.java.simpleName

    private val _errorNotifier: SingleLiveEvent<ErrorType> = SingleLiveEvent()
    val errorNotifier
        get() = _errorNotifier

    @UiThread
    fun notifyError(error: ErrorType) {
        _errorNotifier.value = error
    }

}

