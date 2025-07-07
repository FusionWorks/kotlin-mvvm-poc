package com.mindorks.framework.mvvm.ui.base

import androidx.annotation.StringRes

/**
 * Base interface that any class that wants to act as a View in the MVVM pattern must implement
 */
interface MvpView {

    fun showLoading()

    fun hideLoading()

    fun openActivityOnTokenExpire()

    fun onError(@StringRes resId: Int)

    fun onError(message: String?)

    fun showMessage(message: String?)

    fun showMessage(@StringRes resId: Int)

    fun isNetworkConnected(): Boolean

    fun hideKeyboard()
}