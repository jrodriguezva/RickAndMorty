package com.jrodriguezva.rickandmortykotlin.framework.mappers

import com.jrodriguezva.rickandmortykotlin.ui.utils.extensions.getIdFromUrl
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import java.util.Locale
import com.jrodriguezva.rickandmortykotlin.framework.local.Character as CharacterDto
import com.jrodriguezva.rickandmortykotlin.framework.local.Location as LocationDto
import com.jrodriguezva.rickandmortykotlin.framework.network.Character as CharacterServer
import com.jrodriguezva.rickandmortykotlin.framework.network.Location as LocationServer

fun CharacterDto.toDomain() = Character(
    characterId,
    name,
    enumValueOf(status.name),
    species,
    enumValueOf(gender.name),
    origin.toDomain(),
    location.toDomain(),
    image,
    page,
    favorite
)

fun LocationDto.toDomain() = Location(locationId, name, type, dimension)


fun Character.toRoom() = CharacterDto(
    id,
    name,
    enumValueOf(status.name),
    species,
    enumValueOf(gender.name),
    origin.toRoom(),
    location.toRoom(),
    image,
    page,
    favorite
)

fun Location.toRoom() = LocationDto(id, name, type, dimension)

fun CharacterServer.toDomain(page: Int? = null) = Character(
    id,
    name,
    enumValueOf(status.toUpperCase(Locale.getDefault())),
    species,
    enumValueOf(gender.toUpperCase(Locale.getDefault())),
    origin.toDomain(),
    location.toDomain(),
    image,
    page,
    false
)

fun LocationServer.toDomain() = Location(url.getIdFromUrl(), name, type, dimension, residents.map { it.getIdFromUrl() })
