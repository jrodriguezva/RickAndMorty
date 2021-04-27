package com.jrodriguezva.rickandmortykotlin.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
@Suppress("ComplexInterface")
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun getAll(): Flow<List<Character>>

    @Query("SELECT * FROM character WHERE favorite = :favorite")
    fun getFavorites(favorite: Boolean = true): Flow<List<Character>>

    @Query("SELECT * FROM character WHERE page = :page")
    fun getCharacterByPage(page: Int): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacter(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    @Query("SELECT IFNULL(MAX(page),0) FROM character")
    suspend fun getLastPage(): Int

    @Update
    suspend fun update(character: Character)

    @Query("SELECT * FROM character WHERE characterId = :characterId")
    fun getCharacterFlow(characterId: Int): Flow<Character>

    @Query("SELECT * FROM character WHERE characterId = :characterId")
    suspend fun getCharacter(characterId: Int): Character?

    @Query(
        """
        SELECT * FROM character WHERE location_locationId = 
        (SELECT location_locationId FROM character WHERE characterID = :characterID)
                """
    )
    fun getCharacterFromLocation(characterID: Int): Flow<List<Character>>
}
