package com.demo.moviebag.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.moviebag.R
import com.demo.moviebag.adapters.CastAdapter
import com.demo.moviebag.databinding.ActivityCastBinding
import com.demo.moviebag.utils.Resource
import com.demo.moviebag.utils.showToast
import com.demo.moviebag.viewmodels.CastViewModel

class CastActivity : AppCompatActivity() {

    lateinit var binding: ActivityCastBinding
    private val castAdapter by lazy { CastAdapter() }

    private val castViewModel: CastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cast)

        binding.apply {
            viewModel = castViewModel
            lifecycleOwner = this@CastActivity
            executePendingBindings()
            rvCast.adapter = castAdapter

            appbar.apply {
                tvTitle.text = getString(R.string.cast)
                ivBack.setOnClickListener { onBackPressed() }
            }
        }

        observeMovieData()

        binding.srl.setOnRefreshListener {
            castViewModel.refresh()
        }
    }

    private fun observeMovieData() {

        castViewModel.cast.observe(this, {

            when (it) {
                is Resource.Success -> {
                    castAdapter.submitList(it.data?.castList)
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