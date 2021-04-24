package com.jrodriguezva.rickandmortykotlin.framework.network


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "info")
    val info: Info = Info(),
    @Json(name = "results")
    val results: List<Character> = listOf()
)

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

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name")
    val name: String = "",
    @Json(name = "url")
    val url: String = ""
)

