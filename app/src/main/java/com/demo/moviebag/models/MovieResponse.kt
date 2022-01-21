package com.demo.moviebag.models

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MovieListResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "total_results") val totalResults: Int?,
    @field:Json(name = "total_pages") val totalPages: Int?,
    @field:Json(name = "results") val results: List<Movie>,
)
