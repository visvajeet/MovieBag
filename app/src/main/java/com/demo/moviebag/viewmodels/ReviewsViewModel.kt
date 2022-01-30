package com.demo.moviebag.viewmodels

import androidx.lifecycle.*
import com.demo.moviebag.data.RetrofitInstance
import com.demo.moviebag.models.Review
import com.demo.moviebag.models.ReviewsResponse
import com.demo.moviebag.utils.Constants.MOVIE_ID
import com.demo.moviebag.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class ReviewsViewModel(private val state: SavedStateHandle) : ViewModel() {


    private var getDataJob: Job? = null

    private val _reviews = MutableLiveData<Resource<ReviewsResponse>>()
    val reviews = _reviews

    val isLoading = Transformations.map(reviews) {
        it is Resource.Loading
    }

    val isError = Transformations.map(reviews) {
        it is Resource.Error
    }

    private val _selectedFilter = MutableLiveData(0)
    val filter = _selectedFilter

    fun getFilteredList(num: Int): List<Review> {

        return if (num > 0) {
            _reviews.value?.data?.reviews?.filter { it.rating == num.toFloat() } ?: emptyList()
        } else {
            _reviews.value?.data?.reviews ?: emptyList()
        }
    }

    fun selectFilter(filter: Int) {
        _selectedFilter.value = filter
    }

    init {
        getData()
    }

    private fun getData() {

        getDataJob?.cancel()

        selectFilter(0)

        getDataJob = viewModelScope.launch {

            val movieId = state.get<String>(MOVIE_ID).toString()

            _reviews.postValue(Resource.Loading())

            try {

                val res = RetrofitInstance.moviesAPI.getReviews(movieId = movieId)
                _reviews.postValue(Resource.Success(res))

            } catch (e: Exception) {
                val msg = if (e is IOException) {
                    "Please check internet connection"
                } else e.message.toString()

                _reviews.postValue(Resource.Error(message = msg))
            }

        }

    }

    fun refresh() {
        getData()
    }

}