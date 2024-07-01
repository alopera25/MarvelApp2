package com.marvelapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import com.marvelapp.data.datasource.remote.Comic
import com.marvelapp.data.datasource.remote.Event
import com.marvelapp.data.datasource.remote.Serie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class DetailViewModel(
    repository: CharacterRepository,
    id: Int
) : ViewModel() {

    private val message = MutableStateFlow<String?>(null)
    val state: StateFlow<UiState> =
        combine(repository.fetchCharacterById(id), message) { character, message ->
            UiState(
                loading = false,
                character = character,
                message = message
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )

    data class UiState(
        val loading: Boolean = false,
        val character: Character? = null,
        val message: String? = null
    )



    fun onFavoriteClicked() {
        message.value = "Favorite Clicked"
    }

    fun onMessageShown() {
        message.value = null
    }
}
