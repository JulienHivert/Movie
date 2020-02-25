package com.example.allocinoche.db

import androidx.room.TypeConverter
import com.example.allocinoche.data.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()
    private val genreType = object : TypeToken<List<Genre>>() {}.type

    @TypeConverter
    fun genreToString(genreList: List<Int>): String {
        return gson.toJson(genreList, genreType)
    }

    @TypeConverter
    fun StringToGenre(json: String?): List<Int>{
        return gson.fromJson(json, genreType)
    }
}