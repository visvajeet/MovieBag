package com.demo.moviebag.models

import androidx.annotation.Keep

@Keep
data class MovieWithSimilarMovies(
    val movie: Movie,
    val similarMovies: List<Movie> = emptyList(),
)
