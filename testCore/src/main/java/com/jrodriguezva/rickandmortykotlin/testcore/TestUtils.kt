package com.jrodriguezva.rickandmortykotlin.testcore

import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Gender
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Status


val testCharacter = Character(
    id = 110,
    name = "Eli's Girlfriend",
    status = Status.ALIVE,
    species = "Human",
    gender = Gender.FEMALE,
    origin = Location(id = 8, name = "Post-Apocalyptic Earth"),
    location = Location(id = 8, name = "Post-Apocalyptic Earth"),
    image = "https://rickandmortyapi.com/api/character/avatar/110.jpeg",
    page = 3,
    favorite = true
)

val testCharacters = listOf(
    Character(
        id = 1,
        name = "Rick Sanchez",
        status = Status.ALIVE,
        species = "Human",
        gender = Gender.MALE,
        origin = Location(id = 1, name = "Earth (C-137)"),
        location = Location(id = 20, name = "Earth (Replacement Dimension)"),
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        page = 1,
        favorite = true
    ),
    Character(
        id = 2,
        name = "Morty Smith",
        status = Status.ALIVE,
        species = "Human",
        gender = Gender.MALE,
        origin = Location(id = 1, name = "Earth (C-137)"),
        location = Location(id = 20, name = "Earth (Replacement Dimension)"),
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        page = 1,
        favorite = false
    ),
    Character(
        id = 6,
        name = "Abadango Cluster Princess",
        status = Status.ALIVE,
        species = "Alien",
        gender = Gender.FEMALE,
        origin = Location(id = 2, name = "Abadango"),
        location = Location(id = 2, name = "Abadango"),
        image = "https://rickandmortyapi.com/api/character/avatar/6.jpeg",
        page = 2,
        favorite = false
    ),
)

var testLocation = Location(
    id = 20,
    name = "Earth (Replacement Dimension)",
    type = "Planet",
    dimension = "Replacement Dimension",
    resident = listOf(1, 2, 3, 4, 5)
)


var testLocations = listOf(
    Location(
        id = 1,
        name = "Earth (C-137)",
        type = "Planet",
        dimension = "Dimension C-137",
        resident = listOf(11, 12, 23, 45, 95)
    ), Location(
        id = 20,
        name = "Earth (Replacement Dimension)",
        type = "Planet",
        dimension = "Replacement Dimension",
        resident = listOf(1, 2, 3, 4, 5)
    ), Location(
        id = 6,
        name = "Interdimensional Cable",
        type = "TV",
        dimension = "unknown",
        resident = listOf(1, 12, 23, 4, 5)
    ),
)