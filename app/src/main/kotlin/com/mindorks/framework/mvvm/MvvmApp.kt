package com.mindorks.framework.mvvm

import android.app.Application
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.utils.AppLogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Application class for MVVM architecture
 */
@HiltAndroidApp
class MvvmApp : Application() {

    @Inject
    lateinit var dataRepository: DataRepository

    override fun onCreate() {
        super.onCreate()
        
        AppLogger.init()
        initializeDebugTools()
    }

    private fun initializeDebugTools() {
        if (BuildConfig.DEBUG) {
            // Flipper and other debug tools are auto-initialized via dependencies
            AppLogger.d("Debug tools initialized")
        }
    }
}