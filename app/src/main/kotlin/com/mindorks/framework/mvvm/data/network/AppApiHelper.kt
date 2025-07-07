package com.mindorks.framework.mvvm.data.network

import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.data.network.model.LoginRequest
import com.mindorks.framework.mvvm.data.network.model.LoginResponse
import com.mindorks.framework.mvvm.data.network.model.LogoutResponse
import com.mindorks.framework.mvvm.data.network.model.OpenSourceResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppApiHelper @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {

    override suspend fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest): NetworkResult<LoginResponse> {
        return safeApiCall { apiService.doServerLogin(request) }
    }

    override suspend fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest): NetworkResult<LoginResponse> {
        return safeApiCall { apiService.doGoogleLogin(request) }
    }

    override suspend fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest): NetworkResult<LoginResponse> {
        return safeApiCall { apiService.doFacebookLogin(request) }
    }

    override suspend fun doLogoutApiCall(): NetworkResult<LogoutResponse> {
        return safeApiCall { apiService.doLogout() }
    }

    override suspend fun getBlogApiCall(): NetworkResult<BlogResponse> {
        return safeApiCall { apiService.getBlogs() }
    }

    override suspend fun getOpenSourceApiCall(): NetworkResult<OpenSourceResponse> {
        return safeApiCall { apiService.getOpenSourceProjects() }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    NetworkResult.Success(body)
                } ?: NetworkResult.Error("Empty response body")
            } else {
                NetworkResult.Error(
                    message = response.message() ?: "Unknown error",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            NetworkResult.Error(
                message = e.message ?: "Network error occurred"
            )
        }
    }
}