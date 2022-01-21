package com.demo.moviebag.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.demo.moviebag.R
import com.demo.moviebag.adapters.BannerAdapter
import com.demo.moviebag.adapters.ListLoadStateAdapter
import com.demo.moviebag.adapters.MovieAdapter
import com.demo.moviebag.databinding.ActivityHomeBinding
import com.demo.moviebag.models.Movie
import com.demo.moviebag.utils.Constants.MOVIE_ID
import com.demo.moviebag.utils.showToast
import com.demo.moviebag.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private val movieAdapter by lazy { MovieAdapter(clickLister) }
    private val bannerAdapter by lazy { BannerAdapter(bannerClickListener) }

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        setAdapters()
        observeMovieList()
        observeBanner()

        binding.srl.setOnRefreshListener {
            movieAdapter.refresh()
        }

    }

    private fun observeBanner() {
        viewModel.banner.observe(this, {
            bannerAdapter.submitList(it)
        })
    }

    private fun observeMovieList() {

        viewModel.movies.observe(this, {
            lifecycleScope.launch {
                movieAdapter.submitData(it)
            }
        })
    }


    private fun setAdapters() {

        binding.apply {
            vpMovies.adapter = bannerAdapter
            indicator.setViewPager2(vpMovies)
        }

        binding.rvMovies.adapter = movieAdapter.withLoadStateFooter(
            footer = ListLoadStateAdapter(movieAdapter::retry)
        )
        movieAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    binding.srl.isRefreshing = true
                }
                is LoadState.NotLoading -> {
                    binding.srl.isRefreshing = false
                    viewModel.setBanner(movieAdapter.snapshot().items.take(4))
                    binding.indicator.setViewPager2(binding.vpMovies)
                }
                is LoadState.Error -> {
                    val error = (it.refresh as LoadState.Error)
                    binding.srl.isRefreshing = false
                    showToast(error.error.message.toString())
                }
                else -> {
                    binding.srl.isRefreshing = false
                }
            }
        }

    }

    private val clickLister = MovieAdapter.ClickListener { _: View, movie: Movie ->
        openMovieDetailActivity(movie.id.toString())
    }

    private val bannerClickListener = BannerAdapter.ClickListener { _: View, movie: Movie ->
        openMovieDetailActivity(movie.id.toString())
    }

    private fun openMovieDetailActivity(id: String) {
        val i = Intent(this, MovieDetailActivity::class.java)
        i.putExtra(MOVIE_ID, id)
        startActivity(i)
    }
}