package com.marvelapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CharacterRepository) : ViewModel() {
    private var currentPage = 0
    private val pageSize = 20

    val state: StateFlow<UiState> = repository.characters
        .map { UiState(characters = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )

    fun fetchNextPage() {
        viewModelScope.launch {
            repository.fetchCharacters(currentPage * pageSize, pageSize)
            currentPage++
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val characters: List<Character> = emptyList()
    )
}