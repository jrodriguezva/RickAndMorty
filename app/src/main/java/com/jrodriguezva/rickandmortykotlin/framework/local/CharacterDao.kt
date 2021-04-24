package com.jrodriguezva.rickandmortykotlin.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun getAll(): Flow<List<Character>>

    @Query("SELECT * FROM character WHERE favorite = :favorite")
    fun getFavorites(favorite: Boolean = true): Flow<List<Character>>

    @Query("SELECT * FROM character WHERE page = :page")
    fun getCharacterByPage(page: Int): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(characters: List<Character>)

    @Query("SELECT IFNULL(MAX(page),0) FROM character")
    suspend fun getLastPage(): Int

    @Update
    suspend fun update(toRoom: Character)

}