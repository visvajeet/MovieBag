package com.demo.moviebag.models

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CastResponse(
    @field:Json(name = "cast") val castList: List<Cast> = emptyList(),
    @field:Json(name = "id") val id: Int?,

    )
