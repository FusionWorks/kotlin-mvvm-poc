package com.mindorks.framework.mvvm.ui.feed.opensource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.databinding.FragmentOpenSourceBinding
import com.mindorks.framework.mvvm.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpenSourceFragment : BaseFragment<FragmentOpenSourceBinding>() {

    private val viewModel: OpenSourceViewModel by viewModels()
    private lateinit var openSourceAdapter: OpenSourceAdapter

    companion object {
        fun newInstance(): OpenSourceFragment {
            return OpenSourceFragment()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOpenSourceBinding {
        return FragmentOpenSourceBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        setupRecyclerView()
        observeViewModel()
        viewModel.loadOpenSourceProjects()
    }

    private fun setupRecyclerView() {
        openSourceAdapter = OpenSourceAdapter { 
            // Handle retry click
            viewModel.loadOpenSourceProjects()
        }
        
        binding.repoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = openSourceAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.projects.collect { projects ->
                openSourceAdapter.submitList(projects)
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
}