package com.mindorks.framework.mvvm.debug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mindorks.framework.mvvm.databinding.ActivityDebugBinding
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DebugActivity : BaseActivity<ActivityDebugBinding>() {

    private val viewModel: DebugViewModel by viewModels()

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DebugActivity::class.java)
        }
    }

    override fun getViewBinding(): ActivityDebugBinding {
        return ActivityDebugBinding.inflate(layoutInflater)
    }

    override fun setupView() {
        setupToolbar()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Debug Menu"
        }
    }

    private fun setupClickListeners() {
        binding.btnExportDatabase.setOnClickListener {
            viewModel.exportDatabase()
        }

        binding.btnClearDatabase.setOnClickListener {
            viewModel.clearDatabase()
        }

        binding.btnRefreshInfo.setOnClickListener {
            viewModel.refreshDatabaseInfo()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.databaseInfo.collect { info ->
                with(binding) {
                    tvUserCount.text = "Users: ${info.userCount}"
                    tvQuestionCount.text = "Questions: ${info.questionCount}"
                    tvOptionCount.text = "Options: ${info.optionCount}"
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                if (isLoading) showLoading() else hideLoading()
            }
        }

        lifecycleScope.launch {
            viewModel.message.collect { message ->
                message?.let {
                    showMessage(it)
                    viewModel.clearMessage()
                }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}