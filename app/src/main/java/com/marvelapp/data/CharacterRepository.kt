package com.marvelapp.data

import com.marvelapp.data.datasource.CharacterLocalDataSource
import com.marvelapp.data.datasource.CharacterRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

class CharacterRepository(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
) {
    val characters: Flow<List<Character>> = localDataSource.character.onEach { localCharacters ->
        if (localCharacters.isEmpty()) {
            val remoteCharacters = characterRemoteDataSource.fetchCharacters(offset = 0, limit = 20)
            localDataSource.saveCharacter(remoteCharacters!!)
        }
    }

    /* private val _characters = MutableStateFlow<List<Character>>(emptyList())

     suspend fun fetchCharacters(offset: Int, limit: Int) {
         val newCharacters: List<Character> = characterRemoteDataSource.fetchCharacters(offset, limit) ?: return
         _characters.update { it + newCharacters }
     }*/

    fun fetchCharacterById(id: Int): Flow<Character> = localDataSource.fetchCharacterById(id)
        .onEach { character ->
            if (character == null) {
                val remoteCharacter = characterRemoteDataSource.fetchCharacterById(id)
                localDataSource.saveCharacter(listOf(remoteCharacter!!))
            }
        }
        .filterNotNull()

    suspend fun toggleFavorite(character: Character) {
        localDataSource.saveCharacter(listOf(character.copy(isFavorite = !character.isFavorite)))
    }
}