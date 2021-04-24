package com.jrodriguezva.rickandmortykotlin.data.datasource

import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getCharacters(): Flow<List<Character>>
    suspend fun saveCharacters(characters: List<Character>)
    suspend fun getCharacter(id: Int): Character

    fun getCharactersLastKnownLocation(idCharacter: Int): Flow<List<Location>>
    suspend fun saveLocations(locations: List<Location>)
    suspend fun getLastPage(): Int
    fun getCharacters(page: Int): Flow<List<Character>>
}