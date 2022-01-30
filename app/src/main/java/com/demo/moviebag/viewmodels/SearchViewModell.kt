package com.demo.moviebag.viewmodels

import androidx.lifecycle.*
import com.demo.moviebag.data.RetrofitInstance
import com.demo.moviebag.models.SearchResponse
import com.demo.moviebag.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException


class SearchViewModel(private val state: SavedStateHandle) : ViewModel() {

    private var getDataJob: Job? = null

    private val _search = MutableLiveData<Resource<SearchResponse>>()
    val search = _search

    val isLoading = Transformations.map(search) {
        it is Resource.Loading
    }


    fun searchQuery(query: String) {

        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {

             delay(400)
            _search.postValue(Resource.Loading())

            try {

                val res = RetrofitInstance.moviesAPI.search(query = query)
                _search.postValue(Resource.Success(res))

            } catch (e: Exception) {
                val msg = if (e is IOException) {
                    "Please check internet connection"
                } else e.message.toString()

                _search.postValue(Resource.Error(message = msg))
            }

        }
    }


}