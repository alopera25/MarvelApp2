package com.marvelapp.data.datasource

import com.marvelapp.data.Character
import com.marvelapp.data.datasource.database.CharacterDao

class CharacterLocalDataSource(private val characterDao: CharacterDao) {

    val character = characterDao.fetchCharacter()

    fun fetchCharacterById(id: Int) =characterDao.fetchCharacterById(id)

    suspend fun isEmpty() = characterDao.countCharacter() ==0

    suspend fun saveCharacter(character: List<Character>) = characterDao.saveCharacter(character)

}