package com.demo.moviebag.models

import androidx.annotation.Keep
import com.demo.moviebag.utils.Constants
import com.demo.moviebag.utils.getFormattedDate
import com.squareup.moshi.Json

@Keep
data class Review(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "content") val content: String?,
    @field:Json(name = "author") val author: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "updated_at") val updatedAt: String?,
    @field:Json(name = "author_details") val authorDetails: AuthorDetails?,

    ) {
    val image get() = Constants.THUMBNAIL_IMG.plus(authorDetails?.avatarPath)
    val formattedDate get() = updatedAt?.getFormattedDate()
    val rating
        get() = (authorDetails?.rating?.let { return@let if (it > 0) it / 2 else it }
            ?: 0).toFloat()
}

@Keep
data class AuthorDetails(
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "username") val username: String?,
    @field:Json(name = "avatar_path") val avatarPath: String?,
    @field:Json(name = "rating") val rating: Int?,

    )


