package com.jrodriguezva.rickandmortykotlin.domain.repository

import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): Flow<List<Character>>
    fun getCharactersLastKnownLocation(): Flow<List<Location>>
    suspend fun checkRequireNewPage(fromInit: Boolean): Flow<Resource<List<Character>>>
    suspend fun getCharacter(id: Int)
    fun getCharacterFavorites(): Flow<List<Character>>
    suspend fun updateFavorite(character: Character)
}