package com.prabhat.movieapp.data.local.upcomingMovie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface UpComingMovieDao {
    @Upsert
//    suspend fun upsertAll(beersLList: List<UpComingMovieEntity>)
    suspend fun upsertAll(upcomingMovieList: List<UpComingMovieEntity>)

    @Query("SELECT * FROM upcomingmovieentity")
    fun pagingSource():PagingSource<Int,UpComingMovieEntity>


    @Query("DELETE FROM upcomingmovieentity")
    suspend fun clearAll()




}