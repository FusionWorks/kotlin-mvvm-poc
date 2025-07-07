package com.mindorks.framework.mvvm.ui.feed.blogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.databinding.FragmentBlogBinding
import com.mindorks.framework.mvvm.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BlogFragment : BaseFragment<FragmentBlogBinding>() {

    private val viewModel: BlogViewModel by viewModels()
    private lateinit var blogAdapter: BlogAdapter

    companion object {
        fun newInstance(): BlogFragment {
            return BlogFragment()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBlogBinding {
        return FragmentBlogBinding.inflate(inflater, container, false)
    }

    override fun setupView() {
        setupRecyclerView()
        observeViewModel()
        viewModel.loadBlogs()
    }

    private fun setupRecyclerView() {
        blogAdapter = BlogAdapter { 
            // Handle retry click
            viewModel.loadBlogs()
        }
        
        binding.blogRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = blogAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.blogs.collect { blogs ->
                blogAdapter.submitList(blogs)
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