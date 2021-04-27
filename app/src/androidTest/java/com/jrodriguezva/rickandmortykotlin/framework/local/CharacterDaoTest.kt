package com.jrodriguezva.rickandmortykotlin.framework.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jrodriguezva.rickandmortykotlin.framework.mappers.toRoom
import com.jrodriguezva.rickandmortykotlin.testcore.testCharacter
import com.jrodriguezva.rickandmortykotlin.testcore.testCharacters
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var characterDao: CharacterDao
    private lateinit var db: RickAndMortyDatabase

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, RickAndMortyDatabase::class.java).build()
        characterDao = db.characterDao()

        val user = testCharacters.map { it.toRoom() }
        characterDao.insertAllCharacter(user)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getCharacter() = runBlocking {

        val byId = characterDao.getCharacter(1)

        assertNotNull(byId)
        assertEquals(byId!!.characterId, 1)
    }

    @Test
    fun getCharacterFlow() = runBlocking {

        val byId = characterDao.getCharacterFlow(1).first()

        assertNotNull(byId)
        assertEquals(byId.characterId, 1)
        assertEquals(byId.name, "Rick Sanchez")
    }

    @Test
    fun insert() = runBlocking {

        val byId = characterDao.getAll().first()

        assertNotNull(byId)
        assertEquals(byId.size, 3)
        assertEquals(byId[0].name, testCharacters[0].name)

        characterDao.insert(testCharacter.toRoom())

        assertNotNull(byId)
        assertEquals(byId.size, 4)
    }

    @Test
    fun getAll() = runBlocking {

        val byId = characterDao.getAll().first()

        assertNotNull(byId)
        assertEquals(byId.size, 3)
        assertEquals(byId[0].name, testCharacters[0].name)
    }

    @Test
    fun getCharacterFromLocation() = runBlocking {

        val byId = characterDao.getCharacterFromLocation(1).first()

        assertNotNull(byId)
        assertEquals(byId.size, 2)
        assertEquals(byId[0].name, testCharacters[0].name)
    }

    @Test
    fun writeCharacterListAndGetFavorites() = runBlocking {

        val byId = characterDao.getFavorites().first()

        assertNotNull(byId)
        assertEquals(byId.size, 1)
        assertEquals(byId[0].name, testCharacters[0].name)
    }

    @Test
    fun getCharacterByPage() = runBlocking {

        val byId = characterDao.getCharacterByPage(1).first()

        assertNotNull(byId)
        assertEquals(byId.size, 2)
        assertEquals(byId[0].name, testCharacters[0].name)
    }

    @Test
    fun getLastPage() = runBlocking {

        val page = characterDao.getLastPage()

        assertNotNull(page)
        assertEquals(page, 2)
    }

    @Test
    fun update() = runBlocking {

        val byId = characterDao.getCharacter(1)

        assertNotNull(byId)
        assertEquals(byId!!.name, "Rick Sanchez")
        assertTrue(byId.favorite)

        val character = Character(
            characterId = 1,
            name = "Rick",
            status = Status.ALIVE,
            species = "Human",
            gender = Gender.MALE,
            origin = Location(locationId = 1, name = "Earth (C-137)"),
            location = Location(locationId = 20, name = "Earth (Replacement Dimension)"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            page = 1,
            favorite = false
        )
        characterDao.update(character)

        val byId2 = characterDao.getCharacter(1)
        assertEquals(byId2!!.name, "Rick")
        assertFalse(byId2.favorite)
    }
}
