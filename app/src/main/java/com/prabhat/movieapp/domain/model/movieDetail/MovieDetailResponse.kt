package com.prabhat.movieapp.domain.model.movieDetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class MovieDetailResponse(
    val id: Int,
    val genreIds: List<Int>,
    val imageUrl: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
) : Parcelable