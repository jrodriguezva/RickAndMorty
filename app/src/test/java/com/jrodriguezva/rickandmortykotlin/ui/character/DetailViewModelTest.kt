package com.jrodriguezva.rickandmortykotlin.ui.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import com.jrodriguezva.rickandmortykotlin.testcore.BaseTest
import com.jrodriguezva.rickandmortykotlin.testcore.testCharacter
import com.jrodriguezva.rickandmortykotlin.testcore.testCharacters
import com.jrodriguezva.rickandmortykotlin.testcore.testLocation
import com.jrodriguezva.rickandmortykotlin.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class DetailViewModelTest : BaseTest() {

    @MockK
    lateinit var repository: RickAndMortyRepository

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { repository.checkRequireNewPage(true) } returns flowOf(Resource.Loading)
        val savedStateHandle: SavedStateHandle = mockk()
        every { savedStateHandle.get<Int>(any()) } returns 1
        viewModel = DetailViewModel(repository, savedStateHandle)
    }

    @Test
    fun `Spinner should be true when emit loading`() = runBlocking {
        coEvery {
            repository.getLastKnownLocation(any())
        } returns flowOf(Resource.Loading)

        viewModel.getLastLocation(1)
        viewModel.spinner.test {
            true shouldBeEqualTo expectItem()
        }
    }

    @Test
    fun `Spinner should be false when emit success`() = runBlocking {
        coEvery {
            repository.getLastKnownLocation(any())
        } returns flowOf(Resource.Success(testLocation))

        viewModel.getLastLocation(1)

        viewModel.spinner.test {
            false shouldBeEqualTo expectItem()
        }
    }

    @Test
    fun `Spinner should be false when emit fail`() = runBlocking {
        coEvery {
            repository.getLastKnownLocation(any())
        } returns flowOf(Resource.Failure(mockk()))

        viewModel.getLastLocation(1)

        viewModel.spinner.test {
            false shouldBeEqualTo expectItem()
        }
    }

    @Test
    fun `location should be testLocation if server return success`() {
        coEvery {
            repository.getLastKnownLocation(any())
        } returns flowOf(Resource.Success(testLocation))

        viewModel.getLastLocation(1)

        val result = viewModel.location.getOrAwaitValue()

        result shouldBeEqualTo testLocation
    }

    @Test
    fun testGetCharacterId() {
        viewModel.characterId shouldBe 1
    }

    @Test
    fun testGetCharacter() = runBlocking {
        coEvery { repository.getCharacter(any()) } returns flowOf(testCharacter)

        viewModel.character.test {
            expectItem() shouldBeEqualTo testCharacter
            expectComplete()
        }
    }

    @Test
    fun testGetCharactersLocation() = runBlocking {
        coEvery { repository.getCharactersLastKnownLocation(any()) } returns flowOf(testCharacters)

        viewModel.charactersLocation.test {
            expectItem() shouldBeEqualTo testCharacters
            expectComplete()
        }
    }

    @Test
    fun `onClickFavorite should be update favorite`() = runBlocking {
        viewModel.onClickFavorite(testCharacter)

        coVerify(exactly = 1) {
            repository.updateFavorite(testCharacter)
        }
    }
}
