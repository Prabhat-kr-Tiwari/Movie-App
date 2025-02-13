package com.prabhat.movieapp.mappers

import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieVideoResponseDTO.UpComingMovieVideoResponseDTO
import com.prabhat.movieapp.domain.model.upcomingMovie.upcomingMovieVideo.UpcomingMovieVideo


fun UpComingMovieVideoResponseDTO.toUpcomingMovieVide(): UpcomingMovieVideo {

    return UpcomingMovieVideo(name = results.map {
        it.name
    },
        key = results.map {
            it.key
        }
    )

}
