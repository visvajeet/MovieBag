package com.demo.moviebag.models

import androidx.annotation.Keep
import com.demo.moviebag.utils.Constants
import com.squareup.moshi.Json

@Keep
data class Cast(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "known_for_department") val knownForDepartment: String?,
    @field:Json(name = "profile_path") val profilePath: String?,
    @field:Json(name = "name") val name: String?,
) {
    val image get() = Constants.THUMBNAIL_IMG.plus(profilePath)
}
