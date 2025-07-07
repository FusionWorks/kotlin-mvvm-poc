package com.mindorks.framework.mvvm.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.mindorks.framework.mvvm.R

/**
 * View utility functions
 */
object ViewUtils {

    fun changeIconDrawableToGray(context: Context, drawable: Drawable?) {
        drawable?.let {
            it.mutate()
            it.setColorFilter(
                ContextCompat.getColor(context, R.color.dark_gray),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    fun dpToPx(context: Context, dp: Float): Int {
        return ScreenUtils.dpToPx(context, dp)
    }

    fun pxToDp(context: Context, px: Float): Float {
        return ScreenUtils.pxToDp(context, px)
    }
}