package com.marvelapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import com.marvelapp.data.datasource.remote.Comic
import com.marvelapp.data.datasource.remote.Event
import com.marvelapp.data.datasource.remote.Serie
import com.marvelapp.data.datasource.CharacterRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CharacterRepository,
    private val id: Int
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

    /*init {
        fetchCharacterDetails()
    }

    private fun fetchCharacterDetails() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            try {
                val character = repository.fetchCharacterById(id)
                _state.value = UiState(loading = false, character = character)
            } catch (e: Exception) {
                _state.value = UiState(loading = false, error = "Failed to fetch character details")
            }
        }
    }
*/
    suspend fun fetchComicDetails(comicId: Int): Comic? {
        return repository.fetchComicDetails(comicId)
    }

    suspend fun fetchSerieDetails(serieId: Int): Serie? {
        return repository.fetchSerieDetails(serieId)
    }

    suspend fun fetchEventDetails(serieId: Int): Event? {
        return repository.fetchEventDetails(serieId)
    }

    fun onFavoriteClicked() {
        message.value = "Favorite Clicked"
    }

    fun onMessageShown() {
        message.value = null
    }
}
