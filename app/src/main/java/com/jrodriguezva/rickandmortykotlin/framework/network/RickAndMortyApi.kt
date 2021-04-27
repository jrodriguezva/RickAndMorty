package com.jrodriguezva.rickandmortykotlin.framework.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("api/character")
    suspend fun listCharacter(
        @Query("page") page: Int
    ): Response<CharacterResponse>

    @GET("api/location/{id}")
    suspend fun getLocation(
        @Path("id") locationId: Int
    ): Response<Location>

    @GET("api/character/{id}")
    suspend fun getCharacter(
        @Path("id") characterId: Int
    ): Response<Character>
}
