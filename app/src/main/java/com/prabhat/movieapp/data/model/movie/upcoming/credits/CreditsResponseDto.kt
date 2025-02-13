package com.prabhat.movieapp.data.model.movie.upcoming.credits

data class CreditsResponseDto(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)