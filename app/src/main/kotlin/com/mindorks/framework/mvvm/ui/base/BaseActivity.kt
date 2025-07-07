package com.mindorks.framework.mvvm.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.ui.login.LoginActivity
import com.mindorks.framework.mvvm.utils.CommonUtils
import com.mindorks.framework.mvvm.utils.NetworkUtils

/**
 * Base class for all activities implementing MVVM pattern
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), MvpView {

    protected lateinit var binding: VB
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        setupView()
    }

    protected abstract fun getViewBinding(): VB
    protected abstract fun setupView()

    override fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        progressDialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.white))
            .show()
    }

    override fun onError(message: String?) {
        message?.let { showSnackBar(it) } 
            ?: showSnackBar(getString(R.string.some_error))
    }

    override fun onError(@StringRes resId: Int) {
        onError(getString(resId))
    }

    override fun showMessage(message: String?) {
        message?.let { showSnackBar(it) } 
            ?: showSnackBar(getString(R.string.some_error))
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(this)
    }

    override fun openActivityOnTokenExpire() {
        startActivity(LoginActivity.getStartIntent(this))
        finish()
    }

    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun hideKeyboard() {
        CommonUtils.hideKeyboard(this)
    }

    override fun onDestroy() {
        hideLoading()
        super.onDestroy()
    }
}