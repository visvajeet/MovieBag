package com.demo.moviebag.viewmodels

import androidx.lifecycle.*
import com.demo.moviebag.data.RetrofitInstance
import com.demo.moviebag.models.CastResponse
import com.demo.moviebag.utils.Constants.MOVIE_ID
import com.demo.moviebag.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException

class CastViewModel(private val state: SavedStateHandle) : ViewModel() {

    private var getDataJob: Job? = null

    private val _cast = MutableLiveData<Resource<CastResponse>>()
    val cast = _cast

    val isLoading = Transformations.map(cast) {
        it is Resource.Loading
    }

    init {
        getData()
    }

    private fun getData() {

        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {

            val movieId = state.get<String>(MOVIE_ID).toString()

            _cast.postValue(Resource.Loading())

            try {

                val res = RetrofitInstance.moviesAPI.getMovieCast(movieId = movieId)
                _cast.postValue(Resource.Success(res))

            } catch (e: Exception) {
                val msg = if (e is IOException) {
                    "Please check internet connection"
                } else e.message.toString()

                _cast.postValue(Resource.Error(message = msg))
            }

        }
    }

    fun refresh() {
        getData()
    }

}