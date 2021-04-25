package com.jrodriguezva.rickandmortykotlin.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val image: String,
    val page: Int?,
    var favorite: Boolean,
)