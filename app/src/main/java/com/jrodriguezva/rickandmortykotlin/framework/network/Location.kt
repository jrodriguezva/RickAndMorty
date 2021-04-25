package com.jrodriguezva.rickandmortykotlin.framework.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "created")
    val created: String = "",
    @Json(name = "dimension")
    val dimension: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "residents")
    val residents: List<String> = listOf(),
    @Json(name = "type")
    val type: String = "",
    @Json(name = "url")
    val url: String = ""
)