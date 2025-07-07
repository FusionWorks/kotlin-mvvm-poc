package com.mindorks.framework.mvvm.debug

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.mindorks.framework.mvvm.utils.AppLogger

/**
 * Flipper initialization for debug builds
 */
object FlipperInitializer {

    fun initialize(context: Context) {
        if (FlipperUtils.shouldEnableFlipper(context)) {
            try {
                val client = AndroidFlipperClient.getInstance(context)
                
                // Add Inspector plugin for UI debugging
                client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
                
                // Add Database plugin for Room database inspection
                client.addPlugin(DatabasesFlipperPlugin(context))
                
                // Add Network plugin for API debugging
                client.addPlugin(NetworkFlipperPlugin())
                
                client.start()
                AppLogger.d("Flipper initialized successfully")
            } catch (e: Exception) {
                AppLogger.e("Failed to initialize Flipper: ${e.message}")
            }
        }
    }
}