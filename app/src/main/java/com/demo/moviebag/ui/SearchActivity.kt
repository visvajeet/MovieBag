package com.demo.moviebag.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.demo.moviebag.R
import com.demo.moviebag.adapters.SearchAdapter
import com.demo.moviebag.databinding.ActivitySearchBinding
import com.demo.moviebag.models.Search
import com.demo.moviebag.utils.Constants
import com.demo.moviebag.utils.Resource
import com.demo.moviebag.viewmodels.SearchViewModel

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    private val searchAdapter by lazy { SearchAdapter(clickListener) }

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = this@SearchActivity
            executePendingBindings()
            rvSearch.adapter = searchAdapter

            binding.apply {
                ivBack.setOnClickListener { onBackPressed() }
            }
        }

        binding.etSearch.addTextChangedListener {
            searchViewModel.searchQuery(it.toString())
        }

        observeSearchData()

    }

    private val clickListener = SearchAdapter.ClickListener { view: View, search: Search ->
        val i = Intent(this, MovieDetailActivity::class.java)
        i.putExtra(Constants.MOVIE_ID, search.id)
        i.putExtra(Constants.TYPE, search.mediaType)
        startActivity(i)
    }

    private fun observeSearchData() {

        searchViewModel.search.observe(this) {

            when (it) {
                is Resource.Success -> {
                    searchAdapter.submitList(it.data?.results)
                }
                is Resource.Error -> {
                    val error = it.message
                    // showToast(error.toString())
                }
                else -> {}
            }
        }
    }
}