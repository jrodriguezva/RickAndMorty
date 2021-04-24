package com.jrodriguezva.rickandmortykotlin.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
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
class MainViewModel @Inject constructor(private val repository: RickAndMortyRepository) : ViewModel() {
    private val _spinner = MutableStateFlow(false)
    val spinner: StateFlow<Boolean> get() = _spinner

    val characters: Flow<List<Character>> get() = repository.getCharacters()

    init {
        getNextPage(true)
    }

    fun getNextPage(fromInit: Boolean = false) {
        viewModelScope.launch {
            repository.checkRequireNewPage(fromInit).collect {
                when (it) {
                    is Resource.Loading -> _spinner.value = true
                    else -> _spinner.value = false
                }
            }
        }
    }
}
