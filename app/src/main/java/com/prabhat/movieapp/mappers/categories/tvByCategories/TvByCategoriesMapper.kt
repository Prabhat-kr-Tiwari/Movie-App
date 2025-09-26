package com.prabhat.movieapp.mappers.categories.tvByCategories

import com.prabhat.movieapp.data.model.categories.movieByCategories.MovieByCategoriesResponseDto
import com.prabhat.movieapp.data.model.categories.tvByCategories.TvByCategoriesResponseDto
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.domain.model.tvByCategories.TvByCategories
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL

fun TvByCategoriesResponseDto.toDomainTvByCategories():List<TvByCategories>{

    return results.map { result->
        TvByCategories(
            id = result.id,
            genreIds = result.genre_ids,
            imageUrl = getFullImageUrl(result.poster_path),
            originalName = result.original_name,
            overview = result.overview,
            firstAirDate = result.first_air_date,
            adult = result.adult,
            page = page,
            totalPages = total_pages,
            totalResult = total_results
        )

    }

}
private fun getFullImageUrl(posterPath: String?): String {
    return if (!posterPath.isNullOrEmpty()) "$BASE_IMAGE_URL$posterPath" else "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg"
}