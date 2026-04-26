package com.prabhat.movieapp.presentation.screen.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
enum class MovieCategory : Parcelable {
    SERIES,
    UPCOMING,
    TRENDING_WEEK
}