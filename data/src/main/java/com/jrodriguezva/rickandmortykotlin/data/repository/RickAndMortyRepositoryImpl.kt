package com.jrodriguezva.rickandmortykotlin.data.repository

import com.jrodriguezva.rickandmortykotlin.data.datasource.LocalDataSource
import com.jrodriguezva.rickandmortykotlin.data.datasource.RemoteDataSource
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RickAndMortyRepositoryImpl constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RickAndMortyRepository {
    override fun getCharacters(): Flow<List<Character>> {
        return localDataSource.getCharacters()
    }

    override fun getCharactersLastKnownLocation(): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkRequireNewPage(fromInit: Boolean) = flow {
        emit(Resource.Loading)
        val page = localDataSource.getLastPage() + 1
        if (!fromInit || page == 1) {
            when (val characters = remoteDataSource.getCharacters(page)) {
                is Resource.Success -> {
                    localDataSource.saveCharacters(characters.data)
                    emit(characters)
                }
                else -> emit(characters)
            }
        }
    }

    override suspend fun getCharacter(id: Int) {
        TODO("Not yet implemented")
    }
}