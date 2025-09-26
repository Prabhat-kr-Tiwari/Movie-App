package com.prabhat.movieapp.mappers.categories.movieByCategories

import com.prabhat.movieapp.data.model.categories.movieByCategories.MovieByCategoriesResponseDto
import com.prabhat.movieapp.domain.model.categories.MovieByCategories
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL

fun MovieByCategoriesResponseDto.toDomainMovieByCategories():List<MovieByCategories>{

    return results.map { result->
        MovieByCategories(
            id = result.id,
            genreIds = result.genre_ids,
            imageUrl = getFullImageUrl(result.poster_path),
            originalTitle = result.original_title,
            overview = result.overview,
            releaseDate = result.release_date,
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