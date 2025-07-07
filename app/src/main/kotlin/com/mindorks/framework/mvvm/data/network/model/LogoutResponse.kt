package com.mindorks.framework.mvvm.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutResponse(
    @Json(name = "status_code")
    val statusCode: String?,
    @Json(name = "message")
    val message: String?
)