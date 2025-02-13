package com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits

import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val id: Int,
    val knownForDepartment: String,
    val originalName: String,
    val profilePath: String,
    val castId: Int,
    val character: String,
    val creditId: String,
)