package com.mindorks.framework.mvvm.ui.login

import androidx.lifecycle.viewModelScope
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.data.model.LoggedInMode
import com.mindorks.framework.mvvm.data.network.model.LoginRequest
import com.mindorks.framework.mvvm.data.repository.DataRepository
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import com.mindorks.framework.mvvm.utils.CommonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    dataRepository: DataRepository
) : BaseViewModel(dataRepository) {

    private val _loginSuccess = MutableSharedFlow<Unit>()
    val loginSuccess: SharedFlow<Unit> = _loginSuccess

    private val _loginError = MutableSharedFlow<Int>()
    val loginError: SharedFlow<Int> = _loginError

    fun onServerLoginClick(email: String, password: String) {
        // Validate input
        when {
            email.isBlank() -> {
                viewModelScope.launch { _loginError.emit(R.string.empty_email) }
                return
            }
            !CommonUtils.isEmailValid(email) -> {
                viewModelScope.launch { _loginError.emit(R.string.invalid_email) }
                return
            }
            password.isBlank() -> {
                viewModelScope.launch { _loginError.emit(R.string.empty_password) }
                return
            }
        }

        launchWithLoading {
            val request = LoginRequest.ServerLoginRequest(email, password)
            val result = dataRepository.doServerLoginApiCall(request)
            
            handleNetworkResult(
                result = result,
                onSuccess = { response ->
                    dataRepository.updateUserInfo(
                        accessToken = response.accessToken,
                        userId = response.userId,
                        loggedInMode = LoggedInMode.SERVER,
                        userName = response.userName,
                        email = response.userEmail,
                        profilePicPath = response.serverProfilePicUrl
                    )
                    viewModelScope.launch { _loginSuccess.emit(Unit) }
                }
            )
        }
    }

    fun onGoogleLoginClick() {
        launchWithLoading {
            val request = LoginRequest.GoogleLoginRequest("test1", "test1")
            val result = dataRepository.doGoogleLoginApiCall(request)
            
            handleNetworkResult(
                result = result,
                onSuccess = { response ->
                    dataRepository.updateUserInfo(
                        accessToken = response.accessToken,
                        userId = response.userId,
                        loggedInMode = LoggedInMode.GOOGLE,
                        userName = response.userName,
                        email = response.userEmail,
                        profilePicPath = response.googleProfilePicUrl
                    )
                    viewModelScope.launch { _loginSuccess.emit(Unit) }
                }
            )
        }
    }

    fun onFacebookLoginClick() {
        launchWithLoading {
            val request = LoginRequest.FacebookLoginRequest("test3", "test4")
            val result = dataRepository.doFacebookLoginApiCall(request)
            
            handleNetworkResult(
                result = result,
                onSuccess = { response ->
                    dataRepository.updateUserInfo(
                        accessToken = response.accessToken,
                        userId = response.userId,
                        loggedInMode = LoggedInMode.FACEBOOK,
                        userName = response.userName,
                        email = response.userEmail,
                        profilePicPath = response.fbProfilePicUrl
                    )
                    viewModelScope.launch { _loginSuccess.emit(Unit) }
                }
            )
        }
    }
}