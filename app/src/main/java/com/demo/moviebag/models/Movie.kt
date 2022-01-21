package com.demo.moviebag.models

import androidx.annotation.Keep
import com.demo.moviebag.utils.Constants.THUMBNAIL_IMG
import com.demo.moviebag.utils.Constants.WIDE_IMG
import com.squareup.moshi.Json

@Keep
data class Movie(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "original_language") val language: String?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "popularity") val popularity: Float?,
    @field:Json(name = "poster_path") val posterPath: String? = "",
    @field:Json(name = "production_companies") val productionCompanies: List<ProductionCompanies>,
    @field:Json(name = "release_date") val releaseDate: String?,
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "vote_average") val voteAverage: Float? = 0f,
) {

    val thumbnailPoster get() = THUMBNAIL_IMG.plus(posterPath)
    val wideImage get() = WIDE_IMG.plus(backdropPath)
    val rating get() = (voteAverage?.let { return@let if (it > 0) it / 2 else it } ?: 0f)
    val formattedLanguage
        get() = when (language) {
            "en" -> "English"
            else -> language
        }
}

@Keep
data class ProductionCompanies(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "logo_path") val logoPath: String?,
) {
    val image get() = THUMBNAIL_IMG.plus(logoPath)
}
