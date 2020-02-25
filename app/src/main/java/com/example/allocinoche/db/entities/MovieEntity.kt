package com.example.allocinoche.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieDB")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uID") val uId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "poster") val poster: String?,
    @ColumnInfo(name = "original_title") val originalTitle: String?,
    @ColumnInfo(name = "video") val video: Boolean,
    @ColumnInfo(name = "vote") val vote: Double,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "originalLanguage") val originalLanguage: String?,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "Poster_path") val posterPath: String?,
    @ColumnInfo(name = "genres") val genre: List<Int>,
    @ColumnInfo(name = "backdrop_path") val backDropPath: String?,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "liked") val liked: Boolean?)
