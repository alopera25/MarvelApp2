package com.marvelapp.data

import com.marvelapp.data.datasource.CharacterLocalDataSource
import com.marvelapp.data.datasource.CharacterRemoteDataSource
import com.marvelapp.data.datasource.remote.Comic
import com.marvelapp.data.datasource.remote.Event
import com.marvelapp.data.datasource.remote.Serie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class CharacterRepository (
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource
){
    val characters: Flow<List<Character>> = localDataSource.characters.transform { localCharacters ->
        val characters = if (localCharacters.isNotEmpty()) {
            localCharacters
        } else {
            characterRemoteDataSource.fetchCharacter(0, 18)?.also { fetchedCharacters ->
                if (fetchedCharacters != null) {
                    localDataSource.saveCharacter(fetchedCharacters)
                }
            }
<<<<<<< Updated upstream
        emit(characters!!)
    }

    fun fetchCharacterById(id: Int): Flow<Character> =
        localDataSource.fetchCharacterById(id).transform { localCharacters ->
            val character = localCharacters
                ?: characterRemoteDataSource.fetchCharacterById(id).also { localDataSource.saveCharacter(listOf(it!!)) }
            emit(character!!)
        }

    /*suspend fun fetchCharacter(offset: Int, limit: Int): List<Character> {
        if (localDataSource.isEmpty()) {
            val character = characterRemoteDataSource.fetchCharacter(offset, limit)
            if (character != null) {
                localDataSource.saveCharacter(character)
=======
        }
        characters?.let { emit(it) }
    }

    fun fetchCharacterById(id: Int): Flow<Character> =
        localDataSource.fetchCharacterById(id).transform { localCharacter ->
            val character = localCharacter ?: characterRemoteDataSource.fetchCharacterById(id)?.also { fetchedCharacter ->
                if (fetchedCharacter != null) {
                    localDataSource.saveCharacter(listOf(fetchedCharacter))
                }
>>>>>>> Stashed changes
            }
            character?.let { emit(it) }
        }

    suspend fun fetchComicDetails(comicId: Int): Comic? = characterRemoteDataSource.fetchComicDetails(comicId)

    suspend fun fetchSerieDetails(serieId: Int): Serie? = characterRemoteDataSource.fetchSerieDetails(serieId)

    suspend fun fetchEventDetails(eventId: Int): Event? = characterRemoteDataSource.fetchEventDetails(eventId)
}


