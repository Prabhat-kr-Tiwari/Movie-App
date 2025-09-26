package com.prabhat.movieapp.mappers.trending

import android.util.Log
import com.prabhat.movieapp.data.model.movie.trending.TrendingOfWeekResponseDto
import com.prabhat.movieapp.domain.model.trending.TrendingOfWeek
import com.prabhat.movieapp.mappers.Mapper
import com.prabhat.movieapp.utils.Constants.BASE_IMAGE_URL
import javax.inject.Inject

class TrendingOfWeekResponseDtoToTrendingOfWeekMapper
@Inject constructor():Mapper<TrendingOfWeekResponseDto,TrendingOfWeek> {
    override fun map(from: TrendingOfWeekResponseDto): TrendingOfWeek {
        val firstResult =
            from.results.firstOrNull() ?: throw IllegalArgumentException("No trending data available")
        Log.d("ROMMY", "map: "+firstResult)

        return TrendingOfWeek(
            id = firstResult.id,
            originalName = firstResult.original_name,
            overview = firstResult.overview,
            posterPath = getFullImageUrl(firstResult.poster_path),
            mediaType = firstResult.media_type,
            genereId = firstResult.genre_ids,
            firstAirDate = firstResult.first_air_date
        )
    }

}
private fun getFullImageUrl(posterPath: String?): String {
    return if (!posterPath.isNullOrEmpty()) "$BASE_IMAGE_URL$posterPath" else "https://coffective.com/wp-content/uploads/2018/06/default-featured-image.png.jpg"
}