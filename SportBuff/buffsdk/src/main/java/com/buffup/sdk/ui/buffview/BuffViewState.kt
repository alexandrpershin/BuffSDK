package com.buffup.sdk.ui.buffview

import com.buffup.sdk.model.BuffModel

/**
 * Represent state of [BuffView]
 * */

sealed class BuffViewState {
    class InitialShow(val data: BuffModel) : BuffViewState()
    object AnswerSelected : BuffViewState()
    object Timeout : BuffViewState()
    object CloseClicked : BuffViewState()
}