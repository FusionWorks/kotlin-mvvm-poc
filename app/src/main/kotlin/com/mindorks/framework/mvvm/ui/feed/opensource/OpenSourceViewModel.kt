package com.mindorks.framework.mvvm.ui.feed.opensource

import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.data.network.model.OpenSourceResponse
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenSourceViewModel @Inject constructor(
    dataRepository: DataRepository
) : BaseViewModel(dataRepository) {

    private val _projects = MutableStateFlow<List<OpenSourceResponse.Repo>>(emptyList())
    val projects: StateFlow<List<OpenSourceResponse.Repo>> = _projects

    fun loadOpenSourceProjects() {
        viewModelScope.launch(exceptionHandler) {
            isLoading.value = true
            val result = dataRepository.getOpenSourceApiCall()
            handleNetworkResult(
                result = result,
                onSuccess = { response ->
                    _projects.value = response.data ?: emptyList()
                }
            )
        }
    }
}