package com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits

import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val id: Int,
    val knownForDepartment: String,
    val originalName: String,
    val profilePath: String,
    val creditId: String,
    val department: String,
    val job: String
)
