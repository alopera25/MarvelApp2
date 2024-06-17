package com.marvelapp

import android.app.Application
import androidx.room.Room
import com.marvelapp.data.datasource.database.CharacterDatabase

class App : Application() {

    lateinit var db: CharacterDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, CharacterDatabase::class.java, "character-db")
            .build()
    }
}