package com.jrodriguezva.rickandmortykotlin.framework.di

import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import com.jrodriguezva.rickandmortykotlin.testcore.location
import com.jrodriguezva.rickandmortykotlin.testcore.testCharacters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository : RickAndMortyRepository {
    override fun getCharacters(): Flow<List<Character>> = flowOf(testCharacters)


    override fun getLastKnownLocation(locationId: Int): Flow<Resource<Location>> = flowOf(Resource.Success(location))

    override fun checkRequireNewPage(fromInit: Boolean): Flow<Resource<List<Character>>> =
        flowOf(Resource.Success(testCharacters))

    override fun getCharactersLastKnownLocation(characterId: Int) = flowOf(testCharacters)

    override fun getCharacter(characterId: Int): Flow<Character> = flowOf(testCharacters.first { it.id == characterId })

    override fun getCharacterFavorites(): Flow<List<Character>> = flowOf(testCharacters.filter { it.favorite })

    override suspend fun updateFavorite(character: Character) {
        // Do nothing
    }

}