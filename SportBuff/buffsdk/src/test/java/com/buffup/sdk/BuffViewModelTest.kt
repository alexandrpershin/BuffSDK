package com.buffup.sdk

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.buffup.sdk.api.TaskResult
import com.buffup.sdk.api.repository.BuffRepository
import com.buffup.sdk.di.JsonUtils
import com.buffup.sdk.di.testModule
import com.buffup.sdk.model.Answer
import com.buffup.sdk.model.toModel
import com.buffup.sdk.ui.buffview.BuffViewModel
import com.buffup.sdk.ui.buffview.BuffViewModel.Companion.INITIAL_DELAY
import com.buffup.sdk.ui.buffview.BuffViewModel.Companion.INTERVAL_BEFORE_FETCH_DATA
import com.buffup.sdk.ui.buffview.BuffViewModel.Companion.ONE_SECOND
import com.buffup.sdk.ui.buffview.BuffViewState
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get


class BuffViewModelTest : KoinTest {

    val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val module = module {
        factory { BuffViewModel(get(), testCoroutineDispatcher) }
    }

    /**
     * Allows LiveData value changes in unit tests without the need for Android SDK classes.
     */
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun initDependencies() {
        MockKAnnotations.init(this)
        startKoin { modules(listOf(testModule, module)) }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `test initial delay before fetch data from server`() =
        runBlockingTest(testCoroutineDispatcher) {
            val buffResponse = JsonUtils.provideTestBuffResponse()
            val buffModel = buffResponse.toModel()

            coEvery { get<BuffRepository>().getNextBuff(any()) } returns TaskResult.SuccessResult(
                buffResponse
            )

            val viewModel = get<BuffViewModel>()

            val mockObserver = spyk<Observer<BuffViewState>>()
            viewModel.viewStateLiveData.observeForever(mockObserver)

            viewModel.startFetchData()
            delay(INITIAL_DELAY)
            viewModel.stop()

            verify(atLeast = 1) { mockObserver.onChanged(BuffViewState.InitialShow(buffModel)) }
        }

    @Test
    fun `test close clicked state`() = runBlockingTest(testCoroutineDispatcher) {
        val viewModel = get<BuffViewModel>()
        viewModel.onCloseClicked()

        assert(viewModel.viewStateLiveData.value == BuffViewState.Close)
    }

    @Test
    fun `test answer selected state`() = runBlockingTest(testCoroutineDispatcher) {
        val viewModel = get<BuffViewModel>()
        val mockAnswer = mockk<Answer>()
        viewModel.onAnswerSelected(mockAnswer)

        assert(viewModel.viewStateLiveData.value == BuffViewState.AnswerSelected)
    }

    @Test
    fun `test timeout state`() = runBlockingTest(testCoroutineDispatcher) {
        val buffResponse = JsonUtils.provideTestBuffResponse()
        val buffModel = buffResponse.toModel()

        coEvery { get<BuffRepository>().getNextBuff(any()) } returns TaskResult.SuccessResult(
            buffResponse
        )

        val viewModel = get<BuffViewModel>()

        val mockObserver = spyk<Observer<BuffViewState>>()
        viewModel.viewStateLiveData.observeForever(mockObserver)

        viewModel.startFetchData()
        delay(INITIAL_DELAY)

        delay((buffModel.secondsToShow + 1) * 1000L)

        viewModel.stop()

        verify(atLeast = 1) { mockObserver.onChanged(BuffViewState.Timeout) }
    }

    @Test
    fun `test fetch data time interval state`() = runBlockingTest(testCoroutineDispatcher) {
        val buffResponse = JsonUtils.provideTestBuffResponse()
        val buffModel = buffResponse.toModel()

        coEvery { get<BuffRepository>().getNextBuff(any()) } returns TaskResult.SuccessResult(
            buffResponse
        )

        val viewModel = get<BuffViewModel>()

        val mockObserver = spyk<Observer<BuffViewState>>()
        viewModel.viewStateLiveData.observeForever(mockObserver)

        viewModel.startFetchData()
        delay(INITIAL_DELAY)

        delay(INTERVAL_BEFORE_FETCH_DATA * ONE_SECOND)
        delay(INTERVAL_BEFORE_FETCH_DATA * ONE_SECOND)

        viewModel.stop()

        verify(atLeast = 2) { mockObserver.onChanged(BuffViewState.InitialShow(buffModel)) }
    }

    @Test
    fun `test timer started once data fetched from server`() =
        runBlockingTest(testCoroutineDispatcher) {
            val buffResponse = JsonUtils.provideTestBuffResponse()
            val buffModel = buffResponse.toModel()

            coEvery { get<BuffRepository>().getNextBuff(any()) } returns TaskResult.SuccessResult(
                buffResponse
            )

            val viewModel = get<BuffViewModel>()

            val mockObserver = spyk<Observer<Int>>()
            viewModel.showViewTimerLiveData.observeForever(mockObserver)

            viewModel.startFetchData()
            delay(INITIAL_DELAY)

            delay(INTERVAL_BEFORE_FETCH_DATA * ONE_SECOND)

            viewModel.stop()

            verify(atLeast = 1) { mockObserver.onChanged(buffModel.secondsToShow) }
        }

    @Test
    fun `test all work was stopped when stop() called`() =
        runBlockingTest(testCoroutineDispatcher) {
            val buffResponse = JsonUtils.provideTestBuffResponse()
            val buffModel = buffResponse.toModel()

            coEvery { get<BuffRepository>().getNextBuff(any()) } returns TaskResult.SuccessResult(
                buffResponse
            )

            val viewModel = get<BuffViewModel>()

            val stateObserver = spyk<Observer<BuffViewState>>()

            viewModel.viewStateLiveData.observeForever(stateObserver)

            viewModel.startFetchData()
            delay(INITIAL_DELAY)

            viewModel.stop()

            verify(atLeast = 1) { stateObserver.onChanged(BuffViewState.Close) }
        }

}


