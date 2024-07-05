package com.marvelapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CharacterRepository,
    id: Int
) : ViewModel() {

    val state: StateFlow<UiState> = repository.fetchCharacterById(id)
        .map { UiState(character = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )

    data class UiState(
        val loading: Boolean = false,
        val character: Character? = null
    )

    fun onFavoriteClicked() {
        state.value.character?.let {
            viewModelScope.launch {
                repository.toggleFavorite(it)
            }
        }
    }

}
