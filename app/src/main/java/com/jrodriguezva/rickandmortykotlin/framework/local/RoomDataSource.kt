package com.jrodriguezva.rickandmortykotlin.framework.local

import com.jrodriguezva.rickandmortykotlin.data.datasource.LocalDataSource
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.framework.mappers.toDomain
import com.jrodriguezva.rickandmortykotlin.framework.mappers.toRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDataSource(db: RickAndMortyDatabase) : LocalDataSource {

    private val characterDao = db.characterDao()
    override fun getCharacters(): Flow<List<Character>> =
        characterDao.getAll().map { characters -> characters.map { it.toDomain() } }

    override suspend fun updateCharacter(character: Character) {
        characterDao.update(character.toRoom())
    }

    override fun getCharacters(page: Int): Flow<List<Character>> =
        characterDao.getCharacterByPage(page).map { characters -> characters.map { it.toDomain() } }

    override fun getCharacterFavorites(): Flow<List<Character>> =
        characterDao.getFavorites().map { characters -> characters.map { it.toDomain() } }


    override suspend fun saveCharacters(characters: List<Character>) {
        characterDao.insertAllCharacter(characters.map { it.toRoom() })
    }

    override suspend fun getCharacter(id: Int): Character {
        TODO("Not yet implemented")
    }

    override fun getCharactersLastKnownLocation(idCharacter: Int): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveLocations(locations: List<Location>) {
        TODO("Not yet implemented")
    }

    override suspend fun getLastPage(): Int = characterDao.getLastPage()

}