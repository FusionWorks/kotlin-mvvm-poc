package com.mindorks.framework.mvvm.ui.feed.blogs

import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    dataRepository: DataRepository
) : BaseViewModel(dataRepository) {

    private val _blogs = MutableStateFlow<List<BlogResponse.Blog>>(emptyList())
    val blogs: StateFlow<List<BlogResponse.Blog>> = _blogs

    fun loadBlogs() {
        viewModelScope.launch(exceptionHandler) {
            isLoading.value = true
            val result = dataRepository.getBlogApiCall()
            handleNetworkResult(
                result = result,
                onSuccess = { response ->
                    _blogs.value = response.data ?: emptyList()
                }
            )
        }
    }
}