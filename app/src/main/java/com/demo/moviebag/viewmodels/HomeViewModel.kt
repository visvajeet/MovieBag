package com.demo.moviebag.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.demo.moviebag.data.MoviePagingSource
import com.demo.moviebag.models.Movie

class HomeViewModel : ViewModel() {

    private val _banner = MutableLiveData<List<Movie>>()
    val banner = _banner

    fun setBanner(list: List<Movie>) {
        _banner.postValue(list)
    }

    val movies = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { MoviePagingSource() }
    ).liveData.cachedIn(viewModelScope)


}