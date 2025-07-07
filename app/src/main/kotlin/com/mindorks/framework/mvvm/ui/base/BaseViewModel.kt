package com.mindorks.framework.mvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.data.network.NetworkResult
import com.mindorks.framework.mvvm.data.repository.DataRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Base ViewModel class with common functionality
 */
open class BaseViewModel @Inject constructor(
    protected val dataRepository: DataRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: MutableStateFlow<String?> = _error

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _isLoading.value = false
        _error.value = throwable.message ?: "An unknown error occurred"
    }

    protected fun <T> handleNetworkResult(
        result: NetworkResult<T>,
        onSuccess: (T) -> Unit,
        onError: ((String, Int?) -> Unit)? = null
    ) {
        when (result) {
            is NetworkResult.Loading -> {
                _isLoading.value = result.isLoading
            }
            is NetworkResult.Success -> {
                _isLoading.value = false
                onSuccess(result.data)
            }
            is NetworkResult.Error -> {
                _isLoading.value = false
                onError?.invoke(result.message, result.code) 
                    ?: run { _error.value = result.message }
            }
        }
    }

    protected fun launchWithLoading(block: suspend () -> Unit) {
        viewModelScope.launch(exceptionHandler) {
            _isLoading.value = true
            try {
                block()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun setUserAsLoggedOut() {
        dataRepository.setUserAsLoggedOut()
    }
}