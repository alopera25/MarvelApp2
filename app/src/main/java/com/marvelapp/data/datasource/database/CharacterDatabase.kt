package com.marvelapp.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marvelapp.data.Character

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao

}