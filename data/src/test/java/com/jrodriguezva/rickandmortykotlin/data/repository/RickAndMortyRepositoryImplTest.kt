package com.jrodriguezva.rickandmortykotlin.data.repository

import com.jrodriguezva.rickandmortykotlin.data.datasource.LocalDataSource
import com.jrodriguezva.rickandmortykotlin.data.datasource.RemoteDataSource
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import com.jrodriguezva.rickandmortykotlin.testcore.BaseTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RickAndMortyRepositoryImplTest : BaseTest() {

    @MockK
    lateinit var localDataSource: LocalDataSource

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repository: RickAndMortyRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = RickAndMortyRepositoryImpl(localDataSource, remoteDataSource, coroutinesTestRule.testDispatcherProvider)
    }

    @Test
    fun `getCharacter should get character flow from local data source`() {
        val id = 1
        val character: Character = mockk()

        coEvery {
            localDataSource.getCharacterFlow(id)
        } returns flowOf(character)

        repository.getCharacter(id)

        verify { localDataSource.getCharacterFlow(id) }
    }

    @Test
    fun `getLastKnownLocation emit Failure if server fail`() = runBlocking {
        val id = 1
        val failure: Resource.Failure = mockk()

        coEvery {
            remoteDataSource.getLocation(id)
        } returns failure

        val result = repository.getLastKnownLocation(id).toList()

        result[0] shouldBe Resource.Loading
        result[1] shouldBe failure
    }

    @Test
    fun `getLastKnownLocation emit Resource if response server is ok`() = runBlocking {
        val id = 1
        val location: Resource.Success<Location> = mockk()
        every { location.data } returns mockk()
        every { location.data.resident } returns null
        coEvery { remoteDataSource.getLocation(id) } returns location

        val result = repository.getLastKnownLocation(id).toList()

        result[0] shouldBe Resource.Loading
        result[1] shouldBe location
    }

    @Test
    fun `getLastKnownLocation emit Resource if server is ok and not call getCharacter when all is in database`() =
        runBlocking {
            val id = 1
            val location: Resource.Success<Location> = mockk()
            every { location.data } returns mockk()
            every { location.data.resident } returns listOf(1, 2)
            coEvery { remoteDataSource.getLocation(id) } returns location
            coEvery { localDataSource.getCharacter(any()) } returns mockk()

            val result = repository.getLastKnownLocation(id).toList()

            result[0] shouldBe Resource.Loading
            result[1] shouldBe location
            coVerify(exactly = 2) { localDataSource.getCharacter(any()) }
            coVerify(exactly = 0) { localDataSource.saveCharacter(any()) }
        }

    @Test
    fun `getLastKnownLocation emit Resource if server is ok and call getCharacter when is not in database`() =
        runBlocking {
            val id = 1
            val location: Resource.Success<Location> = mockk()
            every { location.data } returns mockk()
            every { location.data.resident } returns listOf(1, 2)
            coEvery { remoteDataSource.getLocation(id) } returns location
            coEvery { localDataSource.getCharacter(any()) } returns null

            val character: Resource.Success<Character> = mockk()
            coEvery { character.data } returns mockk()

            coEvery { remoteDataSource.getCharacter(any()) } returns character

            val result = repository.getLastKnownLocation(id).toList()

            result[0] shouldBe Resource.Loading
            result[1] shouldBe location
            coVerify(exactly = 2) { localDataSource.getCharacter(any()) }
            coVerify(exactly = 2) { remoteDataSource.getCharacter(any()) }
            coVerify(exactly = 2) { localDataSource.saveCharacter(any()) }
        }

    @Test
    fun getCharactersLastKnownLocation() {
        coEvery { localDataSource.getCharactersLastKnownLocation(any()) } returns mockk()
        repository.getCharactersLastKnownLocation(1)

        verify {
            localDataSource.getCharactersLastKnownLocation(1)
        }
    }

    @Test
    fun `checkRequireNewPage call server if fromInit is true and page is 1`() =
        runBlocking {
            val listCharacters: Resource.Success<List<Character>> = mockk()

            coEvery { localDataSource.getLastPage() } returns 0
            coEvery { remoteDataSource.getCharacters(1) } returns listCharacters
            val characters: List<Character> = mockk()
            coEvery { listCharacters.data } returns characters

            val result = repository.checkRequireNewPage(true).toList()

            coVerify {
                remoteDataSource.getCharacters(1)
                localDataSource.saveCharacters(characters)
            }

            result.size shouldBeEqualTo 2
            result[0] shouldBeInstanceOf Resource.Loading::class
            result[1] shouldBeInstanceOf Resource.Success::class
        }

    @Test
    fun `checkRequireNewPage emit error if server fail`() =
        runBlocking {
            val listCharacters: Resource.Failure = mockk()

            coEvery { localDataSource.getLastPage() } returns 0
            coEvery { remoteDataSource.getCharacters(1) } returns listCharacters

            val result = repository.checkRequireNewPage(true).toList()

            coVerify {
                remoteDataSource.getCharacters(1)
            }

            result.size shouldBeEqualTo 2
            result[0] shouldBeInstanceOf Resource.Loading::class
            result[1] shouldBeInstanceOf Resource.Failure::class
        }

    @Test
    fun updateFavorite() = runBlocking {
        val character: Character = mockk()

        repository.updateFavorite(character)

        coVerify {
            localDataSource.updateCharacter(any())
        }
    }

    @Test
    fun getCharacters() = runBlocking {

        val character: Character = mockk()
        val listCharacters: List<Character> = listOf(character, character)

        coEvery {
            localDataSource.getCharacters()
        } returns flowOf(listCharacters)

        val result = repository.getCharacters().first()

        verify { localDataSource.getCharacters() }

        result.size shouldBeEqualTo 2
        result[0] shouldBeInstanceOf Character::class
    }

    @Test
    fun getCharacterFavorites() = runBlocking {
        val character: Character = mockk()
        val listCharacters: List<Character> = listOf(character, character)

        coEvery {
            localDataSource.getCharacterFavorites()
        } returns flowOf(listCharacters)

        val result = repository.getCharacterFavorites().first()

        verify { localDataSource.getCharacterFavorites() }

        result.size shouldBeEqualTo 2
        result[0] shouldBeInstanceOf Character::class
    }
}
