package com.jrodriguezva.rickandmortykotlin.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: RickAndMortyRepository) : ViewModel() {

    val characters: Flow<List<Character>> get() = repository.getCharacterFavorites()

    fun onClickFavorite(character: Character) {
        viewModelScope.launch {
            repository.updateFavorite(character)
        }
    }
}
