package com.buffup.sdk.ui.buffview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.buffup.sdk.api.TaskResult
import com.buffup.sdk.api.repository.BuffRepository
import com.buffup.sdk.model.Answer
import com.buffup.sdk.model.toModel
import com.buffup.sdk.ui.base.BaseViewModel
import com.buffup.sdk.ui.buffview.BuffViewState.InitialShow
import com.buffup.sdk.utils.Logger
import com.buffup.sdk.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

/**
 * ViewModel class for [BuffView]
 *  Handles main business logic of [BuffView]
 * */

class BuffViewModel(
    private val buffRepository: BuffRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.Main
) : BaseViewModel() {

    private var isFetchDataActive: AtomicBoolean = AtomicBoolean(false)
    private var isShowViewTimerActive: AtomicBoolean = AtomicBoolean(false)

    /**
     * LiveData which notify View about state changed
     * */

    private var _stateLiveData = MutableLiveData<BuffViewState>()
    val viewStateLiveData: LiveData<BuffViewState>
        get() = _stateLiveData

    /**
     * LiveData which notify View about how many seconds left till [BuffView] will be dismissed
     * */

    private var _showViewTimerLiveData = SingleLiveEvent<Int>()
    val showViewTimerLiveData: LiveData<Int>
        get() = _showViewTimerLiveData

    /**
     * Starts if not have been started yet.
     * Asks backend every [INTERVAL_BEFORE_FETCH_DATA] seconds for new portion of data
     * Works forever while [stopFetchData] or [stop]  method was called
     * */

    internal fun startFetchData() {
        if (!isFetchDataActive.get()) {
            isFetchDataActive.set(true)

            viewModelScope.launch(coroutineContext) {
                delay(INITIAL_DELAY)

                var countDownSeconds = 0
                while (isFetchDataActive.get()) {
                    if (countDownSeconds <= 0) {
                        fetchNextBuff()
                        countDownSeconds = INTERVAL_BEFORE_FETCH_DATA
                    }
                    countDownSeconds--
                    delay(ONE_SECOND)
                }
            }
        }
    }

    /**
     * Once data was received from backend, run countdown timer.
     * if [seconds] amount of time user didn't interacted with [BuffView]
     *  and once [countDownSeconds] == 0, [BuffView] gets dismissed
     * */

    private fun startTimer(seconds: Int) {
        if (!isShowViewTimerActive.get()) {
            isShowViewTimerActive.set(true)

            viewModelScope.launch(coroutineContext) {
                var countDownSeconds = seconds

                while (isShowViewTimerActive.get()) {
                    _showViewTimerLiveData.value = countDownSeconds

                    if (countDownSeconds <= 0) {
                        _stateLiveData.value = BuffViewState.Timeout
                        stopTimer()
                    }

                    countDownSeconds--
                    delay(ONE_SECOND)
                }
            }
        }
    }

    /**
     * Called if:
     * 1. User clicked on [BuffView]
     * 2. Previous timer reached 0
     * */

    private fun stopTimer() {
        Logger.logInfo(TAG, "stopTimer()")
        isShowViewTimerActive.set(false)
    }

    /**
     * Stop fetching data from backend
     * */

    private fun stopFetchData() {
        Logger.logInfo(TAG, "stopFetchData()")
        isFetchDataActive.set(false)
    }

    /**
     * Fetch data from backend
     * If any error occured -> send error to errorHandler
     * otherwise show [BuffView] with data and start countdown timer
     * */

    private suspend fun fetchNextBuff() {
        val randomBuffId = Random.nextInt(1, 6)
        val result = buffRepository.getNextBuff(randomBuffId)

        when (result) {
            is TaskResult.ErrorResult -> {
                notifyError(result.errorType)
                Logger.logError(TAG, "ErrorResult, ${result.errorType}")
            }
            is TaskResult.SuccessResult -> {
                val data = result.data.toModel()
                _stateLiveData.value = InitialShow(data)
                startTimer(data.secondsToShow)

                Logger.logInfo(TAG, "SuccessResult, $data")
            }
        }
    }

    /**
     * User clicked on close button on [BuffView]
     * */

    internal fun onCloseClicked() {
        _stateLiveData.value = BuffViewState.Close
        stopTimer()
    }

    /**
     * User clicked on one of answers of [BuffView]
     * */

    internal fun onAnswerSelected(answer: Answer) {
        // update answer on server
        _stateLiveData.value = BuffViewState.AnswerSelected
        stopTimer()
    }

    override fun onCleared() {
        super.onCleared()
        stop()
        Logger.logInfo(TAG, "onCleared() called")
    }

    /**
     * Called to stop or pause work of Sdk
     * */

    fun stop() {
        stopFetchData()
        stopTimer()
        _stateLiveData.value = BuffViewState.Close
        Logger.logInfo(TAG, "stop() called")
    }

    companion object {
        const val ONE_SECOND = 1_000L
        const val INTERVAL_BEFORE_FETCH_DATA = 30
        const val INITIAL_DELAY = 10_000L
    }

}