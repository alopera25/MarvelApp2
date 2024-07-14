package com.marvelapp.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marvelapp.domain.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM DbCharacter")
    fun fetchCharacter(): Flow<List<Character>>

    @Query("SELECT * FROM DbCharacter WHERE id = :id")
    fun fetchCharacterById(id: Int): Flow<Character?>

    @Query("SELECT COUNT(*) FROM DbCharacter")
    suspend fun countCharacter(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacter(character: List<Character>)

}
