package com.mindorks.framework.mvvm.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class LoginRequest {

    @JsonClass(generateAdapter = true)
    data class ServerLoginRequest(
        @Json(name = "email")
        val email: String,
        @Json(name = "password")
        val password: String
    ) : LoginRequest()

    @JsonClass(generateAdapter = true)
    data class GoogleLoginRequest(
        @Json(name = "google_user_id")
        val googleUserId: String,
        @Json(name = "google_id_token")
        val idToken: String
    ) : LoginRequest()

    @JsonClass(generateAdapter = true)
    data class FacebookLoginRequest(
        @Json(name = "fb_user_id")
        val fbUserId: String,
        @Json(name = "fb_access_token")
        val fbAccessToken: String
    ) : LoginRequest()
}