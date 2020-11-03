package com.buffup.sdk.ui.buffview

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Provides dependencies for [BuffViewModel] with [koin]
 * */

object BuffViewModelDi {
    fun getModule(): Module {
        return module {
            viewModel { BuffViewModel(get()) }
        }
    }
}
