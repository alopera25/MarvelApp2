package com.marvelapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import com.marvelapp.stateAsResultIn
import com.marvelapp.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

class HomeViewModel(repository: CharacterRepository) : ViewModel() {

    private val uiReady = MutableStateFlow(true)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<Result<List<Character>>> = uiReady
        .filter { it }
        .flatMapLatest { repository.characters }
        .stateAsResultIn(viewModelScope)
}

