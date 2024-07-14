package com.marvelapp.data.datasource.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marvelapp.data.datasource.remote.Thumbnail

@Entity
data class DbCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: Thumbnail?,
    val isFavorite: Boolean
)