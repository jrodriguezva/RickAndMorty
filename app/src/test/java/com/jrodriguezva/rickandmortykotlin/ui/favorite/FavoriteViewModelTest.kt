package com.jrodriguezva.rickandmortykotlin.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import com.jrodriguezva.rickandmortykotlin.testcore.BaseTest
import com.jrodriguezva.rickandmortykotlin.testcore.testCharacter
import com.jrodriguezva.rickandmortykotlin.testcore.testCharacters
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
class FavoriteViewModelTest : BaseTest() {

    @MockK
    lateinit var repository: RickAndMortyRepository

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun `Listening to Flow emits the list from the server`() = runBlocking {
        coEvery { repository.getCharacterFavorites() } returns flowOf(testCharacters)

        viewModel.characters.test {
            testCharacters shouldBeEqualTo expectItem()
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
