package com.marvelapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marvelapp.data.datasource.remote.ComicSummary
import com.marvelapp.data.datasource.remote.EventSummary
import com.marvelapp.data.datasource.remote.SeriesSummary
import com.marvelapp.data.datasource.remote.Thumbnail

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

