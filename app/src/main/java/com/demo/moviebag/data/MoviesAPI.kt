package com.demo.moviebag.data

import com.demo.moviebag.models.*
import com.demo.moviebag.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String = API_KEY,
        @Query("page") page: Int,

        ): MovieListResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") key: String = API_KEY,

        ): Movie

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: String,
        @Query("api_key") key: String = API_KEY,
        @Query("page") page: Int = 1,

        ): MovieListResponse

    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: String,
        @Query("api_key") key: String = API_KEY,

        ): CastResponse

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: String,
        @Query("api_key") key: String = API_KEY,
        @Query("page") page: Int = 1,

        ): ReviewsResponse

    @GET("3/search/multi")
    suspend fun search(
        @Query("query") query: String,
        @Query("api_key") key: String = API_KEY,
        @Query("page") page: Int = 1,

        ): SearchResponse

    @GET("3/{type}/{id}/images")
    suspend fun getMedia(
        @Path("type") type: String,
        @Path("id") id: String,
        @Query("api_key") key: String = API_KEY,

        ): Media


}