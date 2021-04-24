package com.jrodriguezva.rickandmortykotlin.framework.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    @PrimaryKey val id: Int,
    val name: String? = "",
    val type: String? = null,
    val dimension: String? = null
)
