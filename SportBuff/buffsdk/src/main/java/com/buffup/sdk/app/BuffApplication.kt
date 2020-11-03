package com.buffup.sdk.app

import android.app.Application
import com.buffup.sdk.ui.buffview.BuffViewModelDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
* Application class.
 * Inside [onCreate] method start [Koin], to provide dependencies
* */

class BuffApplication : Application() {


    private val modules = listOf(
        appModule,
        BuffViewModelDi.getModule()
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BuffApplication)
            modules(modules)
        }
    }


}