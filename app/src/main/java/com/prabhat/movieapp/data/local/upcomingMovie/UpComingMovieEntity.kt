package com.prabhat.movieapp.data.local.upcomingMovie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UpComingMovieEntity(
    @PrimaryKey
    val id: Int,
    val genreIds: List<Int>,
    val imageUrl: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val page:Int,
    val totalPages:Int
)
