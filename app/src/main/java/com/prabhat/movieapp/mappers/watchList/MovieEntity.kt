package com.prabhat.movieapp.mappers.watchList

import com.prabhat.movieapp.data.local.watchList.MovieEntity
import com.prabhat.movieapp.domain.model.watchList.Movie

fun MovieEntity.toDomain(): Movie =
    Movie(id, movieId,title, description, imageUrl)

fun Movie.toEntity(): MovieEntity =
    MovieEntity(id, movieId,title, description, imageUrl)
