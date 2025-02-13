package com.prabhat.movieapp.mappers

import com.prabhat.movieapp.data.model.movie.upcoming.UpComingMovieResponseDTO
import com.prabhat.movieapp.domain.model.upcomingMovie.UpComingMovieResponse
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL

//https://image.tmdb.org/t/p/w500/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg
fun UpComingMovieResponseDTO.toUpComingMovieResponse(): List<UpComingMovieResponse> {
    return results.map { result ->
        UpComingMovieResponse(
            id = result.id,
            genreIds = result.genre_ids,
            imageUrl = "${BASE_IMAGE_URL}${result.poster_path ?: ""}" ,
            originalTitle = result.original_title,
            overview = result.overview,
            releaseDate = result.release_date,
            adult = result.adult
            , page = page,
            totalPages = total_pages
        )

    }
}


//Using let for Safe Transformation
//To handle nullable types or apply a transformation only if a value is not null,
// you can use let:
/*fun UpComingMovieResponseDTO.toUpComingMovieResponse(): UpComingMovieResponse {
    val posterPath = results.firstOrNull()?.poster_path?.let { path ->
        "${BASE_IMAGE_URL}$path"
    } ?: ""

    return UpComingMovieResponse(
        imageUrl = posterPath
    )
}*/

/*Using run for Grouping Operations
If you need to group related logic while avoiding clutter, you can use run:

kotlin
Copy
Edit
fun UpComingMovieResponseDTO.toUpComingMovieResponse(): UpComingMovieResponse {
    return results.firstOrNull()?.run {
        UpComingMovieResponse(imageUrl = "${BASE_IMAGE_URL}${poster_path}")
    } ?: UpComingMovieResponse(imageUrl = "")
}
Using with for Context Object
If you are working with a specific object as the context, with can help streamline access to its properties:

kotlin
Copy
Edit
fun UpComingMovieResponseDTO.toUpComingMovieResponse(): UpComingMovieResponse {
    return with(results.firstOrNull()) {
        UpComingMovieResponse(imageUrl = "${BASE_IMAGE_URL}${this?.poster_path ?: ""}")
    }
}
Using also for Side Effects
If you need to perform additional operations during mapping, also is useful:

kotlin
Copy
Edit
fun UpComingMovieResponseDTO.toUpComingMovieResponse(): UpComingMovieResponse {
    val posterPath = results.firstOrNull()?.poster_path?.also {
        println("Mapping poster_path: $it") // Example side effect
    } ?: ""

    return UpComingMovieResponse(
        imageUrl = "${BASE_IMAGE_URL}${posterPath}"
    )
}
Using if with Expression Syntax
If a simple conditional transformation is needed, Kotlin allows if to return values directly:

kotlin
Copy
Edit
fun UpComingMovieResponseDTO.toUpComingMovieResponse(): UpComingMovieResponse {
    val posterPath = if (results.isNotEmpty()) {
        "${BASE_IMAGE_URL}${results[0].poster_path}"
    } else {
        ""
    }

    return UpComingMovieResponse(imageUrl = posterPath)
}*/

