package com.mindorks.framework.mvvm.ui.login

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindorks.framework.mvvm.databinding.ActivityLoginBinding
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        binding.btnServerLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.onServerLoginClick(email, password)
        }

        binding.ibGoogleLogin.setOnClickListener {
            viewModel.onGoogleLoginClick()
        }

        binding.ibFbLogin.setOnClickListener {
            viewModel.onFacebookLoginClick()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginSuccess.collect {
                openMainActivity()
            }
        }

        lifecycleScope.launch {
            viewModel.loginError.collect { errorResId ->
                onError(errorResId)
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) showLoading() else hideLoading()
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    onError(it)
                    viewModel.clearError()
                }
            }
        }
    }

    private fun openMainActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }
}