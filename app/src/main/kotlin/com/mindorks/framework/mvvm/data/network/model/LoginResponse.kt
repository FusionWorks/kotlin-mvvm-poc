package com.mindorks.framework.mvvm.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "status_code")
    val statusCode: String?,
    @Json(name = "user_id")
    val userId: Long?,
    @Json(name = "access_token")
    val accessToken: String?,
    @Json(name = "user_name")
    val userName: String?,
    @Json(name = "email")
    val userEmail: String?,
    @Json(name = "server_profile_pic_url")
    val serverProfilePicUrl: String?,
    @Json(name = "fb_profile_pic_url")
    val fbProfilePicUrl: String?,
    @Json(name = "google_profile_pic_url")
    val googleProfilePicUrl: String?,
    @Json(name = "message")
    val message: String?
)