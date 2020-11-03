package com.buffup.sdk.ui.buffview


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.buffup.sdk.api.ErrorType
import com.buffup.sdk.databinding.BuffViewBinding
import com.buffup.sdk.extensions.*
import com.buffup.sdk.ui.base.BaseView
import com.buffup.sdk.ui.buffview.adapter.AnswerAdapter
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class BuffView : BaseView<BuffViewBinding, BuffViewModel>, LifecycleObserver {

    private lateinit var answerAdapter: AnswerAdapter

    private var onErrorCallback: ((ErrorType) -> Unit)? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    /**
     *  Init viewmodel with help of [Koin]
    * */

    override fun initViewModel(lifecycleOwner: LifecycleOwner): BuffViewModel {
        val viewModel: BuffViewModel by lifecycleOwner.viewModel()
        return viewModel
    }

    override fun initUi(binding: BuffViewBinding) {
        initRecyclerView(binding)
        binding.root.slideDown()
    }

    private fun initRecyclerView(binding: BuffViewBinding) {
        binding.rvAnswers.apply {
            layoutManager = LinearLayoutManager(context)
            answerAdapter = AnswerAdapter(context)
            adapter = answerAdapter

            answerAdapter.onClickListener = {
                viewModel.onAnswerSelected(it)
            }
        }
    }

    fun initialize(onSuccess: (() -> Unit)? = null, onError: (ErrorType) -> Unit) {
        this.onErrorCallback = onError

        onSuccess?.invoke()
        viewModel.startFetchData()
    }

    override fun initBinding(): BuffViewBinding = BuffViewBinding.inflate(
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
        this,
        true
    )

    /**
     * Add click listeners
     * */

    override fun addListeners(binding: BuffViewBinding) {
        binding.sender.buffClose.setOnClickListener {
            viewModel.onCloseClicked()
            it.disableClick()
        }
    }

    /**
     * Subscribe to LiveData objects of [BuffViewModel]
     * */

    override fun addObservers(binding: BuffViewBinding) {
        viewModel.viewStateLiveData.observe(lifecycleOwner, Observer { state ->
            when (state) {

                /**
                 * Show data from backend with [BuffView]
                 * Show [BuffView] with animation
                * */

                is BuffViewState.InitialShow -> {
                    val data = state.data
                    answerAdapter.updateData(data.answers)

                    binding.root.slideUp()
                    binding.sender.buffClose.enableClick()
                    binding.sender.senderName.text = data.author.fullName
                    binding.question.questionText.text = data.question.title

                    Glide.with(context).load(data.author.image).into(binding.sender.senderImage)
                }

                /**
                 * User selected the answer
                 * Hide timer and [BuffView] with animation delay
                 * */

                BuffViewState.AnswerSelected -> {
                    binding.question.questionTimeProgress.fadeOut()
                    binding.root.slideDownWithDelay()
                }

                /**
                 * User have not interacted with [BuffView]
                 * Hide [BuffView] with animation
                 * */

                BuffViewState.Timeout -> {
                    binding.root.slideDown()
                }

                /**
                 * User closed the [BuffView]
                 * Hide [BuffView] with animation immediately
                 * */


                BuffViewState.Close -> {
                    binding.root.slideDown()
                }
            }
        })

        /**
         *  Update timer with progress and left seconds
         * */

        viewModel.showViewTimerLiveData.observe(lifecycleOwner, Observer {
            binding.question.questionTimeProgress.text = it.toString()
            binding.question.questionTimeProgress.progress = it.toFloat()
        })

    }

    /**
     * Send error to client via [onErrorCallback]
     * */
    override fun errorHandler(errorType: ErrorType) {
        onErrorCallback?.invoke(errorType)
    }

    /**
     * Pause work of SDK (ex. because of client Fragment/Activity paused)
     * */

    fun onPause() {
        viewModel.stop()
    }

    /**
     * Stops work of SDK (for any client's reason)
     * */

    fun stop() {
        viewModel.stop()
    }

    /**
     * Resume work of SDK (ex. because of client Fragment/Activity resumed)
     * */

    fun onResume() {
        viewModel.startFetchData()
    }

}

