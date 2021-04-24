package com.jrodriguezva.rickandmortykotlin.framework.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RickAndMortyApi {
    @GET("api/character")
    suspend fun listCharacter(
        @Query("page") page: Int
    ): Response<CharacterResponse>

}