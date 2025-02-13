package com.prabhat.movieapp.domain.model.upcomingMovie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UpComingMovieResponse(
    val id: Int,
    val genreIds: List<Int>,
    val imageUrl: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val page:Int,
    val totalPages:Int
):Parcelable
