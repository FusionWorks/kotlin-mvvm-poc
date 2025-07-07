package com.mindorks.framework.mvvm.ui.splash

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindorks.framework.mvvm.databinding.ActivitySplashBinding
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.login.LoginActivity
import com.mindorks.framework.mvvm.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun getViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.navigationEvent.collect { event ->
                when (event) {
                    is SplashViewModel.NavigationEvent.NavigateToLogin -> {
                        startActivity(LoginActivity.getStartIntent(this@SplashActivity))
                        finish()
                    }
                    is SplashViewModel.NavigationEvent.NavigateToMain -> {
                        startActivity(MainActivity.getStartIntent(this@SplashActivity))
                        finish()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    onError(it)
                    // On error, navigate to login as fallback
                    startActivity(LoginActivity.getStartIntent(this@SplashActivity))
                    finish()
                }
            }
        }
    }
}