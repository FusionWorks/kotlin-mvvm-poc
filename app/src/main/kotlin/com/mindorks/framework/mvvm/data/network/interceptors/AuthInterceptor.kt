package com.mindorks.framework.mvvm.data.network.interceptors

import com.mindorks.framework.mvvm.BuildConfig
import com.mindorks.framework.mvvm.data.local.prefs.PreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val preferencesHelper: PreferencesHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        // Add API key to all requests
        requestBuilder.addHeader("api_key", BuildConfig.API_KEY)

        // Add authorization header if user is logged in
        preferencesHelper.getAccessToken()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        preferencesHelper.getCurrentUserId()?.let { userId ->
            requestBuilder.addHeader("user_id", userId.toString())
        }

        return chain.proceed(requestBuilder.build())
    }
}