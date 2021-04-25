package com.jrodriguezva.rickandmortykotlin.framework.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Character(
    @PrimaryKey val characterId: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    @Embedded(prefix = "origin_")
    val origin: Location,
    @Embedded(prefix = "location_")
    val location: Location,
    val image: String,
    val page: Int? = null,
    val favorite: Boolean
)
