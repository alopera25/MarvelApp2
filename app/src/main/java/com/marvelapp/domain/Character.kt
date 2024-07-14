package com.marvelapp.domain

import com.marvelapp.data.datasource.remote.Thumbnail

data class Character(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: Thumbnail?,
    val isFavorite: Boolean
)


