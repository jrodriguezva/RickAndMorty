package com.jrodriguezva.rickandmortykotlin.data.datasource

import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource

interface RemoteDataSource {
    suspend fun getCharacters(page: Int): Resource<List<Character>>
}