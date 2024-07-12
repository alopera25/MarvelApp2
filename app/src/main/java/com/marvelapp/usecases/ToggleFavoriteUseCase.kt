package com.marvelapp.usecases

import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository

class ToggleFavoriteUseCase(private val repository: CharacterRepository) {
    suspend operator fun invoke(movie: Character) {
        repository.toggleFavorite(movie)
    }
}