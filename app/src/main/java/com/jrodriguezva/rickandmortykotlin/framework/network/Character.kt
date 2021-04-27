package com.jrodriguezva.rickandmortykotlin.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    @Json(name = "created")
    val created: String = "",
    @Json(name = "episode")
    val episode: List<String> = listOf(),
    @Json(name = "gender")
    val gender: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "image")
    val image: String = "",
    @Json(name = "location")
    val location: Location = Location(),
    @Json(name = "name")
    val name: String = "",
    @Json(name = "origin")
    val origin: Location = Location(),
    @Json(name = "species")
    val species: String = "",
    @Json(name = "status")
    val status: String = "",
    @Json(name = "type")
    val type: String = "",
    @Json(name = "url")
    val url: String = ""
)
