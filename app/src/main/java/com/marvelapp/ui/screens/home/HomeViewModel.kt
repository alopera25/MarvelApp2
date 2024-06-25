package com.marvelapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val uiReady = MutableStateFlow(false)

    val state: StateFlow<UiState> = uiReady
        .filter { it }
        .flatMapLatest { repository.characters }
        .map { UiState(characters = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )

    fun onUiReady() {
        uiReady.value = true
    }

    data class UiState(
        val loading: Boolean = false,
        val characters: List<Character> = emptyList()
    )

}

class HomeViewModelFactory(private val characterRepository: CharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(characterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
