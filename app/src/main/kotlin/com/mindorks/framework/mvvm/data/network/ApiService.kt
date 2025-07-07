package com.mindorks.framework.mvvm.data.network

import com.mindorks.framework.mvvm.data.network.model.BlogResponse
import com.mindorks.framework.mvvm.data.network.model.LoginRequest
import com.mindorks.framework.mvvm.data.network.model.LoginResponse
import com.mindorks.framework.mvvm.data.network.model.LogoutResponse
import com.mindorks.framework.mvvm.data.network.model.OpenSourceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/588d15f5100000a8072d2945")
    suspend fun doServerLogin(
        @Body request: LoginRequest.ServerLoginRequest
    ): Response<LoginResponse>

    @POST("/588d14f4100000a9072d2943")
    suspend fun doGoogleLogin(
        @Body request: LoginRequest.GoogleLoginRequest
    ): Response<LoginResponse>

    @POST("/588d15d3100000ae072d2944")
    suspend fun doFacebookLogin(
        @Body request: LoginRequest.FacebookLoginRequest
    ): Response<LoginResponse>

    @POST("/588d161c100000a9072d2946")
    suspend fun doLogout(): Response<LogoutResponse>

    @GET("/5926ce9d11000096006ccb30")
    suspend fun getBlogs(): Response<BlogResponse>

    @GET("/5926c34212000035026871cd")
    suspend fun getOpenSourceProjects(): Response<OpenSourceResponse>
}