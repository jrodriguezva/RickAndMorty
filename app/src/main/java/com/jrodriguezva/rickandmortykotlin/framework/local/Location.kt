package com.jrodriguezva.rickandmortykotlin.framework.local

data class Location(
    val locationId: Int,
    val name: String? = "",
    val type: String? = null,
    val dimension: String? = null
)
