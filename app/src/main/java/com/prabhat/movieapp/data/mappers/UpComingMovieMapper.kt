package com.prabhat.movieapp.data.mappers

import com.prabhat.movieapp.data.local.upcomingMovie.UpComingMovieEntity
import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.domain.model.upcomingMovie.UpComingMovieResponse
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL


fun UpComingMovieResponseDTO.toUpComingMovieEntity(): UpComingMovieEntity {

    val firstResult = results[0]

    return UpComingMovieEntity(
        id = firstResult.id,
        genreIds = firstResult.genre_ids,
        imageUrl = getFullImageUrl(firstResult.poster_path),
        originalTitle = firstResult.original_title,
        overview = firstResult.original_title,
        releaseDate = firstResult.release_date,
        adult = firstResult.adult,
        page=page,
        totalPages = total_pages
    )

}


private fun getFullImageUrl(posterPath: String?): String {
    return if (!posterPath.isNullOrEmpty()) "$BASE_IMAGE_URL$posterPath" else "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg"
}

fun UpComingMovieEntity.toUpComingMovieResponse():UpComingMovieResponse{
    return UpComingMovieResponse(
        id = id,
        genreIds = genreIds,
        imageUrl = imageUrl,
        originalTitle = originalTitle,
        overview = overview,
        releaseDate = releaseDate,
        adult = adult,
        page=page,
        totalPages = totalPages
    )
}
