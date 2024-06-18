package com.marvelapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marvelapp.data.datasource.remote.ComicSummary
import com.marvelapp.data.datasource.remote.EventSummary
import com.marvelapp.data.datasource.remote.SeriesSummary
import com.marvelapp.data.datasource.remote.Thumbnail
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.marvelapp.data.datasource.database.CharacterDao
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: Thumbnail?,
    val comics: List<ComicSummary>?,
    val events: List<EventSummary>?,
    val series: List<SeriesSummary>?
)


