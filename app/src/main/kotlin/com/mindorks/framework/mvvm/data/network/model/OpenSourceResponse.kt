package com.mindorks.framework.mvvm.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenSourceResponse(
    @Json(name = "status_code")
    val statusCode: String?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "data")
    val data: List<Repo>?
) {
    @JsonClass(generateAdapter = true)
    data class Repo(
        @Json(name = "project_url")
        val projectUrl: String?,
        @Json(name = "img_url")
        val coverImgUrl: String?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "description")
        val description: String?
    )
}