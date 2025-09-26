package com.prabhat.movieapp.mappers.popularSeries

import com.prabhat.movieapp.data.model.movie.popular.PopularSeriesDTO
import com.prabhat.movieapp.domain.model.popular.PopularSeries
import com.prabhat.movieapp.mappers.Mapper
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL
import javax.inject.Inject

class PopularSeriesDTOToPopularSeriesMapper @Inject constructor() :
    Mapper<PopularSeriesDTO, PopularSeries> {
    override fun map(from: PopularSeriesDTO): PopularSeries {
        val firstResult =
            from.results.firstOrNull() ?: throw IllegalArgumentException("No series data available")

        return PopularSeries(
            id = firstResult.id,
            genreIds = firstResult.genre_ids,
            imageUrl = getFullImageUrl(firstResult.poster_path),
            originalTitle = firstResult.original_name,
            overview = firstResult.overview,
            releaseDate = firstResult.first_air_date,
            adult = firstResult.adult,
            page =from.page,
            totalPages = from.total_pages
        )
    }
}

private fun getFullImageUrl(posterPath: String?): String {
    return if (!posterPath.isNullOrEmpty()) "$BASE_IMAGE_URL$posterPath" else "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg"
}