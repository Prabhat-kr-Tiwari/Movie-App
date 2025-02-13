package com.prabhat.movieapp.mappers

import com.prabhat.movieapp.data.model.movie.upcoming.credits.CreditsResponseDto
import com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.Cast
import com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.Crew
import com.prabhat.movieapp.domain.model.upcomingMovie.movieCredits.MovieCredits
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL

fun CreditsResponseDto.toMovieCredits(): MovieCredits {
    return MovieCredits(
        id = id,
        crew = crew.map { crewMember ->
            Crew(
                id = crewMember.id,
                knownForDepartment = crewMember.known_for_department ?: "Unknown",
                originalName = crewMember.original_name ?: "Unknown",
                profilePath = getFullImageUrl(crewMember.profile_path),
                creditId = crewMember.credit_id,
                department = crewMember.department ?: "Unknown",
                job = crewMember.job ?: "Unknown"
            )
        },
        cast = cast.map { castMember ->
            Cast(
                id = castMember.id,
                knownForDepartment = castMember.known_for_department ?: "Unknown",
                originalName = castMember.original_name ?: "Unknown",
                profilePath = getFullImageUrl(castMember.profile_path),
                castId = castMember.cast_id ?: 0,
                character = castMember.character ?: "Unknown",
                creditId = castMember.credit_id
            )
        }
    )
}

private fun getFullImageUrl(posterPath: String?): String {
    return if (!posterPath.isNullOrEmpty()) "$BASE_IMAGE_URL$posterPath" else "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg"
}