package com.jrodriguezva.rickandmortykotlin.data.repository

import com.jrodriguezva.rickandmortykotlin.data.datasource.LocalDataSource
import com.jrodriguezva.rickandmortykotlin.data.datasource.RemoteDataSource
import com.jrodriguezva.rickandmortykotlin.domain.dispatcher.DefaultDispatcherProvider
import com.jrodriguezva.rickandmortykotlin.domain.dispatcher.DispatcherProvider
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class RickAndMortyRepositoryImpl constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: DispatcherProvider = DefaultDispatcherProvider()
) : RickAndMortyRepository {
    override fun getCharacters(): Flow<List<Character>> =
        localDataSource.getCharacters()


    override fun getLastKnownLocation(locationId: Int) = flow {
        emit(Resource.Loading)
        when (val location = remoteDataSource.getLocation(locationId)) {
            is Resource.Success -> {
                localDataSource.saveLocation(location.data)
                loadAllResident(location)
                emit(location)
            }
            else -> emit(location)
        }
    }

    override fun getCharactersLastKnownLocation(characterId: Int) =
        localDataSource.getCharactersLastKnownLocation(characterId)

    private suspend fun loadAllResident(location: Resource.Success<Location>) {
        withContext(dispatcher.io) {
            location.data.resident?.map {
                async {
                    if (localDataSource.getCharacter(it) == null) {
                        when (val characters = remoteDataSource.getCharacter(it)) {
                            is Resource.Success -> {
                                localDataSource.saveCharacter(characters.data)
                            }
                        }
                    }
                }
            }?.awaitAll()
        }
    }


    override fun checkRequireNewPage(fromInit: Boolean) = flow {
        val page = localDataSource.getLastPage() + 1
        if (!fromInit || page == 1) {
            emit(Resource.Loading)
            when (val characters = remoteDataSource.getCharacters(page)) {
                is Resource.Success -> {
                    localDataSource.saveCharacters(characters.data)
                    emit(characters)
                }
                else -> emit(characters)
            }
        }
    }

    override suspend fun updateFavorite(character: Character) {
        localDataSource.updateCharacter(character)
    }

    override fun getCharacter(characterId: Int): Flow<Character> = localDataSource.getCharacterFlow(characterId)

    override fun getCharacterFavorites(): Flow<List<Character>> = localDataSource.getCharacterFavorites()

}