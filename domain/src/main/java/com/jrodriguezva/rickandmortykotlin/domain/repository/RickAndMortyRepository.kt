package com.jrodriguezva.rickandmortykotlin.domain.repository

import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): Flow<List<Character>>
    fun getLastKnownLocation(locationId: Int): Flow<Resource<Location>>
    fun checkRequireNewPage(fromInit: Boolean): Flow<Resource<List<Character>>>
    fun getCharactersLastKnownLocation(characterId: Int): Flow<List<Character>>
    fun getCharacter(characterId: Int): Flow<Character>
    fun getCharacterFavorites(): Flow<List<Character>>
    suspend fun updateFavorite(character: Character)
}