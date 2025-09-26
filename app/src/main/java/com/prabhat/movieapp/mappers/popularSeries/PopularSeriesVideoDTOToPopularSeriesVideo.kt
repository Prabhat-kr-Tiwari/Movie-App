package com.prabhat.movieapp.mappers.popularSeries

import com.prabhat.movieapp.data.model.movie.popular.videos.PopularSeriesVideoResponseDTO
import com.prabhat.movieapp.domain.model.popular.popularSeriesVideo.popularSeriesVideo

fun PopularSeriesVideoResponseDTO.toPopularSeriesVideo(): popularSeriesVideo {

    return popularSeriesVideo(name = results.map {
        it.name
    },
        key = results.map {
            it.key
        }
    )

}