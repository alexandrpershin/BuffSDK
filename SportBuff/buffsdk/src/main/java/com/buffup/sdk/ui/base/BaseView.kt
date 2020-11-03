package com.buffup.sdk.ui.base


import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.buffup.sdk.api.ErrorType


/**
 *  Base class for CustomView with viewmodel object inside
 * */

abstract class BaseView<DB : ViewBinding, VM : BaseViewModel> : ConstraintLayout {

    constructor(context: Context) : super(context) {
        initComponents()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initComponents()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initComponents()
    }

    internal  lateinit var lifecycleOwner: LifecycleOwner

    protected val TAG: String = this::class.java.simpleName

    private var binding: DB? = null

    internal lateinit var viewModel: VM


    /**
     * Abstract methods what should be implemented by derived classes
    * */

    internal abstract fun initBinding(): DB
    internal abstract fun initUi(binding: DB)
    internal abstract fun addListeners(binding: DB)
    internal abstract fun addObservers(binding: DB)
    internal abstract fun errorHandler(errorType: ErrorType)
    internal abstract fun initViewModel(lifecycleOwner: LifecycleOwner): VM

    /**
     * Init ViewBinding
     * Get LifecycleOwner to make observers lifecycle aware
    * */

    private fun initComponents() {
        binding = initBinding()

        lifecycleOwner = context as LifecycleOwner

        viewModel = initViewModel(lifecycleOwner)

        viewModel.errorNotifier.observe(lifecycleOwner, Observer {
            errorHandler(it)
        })

        binding?.let {
            initUi(it)
            addListeners(it)
            addObservers(it)
        }
    }


}