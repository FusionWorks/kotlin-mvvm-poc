package com.mindorks.framework.mvvm.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BlogResponse(
    @Json(name = "status_code")
    val statusCode: String?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "data")
    val data: List<Blog>?
) {
    @JsonClass(generateAdapter = true)
    data class Blog(
        @Json(name = "blog_url")
        val blogUrl: String?,
        @Json(name = "img_url")
        val coverImgUrl: String?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "author")
        val author: String?,
        @Json(name = "published_at")
        val date: String?
    )
}