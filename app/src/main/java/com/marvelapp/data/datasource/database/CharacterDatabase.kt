package com.marvelapp.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marvelapp.domain.Character

@Database(entities = [DbCharacter::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CharacterDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao

}