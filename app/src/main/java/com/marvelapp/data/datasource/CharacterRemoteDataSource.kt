package com.marvelapp.data.datasource

import com.marvelapp.domain.Character
import com.marvelapp.data.datasource.remote.CharactersClient
import com.marvelapp.data.datasource.remote.RemoteCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRemoteDataSource {

    suspend fun fetchCharacters(offset: Int, limit: Int): List<Character>? =
        withContext(Dispatchers.IO) {
            try {
                CharactersClient.instance.fetchCharacter(offset, limit)
                    .data
                    .results
                    .map { it.toDomainModel() }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    suspend fun fetchCharacterById(characterId: Int): Character? = withContext(Dispatchers.IO) {
        try {
            CharactersClient.instance.fetchCharacterById(characterId)
                .data
                .results
                .firstOrNull()
                ?.toDomainModel()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

private fun RemoteCharacter.toDomainModel() = Character(
    id = id,
    name = name,
    description = description,
    thumbnail = thumbnail,
    false
)