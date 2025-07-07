package com.mindorks.framework.mvvm.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.mindorks.framework.mvvm.R

/**
 * App utility functions
 */
object AppUtils {

    fun openPlayStoreForApp(context: Context) {
        val appPackageName = context.packageName
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(context.getString(R.string.app_market_link) + appPackageName)
            )
            context.startActivity(intent)
        } catch (e: android.content.ActivityNotFoundException) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(context.getString(R.string.app_google_play_store_link) + appPackageName)
            )
            context.startActivity(intent)
        }
    }
}