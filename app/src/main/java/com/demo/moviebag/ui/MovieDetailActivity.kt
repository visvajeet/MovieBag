package com.demo.moviebag.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.moviebag.R
import com.demo.moviebag.adapters.ProductionCompaniesAdapter
import com.demo.moviebag.adapters.SimilarMoviesAdapter
import com.demo.moviebag.databinding.ActivityMovieDetailsBinding
import com.demo.moviebag.models.MediaQuery
import com.demo.moviebag.models.Movie
import com.demo.moviebag.models.MovieWithSimilarMovies
import com.demo.moviebag.utils.Constants
import com.demo.moviebag.utils.Constants.MEDIA_QUERY_DATA
import com.demo.moviebag.utils.Resource
import com.demo.moviebag.utils.showToast
import com.demo.moviebag.viewmodels.MovieDetailViewModel

class MovieDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityMovieDetailsBinding
    private val similarMoviesAdapter by lazy { SimilarMoviesAdapter(similarMovieClickListener) }
    private val productionCompaniesAdapter by lazy { ProductionCompaniesAdapter() }

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        setAdapters()
        observeMovieData()

        binding.ivBack.setOnClickListener { onBackPressed() }
        binding.srl.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.btCast.setOnClickListener {
            openCastActivity()
        }

        binding.btMedia.setOnClickListener {
            openMediaActivity()
        }

        binding.btReview.setOnClickListener {
            openReviewsActivity()
        }

        binding.nsv.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY < 200) {
                binding.ivBack.visibility = View.VISIBLE
            } else {
                binding.ivBack.visibility = View.GONE
            }
        }

    }

    private fun openCastActivity() {
        val i = Intent(this, CastActivity::class.java)
        i.putExtra(Constants.MOVIE_ID, viewModel.movieId)
        startActivity(i)
    }

    private fun openMediaActivity() {


        val i = Intent(this, MediaActivity::class.java)
        val mediaQuery = MediaQuery(type = viewModel.type?:"movie", id = viewModel.movieId)
        i.putExtra(MEDIA_QUERY_DATA, mediaQuery)
        startActivity(i)
    }

    private fun openReviewsActivity() {
        val i = Intent(this, ReviewsActivity::class.java)
        i.putExtra(Constants.MOVIE_ID, viewModel.movieId)
        startActivity(i)
    }

    private val similarMovieClickListener =
        SimilarMoviesAdapter.ClickListener { _: View, movie: Movie ->
            openMovieDetailActivity(movie.id.toString())
        }

    private fun openMovieDetailActivity(id: String) {
        finish()
        val i = Intent(this, MovieDetailActivity::class.java)
        i.putExtra(Constants.MOVIE_ID, id)
        startActivity(i)

    }

    private fun observeMovieData() {

        viewModel.movieData.observe(this) {

            when (it) {
                is Resource.Success -> {
                    it.data?.let { it1 -> setUI(it1) }
                }
                is Resource.Error -> {
                    val error = it.message
                    showToast(error.toString())
                }
                else -> {}
            }
        }

    }

    private fun setUI(it: MovieWithSimilarMovies) {

        binding.apply {
            movie = it.movie
            executePendingBindings()
        }
        similarMoviesAdapter.submitList(it.similarMovies)
        productionCompaniesAdapter.submitList(it.movie.productionCompanies)

    }

    private fun setAdapters() {

        binding.apply {
            rvSimilarMovies.adapter = similarMoviesAdapter
            rvProductionHouse.adapter = productionCompaniesAdapter
        }
    }
}