package com.demo.moviebag.viewmodels

import androidx.lifecycle.*
import com.demo.moviebag.data.RetrofitInstance
import com.demo.moviebag.models.Media
import com.demo.moviebag.models.MediaQuery
import com.demo.moviebag.utils.Constants.MEDIA_QUERY_DATA
import com.demo.moviebag.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException


class MediaViewModel(private val state: SavedStateHandle) : ViewModel() {

    private var getDataJob: Job? = null

    private val _media = MutableLiveData<Resource<Media>>()
    val media = _media

    val isLoading = Transformations.map(media) {
        it is Resource.Loading
    }

    init {
        getData()
    }

    private fun getData() {

        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {

            val mediaQuery = state.get<MediaQuery>(MEDIA_QUERY_DATA)

            _media.postValue(Resource.Loading())


            try {
                val res = RetrofitInstance.moviesAPI
                    .getMedia(type = mediaQuery?.type.toString(), mediaQuery?.id.toString())
                _media.postValue(Resource.Success(res))

            } catch (e: Exception) {
                val msg = if (e is IOException) {
                    "Please check internet connection"
                } else e.message.toString()

                _media.postValue(Resource.Error(message = msg))
            }

        }
    }

    fun refresh() {
        getData()
    }

}