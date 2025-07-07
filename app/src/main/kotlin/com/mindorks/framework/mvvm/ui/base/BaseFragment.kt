package com.mindorks.framework.mvvm.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.mindorks.framework.mvvm.utils.CommonUtils

/**
 * Base class for all fragments implementing MVVM pattern
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment(), MvpView {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    private var baseActivity: BaseActivity<*>? = null
    private var progressDialog: ProgressDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            baseActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    protected abstract fun setupView()

    override fun showLoading() {
        hideLoading()
        progressDialog = context?.let { CommonUtils.showLoadingDialog(it) }
    }

    override fun hideLoading() {
        progressDialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
    }

    override fun onError(message: String?) {
        baseActivity?.onError(message)
    }

    override fun onError(@StringRes resId: Int) {
        baseActivity?.onError(resId)
    }

    override fun showMessage(message: String?) {
        baseActivity?.showMessage(message)
    }

    override fun showMessage(@StringRes resId: Int) {
        baseActivity?.showMessage(resId)
    }

    override fun isNetworkConnected(): Boolean {
        return baseActivity?.isNetworkConnected() ?: false
    }

    override fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }

    override fun openActivityOnTokenExpire() {
        baseActivity?.openActivityOnTokenExpire()
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onDestroyView() {
        hideLoading()
        _binding = null
        super.onDestroyView()
    }
}