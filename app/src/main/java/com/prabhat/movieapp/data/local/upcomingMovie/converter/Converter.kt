package com.prabhat.movieapp.data.local.upcomingMovie.converter


import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromListToString(genreIds: List<Int>?): String {
        return Gson().toJson(genreIds)
    }

    @TypeConverter
    fun fromStringToList(genreIdsString: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(genreIdsString, type)
    }
}
