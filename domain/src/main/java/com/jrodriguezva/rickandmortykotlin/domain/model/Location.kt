package com.jrodriguezva.rickandmortykotlin.domain.model

data class Location(
    val id: Int,
    val name: String? = "",
    val type: String? = null,
    val dimension: String? = null,
    val resident: List<Int>? = emptyList()
)
