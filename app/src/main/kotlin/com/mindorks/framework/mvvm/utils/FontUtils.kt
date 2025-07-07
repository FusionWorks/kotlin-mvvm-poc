package com.mindorks.framework.mvvm.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.mindorks.framework.mvvm.R

/**
 * Font utility functions to replace Calligraphy library
 */
object FontUtils {

    /**
     * Apply Source Sans Pro Regular font to TextView
     */
    fun applyRegularFont(context: Context, textView: TextView) {
        val typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_family)
        textView.typeface = typeface
    }

    /**
     * Apply Source Sans Pro Bold font to TextView
     */
    fun applyBoldFont(context: Context, textView: TextView) {
        val typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_family)
        textView.setTypeface(typeface, Typeface.BOLD)
    }

    /**
     * Apply Source Sans Pro Light font to TextView
     */
    fun applyLightFont(context: Context, textView: TextView) {
        val typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_family)
        textView.typeface = typeface
        textView.setTypeface(typeface, Typeface.NORMAL)
    }

    /**
     * Apply Source Sans Pro Italic font to TextView
     */
    fun applyItalicFont(context: Context, textView: TextView) {
        val typeface = ResourcesCompat.getFont(context, R.font.source_sans_pro_family)
        textView.setTypeface(typeface, Typeface.ITALIC)
    }

    /**
     * Extension function to easily apply fonts to TextView
     */
    fun TextView.applyFont(context: Context, fontStyle: FontStyle) {
        when (fontStyle) {
            FontStyle.REGULAR -> FontUtils.applyRegularFont(context, this)
            FontStyle.BOLD -> FontUtils.applyBoldFont(context, this)
            FontStyle.LIGHT -> FontUtils.applyLightFont(context, this)
            FontStyle.ITALIC -> FontUtils.applyItalicFont(context, this)
        }
    }

    enum class FontStyle {
        REGULAR,
        BOLD,
        LIGHT,
        ITALIC
    }
}