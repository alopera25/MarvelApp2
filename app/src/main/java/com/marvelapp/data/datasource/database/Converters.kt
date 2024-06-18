package com.marvelapp.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.marvelapp.data.datasource.remote.ComicSummary
import com.marvelapp.data.datasource.remote.EventSummary
import com.marvelapp.data.datasource.remote.SeriesSummary
import com.marvelapp.data.datasource.remote.Thumbnail
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail): String {
        return json.encodeToString(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(data: String): Thumbnail {
        return json.decodeFromString(data)
    }

    @TypeConverter
    fun fromComicSummaryList(comicSummaryList: List<ComicSummary>): String {
        return json.encodeToString(ListSerializer(ComicSummary.serializer()), comicSummaryList)
    }

    @TypeConverter
    fun toComicSummaryList(data: String): List<ComicSummary> {
        return json.decodeFromString(ListSerializer(ComicSummary.serializer()), data)
    }
    @TypeConverter
    fun fromEventSummaryList(eventSummaryList: List<EventSummary>): String {
        return json.encodeToString(ListSerializer(EventSummary.serializer()), eventSummaryList)
    }

    @TypeConverter
    fun toEventSummaryList(data: String): List<EventSummary> {
        return json.decodeFromString(ListSerializer(EventSummary.serializer()), data)
    }

    @TypeConverter
    fun fromSeriesSummaryList(seriesSummaryList: List<SeriesSummary>): String {
        return json.encodeToString(ListSerializer(SeriesSummary.serializer()), seriesSummaryList)
    }

    @TypeConverter
    fun toSeriesSummaryList(data: String): List<SeriesSummary> {
        return json.decodeFromString(ListSerializer(SeriesSummary.serializer()), data)
    }
}

