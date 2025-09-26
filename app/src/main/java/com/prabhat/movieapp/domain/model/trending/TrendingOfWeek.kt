package com.prabhat.movieapp.domain.model.trending

data class TrendingOfWeek(
    val id:Int?=null,
    val originalName:String?=null,
    val overview:String?=null,
    val posterPath:String?=null,
    val mediaType:String?=null,
    val genereId:List<Int>?=null,
    val firstAirDate:String?=null
)
