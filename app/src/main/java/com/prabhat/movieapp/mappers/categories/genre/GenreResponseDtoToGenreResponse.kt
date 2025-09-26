package com.prabhat.movieapp.mappers.categories.genre

import com.prabhat.movieapp.data.model.categories.GenreResponseDto
import com.prabhat.movieapp.domain.model.genre.Genre


/*fun GenreResponseDto.toGenre():Genre{
    val first = genres[0]
    return Genre(genreId = first.id, genreName = first.name)

}*/
fun GenreResponseDto.toGenre():List<Genre>{
   return genres.map { genre->
        Genre(genreId = genre.id, genreName = genre.name)

    }

}
