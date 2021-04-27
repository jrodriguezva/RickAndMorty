package com.jrodriguezva.rickandmortykotlin.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "count")
    val count: Int = 0,
    @Json(name = "next")
    val next: String = "",
    @Json(name = "pages")
    val pages: Int = 0,
    @Json(name = "prev")
    val prev: Any? = null
)
