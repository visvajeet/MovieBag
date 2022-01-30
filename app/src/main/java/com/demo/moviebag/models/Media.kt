package com.demo.moviebag.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.demo.moviebag.utils.Constants
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
data class Media(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "backdrops") val backdrops: List<MediaImages>?,
    @field:Json(name = "posters") val posters: List<MediaImages>?,
)

@Keep
data class MediaImages(
    @field:Json(name = "aspect_ratio") val aspectRatio: Double?,
    @field:Json(name = "file_path") val filePath: String? = "",
) {
    val imageUrlOG get() = Constants.OG.plus(filePath)
    val imageUrlW500 get() = Constants.W500_IMG.plus(filePath)
    val imageUrlW342 get() = Constants.W342_IMG.plus(filePath)
}


@Keep
@Parcelize
data class MediaQuery(
    val type: String,
    val id: String,
) : Parcelable

