package com.marvelapp.data.datasource

import com.marvelapp.data.Character
import com.marvelapp.data.datasource.remote.CharactersClient
import com.marvelapp.data.datasource.remote.Comic
import com.marvelapp.data.datasource.remote.Event
import com.marvelapp.data.datasource.remote.RemoteCharacter
import com.marvelapp.data.datasource.remote.Serie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRemoteDataSource {

    suspend fun fetchCharacter(offset: Int, limit: Int): List<Character>? = withContext(Dispatchers.IO) {
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

    suspend fun fetchComicDetails(comicId: Int): Comic? = withContext(Dispatchers.IO) {
        try {
            CharactersClient.instance.getComicDetails(comicId)
                .data
                .results
                .firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchSerieDetails(serieId: Int): Serie? = withContext(Dispatchers.IO) {
        try {
            CharactersClient.instance.getSerieDetails(serieId)
                .data
                .results
                .firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchEventDetails(eventId: Int): Event? = withContext(Dispatchers.IO) {
        try {
            CharactersClient.instance.getEventDetails(eventId)
                .data
                .results
                .firstOrNull()
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
    comics = comics?.items,
    events = events?.items,
    series = series?.items
)