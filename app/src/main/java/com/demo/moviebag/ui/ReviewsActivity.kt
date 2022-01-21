package com.demo.moviebag.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.moviebag.R
import com.demo.moviebag.adapters.ReviewAdapter
import com.demo.moviebag.databinding.ActivityReviewsBinding
import com.demo.moviebag.utils.Resource
import com.demo.moviebag.utils.showToast
import com.demo.moviebag.viewmodels.ReviewsViewModel

class ReviewsActivity : AppCompatActivity() {

    lateinit var binding: ActivityReviewsBinding
    private val reviewAdapter by lazy { ReviewAdapter() }

    private val reviewViewModel: ReviewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_reviews)

        binding.apply {
            viewModel = reviewViewModel
            lifecycleOwner = this@ReviewsActivity
            executePendingBindings()
            rvReviews.adapter = reviewAdapter

            appbar.apply {
                tvTitle.text = getString(R.string.review)
                ivBack.setOnClickListener { onBackPressed() }
            }
        }

        observeMovieData()
        observerFilter()

        binding.srl.setOnRefreshListener {
            reviewViewModel.refresh()
        }

        binding.chipGroupRating.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipRatingAll -> reviewViewModel.selectFilter(0)
                R.id.chipRating5 -> reviewViewModel.selectFilter(5)
                R.id.chipRating4 -> reviewViewModel.selectFilter(4)
                R.id.chipRating3 -> reviewViewModel.selectFilter(3)
                R.id.chipRating2 -> reviewViewModel.selectFilter(2)
                R.id.chipRating1 -> reviewViewModel.selectFilter(1)
            }
        }
    }

    private fun observerFilter() {
        reviewViewModel.filter.observe(this, {
            if (it == 0) {
                binding.chipRatingAll.isChecked = true
            }
            reviewAdapter.submitList(reviewViewModel.getFilteredList(it))
        })
    }

    private fun observeMovieData() {

        reviewViewModel.reviews.observe(this, {

            when (it) {
                is Resource.Success -> {
                    reviewAdapter.submitList(it.data?.reviews)
                }
                is Resource.Error -> {
                    val error = it.message
                    showToast(error.toString())
                }
                else -> {}
            }
        })

    }
}