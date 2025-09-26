package com.prabhat.movieapp.data.local.watchList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val movieId:Int,
    val title: String,
    val description: String,
    val imageUrl: String
)


