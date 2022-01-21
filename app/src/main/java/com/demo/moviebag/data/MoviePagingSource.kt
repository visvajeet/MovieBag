package com.demo.moviebag.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.demo.moviebag.models.Movie
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE = 1

class MoviePagingSource(
    private val movieApi: MoviesAPI = RetrofitInstance.moviesAPI,
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val pageIndex = params.key ?: STARTING_PAGE

        return try {

            val response = movieApi.getPopularMovies(page = pageIndex)

            LoadResult.Page(
                data = response.results,
                prevKey = if (pageIndex == STARTING_PAGE) null else pageIndex - 1,
                nextKey = if (pageIndex == response.totalResults) null else pageIndex + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(Exception("Please check internet connection"))
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }
}