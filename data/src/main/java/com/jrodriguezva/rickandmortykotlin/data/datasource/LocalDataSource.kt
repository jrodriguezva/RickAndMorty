package com.jrodriguezva.rickandmortykotlin.data.datasource

import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getCharacters(): Flow<List<Character>>
    fun getCharacterFavorites(): Flow<List<Character>>
    fun getCharacters(page: Int): Flow<List<Character>>
    fun getCharactersLastKnownLocation(idCharacter: Int): Flow<List<Character>>
    fun getCharacterFromLocation(locationId: Int): Flow<List<Character>>
    fun getCharacterFlow(characterId: Int): Flow<Character>

    suspend fun saveLocation(locations: Location)
    suspend fun saveCharacters(characters: List<Character>)
    suspend fun getLastPage(): Int
    suspend fun updateCharacter(character: Character)
    suspend fun saveCharacter(data: Character)
    suspend fun getCharacter(characterId: Int): Character?
}