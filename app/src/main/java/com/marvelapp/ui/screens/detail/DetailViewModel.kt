package com.marvelapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelapp.Result
import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import com.marvelapp.ifSuccess
import com.marvelapp.stateAsResultIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CharacterRepository,
    id: Int
) : ViewModel() {

    val state: StateFlow<Result<Character>> = repository.fetchCharacterById(id)
        .stateAsResultIn(scope = viewModelScope)

    fun onFavoriteClicked() {
        state.value.ifSuccess {
            viewModelScope.launch {
                repository.toggleFavorite(it)
            }
        }
    }

}
