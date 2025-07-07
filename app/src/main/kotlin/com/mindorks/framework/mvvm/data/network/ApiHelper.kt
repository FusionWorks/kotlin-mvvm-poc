package com.mindorks.framework.mvvm.data.network

import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.data.network.model.LoginRequest
import com.mindorks.framework.mvvm.data.network.model.LoginResponse
import com.mindorks.framework.mvvm.data.network.model.LogoutResponse
import com.mindorks.framework.mvvm.data.network.model.OpenSourceResponse

interface ApiHelper {

    suspend fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest): NetworkResult<LoginResponse>

    suspend fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest): NetworkResult<LoginResponse>

    suspend fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest): NetworkResult<LoginResponse>

    suspend fun doLogoutApiCall(): NetworkResult<LogoutResponse>

    suspend fun getBlogApiCall(): NetworkResult<BlogResponse>

    suspend fun getOpenSourceApiCall(): NetworkResult<OpenSourceResponse>
}