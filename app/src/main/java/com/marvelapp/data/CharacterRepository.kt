package com.marvelapp.data

import com.marvelapp.data.datasource.CharacterLocalDataSource
import com.marvelapp.data.datasource.CharacterRemoteDataSource
import com.marvelapp.data.datasource.remote.Comic
import com.marvelapp.data.datasource.remote.Event
import com.marvelapp.data.datasource.remote.Serie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update

class CharacterRepository (
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
){
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    suspend fun fetchCharacters(offset: Int, limit: Int) {
        val newCharacters: List<Character> = characterRemoteDataSource.fetchCharacters(offset, limit) ?: return
        _characters.update { it + newCharacters }
    }

    fun fetchCharacterById(id: Int): Flow<Character> =
        localDataSource.fetchCharacterById(id).transform { localCharacter ->
            val character = localCharacter ?: characterRemoteDataSource.fetchCharacterById(id)?.also { fetchedCharacter ->
                if (fetchedCharacter != null) {
                    localDataSource.saveCharacter(listOf(fetchedCharacter))
                }
            }
            character?.let { emit(it) }
        }

    suspend fun fetchComicDetails(comicId: Int): Comic? = characterRemoteDataSource.fetchComicDetails(comicId)

    suspend fun fetchSerieDetails(serieId: Int): Serie? = characterRemoteDataSource.fetchSerieDetails(serieId)

    suspend fun fetchEventDetails(eventId: Int): Event? = characterRemoteDataSource.fetchEventDetails(eventId)
}