package com.jrodriguezva.rickandmortykotlin.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "info")
    val info: Info? = Info(),
    @Json(name = "results")
    val results: List<Character> = listOf()
)
