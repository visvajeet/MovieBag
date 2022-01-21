package com.demo.moviebag.models

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ReviewsResponse(
    @field:Json(name = "results") val reviews: List<Review> = emptyList(),
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "page") val page: Int?,
)
