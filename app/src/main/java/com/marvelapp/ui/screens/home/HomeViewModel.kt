package com.marvelapp.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import com.marvelapp.data.datasource.CharacterRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
