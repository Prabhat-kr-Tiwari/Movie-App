package com.prabhat.movieapp.data.model.movie.popular.credits

data class CreditsResponseDto(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)