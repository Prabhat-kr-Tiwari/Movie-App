package com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
data class MovieCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)
