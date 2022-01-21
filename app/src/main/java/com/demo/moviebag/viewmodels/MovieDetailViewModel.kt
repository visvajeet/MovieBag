package com.demo.moviebag.viewmodels

import androidx.lifecycle.*
import com.demo.moviebag.data.RetrofitInstance
import com.demo.moviebag.models.MovieWithSimilarMovies
import com.demo.moviebag.utils.Constants.MOVIE_ID
import com.demo.moviebag.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

class MovieDetailViewModel(private val state: SavedStateHandle) : ViewModel() {

    private var getDataJob: Job? = null

    val movieId = state.get<String>(MOVIE_ID).toString()

    private val _movieData = MutableLiveData<Resource<MovieWithSimilarMovies>>()
    val movieData = _movieData

    val isLoading = Transformations.map(movieData) {
        it is Resource.Loading
    }

    val isError = Transformations.map(movieData) {
        it is Resource.Error
    }

    init {
        getData()
    }

    private fun getData() {

        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {

            val movieId = state.get<String>(MOVIE_ID).toString()

            _movieData.postValue(Resource.Loading())

            try {

                coroutineScope {

                    val movieDetail =
                        async { RetrofitInstance.moviesAPI.getMovieDetail(movieId = movieId) }
                    val similarMovies =
                        async { RetrofitInstance.moviesAPI.getSimilarMovies(movieId = movieId) }

                    _movieData.postValue(Resource.Success(MovieWithSimilarMovies(
                        movie = movieDetail.await(),
                        similarMovies = similarMovies.await().results
                    )))

                }

            } catch (e: Exception) {
                val msg = if (e is IOException) {
                    "Please check internet connection"
                } else e.message.toString()
                _movieData.postValue(Resource.Error(message = msg))
            }


        }
    }

    fun refresh() {
        getData()
    }

}