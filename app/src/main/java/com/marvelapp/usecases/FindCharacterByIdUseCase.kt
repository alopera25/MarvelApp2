package com.marvelapp.usecases

import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import kotlinx.coroutines.flow.Flow

class FindCharacterByIdUseCase(private val repository: CharacterRepository) {
    operator fun invoke(id: Int): Flow<Character> = repository.fetchCharacterById(id)
}