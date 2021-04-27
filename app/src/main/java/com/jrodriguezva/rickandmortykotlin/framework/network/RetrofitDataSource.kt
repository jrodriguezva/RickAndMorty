package com.jrodriguezva.rickandmortykotlin.framework.network

import com.jrodriguezva.rickandmortykotlin.data.datasource.RemoteDataSource
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.FailException
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import com.jrodriguezva.rickandmortykotlin.framework.mappers.toDomain

class RetrofitDataSource(private val rickAndMortyApi: RickAndMortyApi, private val networkUtils: NetworkUtils) :
    RemoteDataSource {

    override suspend fun getCharacters(page: Int): Resource<List<Character>> {
        if (!networkUtils.isNetworkAvailable()) return Resource.Failure(FailException.NoConnectionAvailable)
        return rickAndMortyApi.listCharacter(page).run {
            if (isSuccessful) {
                body()?.let { body -> Resource.Success(body.results.map { it.toDomain(page) }) } ?: Resource.Success(emptyList())
            } else {
                Resource.Failure(FailException.BadRequest)
            }
        }
    }

    override suspend fun getLocation(locationId: Int): Resource<Location> {
        if (!networkUtils.isNetworkAvailable()) return Resource.Failure(FailException.NoConnectionAvailable)
        return rickAndMortyApi.getLocation(locationId).run {
            if (isSuccessful) {
                body()?.let { body ->
                    Resource.Success(body.toDomain())
                } ?: Resource.Failure(FailException.BadRequest)
            } else {
                Resource.Failure(FailException.BadRequest)
            }
        }
    }

    override suspend fun getCharacter(characterId: Int): Resource<Character> {
        if (!networkUtils.isNetworkAvailable()) return Resource.Failure(FailException.NoConnectionAvailable)
        return rickAndMortyApi.getCharacter(characterId).run {
            if (isSuccessful) {
                body()?.let { body -> Resource.Success(body.toDomain()) } ?: Resource.Failure(FailException.BadRequest)
            } else {
                Resource.Failure(FailException.BadRequest)
            }
        }
    }
}
