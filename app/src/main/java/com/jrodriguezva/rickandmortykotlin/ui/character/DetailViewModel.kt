package com.jrodriguezva.rickandmortykotlin.ui.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Resource
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _spinner = MutableStateFlow(false)
    val spinner: StateFlow<Boolean> get() = _spinner

    private var _location: MutableLiveData<Location> = MutableLiveData()
    val location: LiveData<Location> get() = _location

    val characterId: Int = savedStateHandle.get<Int>(CHARACTER_ID_SAVED_STATE_KEY) ?: 0
    val character: Flow<Character> get() = repository.getCharacter(characterId)
    val charactersLocation: Flow<List<Character>> get() = repository.getCharactersLastKnownLocation(characterId)

    fun getLastLocation(locationId: Int) {
        viewModelScope.launch {
            repository.getLastKnownLocation(locationId).collect {
                when (it) {
                    is Resource.Loading -> _spinner.value = true
                    is Resource.Success -> {
                        _spinner.value = false
                        _location.value = it.data
                    }
                    else -> _spinner.value = false
                }
            }
        }
    }

    fun onClickFavorite(character: Character) {
        viewModelScope.launch {
            repository.updateFavorite(character)
        }
    }

    companion object {
        private const val CHARACTER_ID_SAVED_STATE_KEY = "characterId"
    }
}
