package com.demo.moviebag.models

import androidx.annotation.Keep
import com.demo.moviebag.utils.Constants.THUMBNAIL_IMG
import com.demo.moviebag.utils.Constants.WIDE_IMG
import com.squareup.moshi.Json

@Keep
data class Search(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "original_language") val language: String?,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "poster_path") val posterPath: String? = "",
    @field:Json(name = "media_type") val mediaType: String?,
    @field:Json(name = "name") val name: String?,
) {

    val thumbnailPoster get() = THUMBNAIL_IMG.plus(posterPath)
    val wideImage get() = WIDE_IMG.plus(backdropPath)
    val formattedLanguage
        get() = when (language) {
            "en" -> "English"
            else -> language
        }
}
