package com.marvelapp.usecases

import com.marvelapp.data.Character
import com.marvelapp.data.CharacterRepository
import kotlinx.coroutines.flow.Flow

class FetchCharactersUseCase(private val characterRepository: CharacterRepository) {
    operator fun invoke(): Flow<List<Character>> = characterRepository.characters
}