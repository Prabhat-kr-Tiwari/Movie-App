package com.prabhat.movieapp.presentation.screen.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MovieTag(
    val id: Int,
) : Parcelable