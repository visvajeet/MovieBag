package com.demo.moviebag.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.demo.moviebag.R
import com.demo.moviebag.adapters.MediaAdapter
import com.demo.moviebag.databinding.ActivityMediaBinding
import com.demo.moviebag.utils.Resource
import com.demo.moviebag.utils.showToast
import com.demo.moviebag.viewmodels.MediaViewModel

class MediaActivity : AppCompatActivity() {

    lateinit var binding: ActivityMediaBinding
    private val mediaAdapter by lazy { MediaAdapter() }

    private val mediaViewModel: MediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_media)

        binding.apply {
            viewModel = mediaViewModel
            lifecycleOwner = this@MediaActivity
            executePendingBindings()
            rvCast.adapter = mediaAdapter

            appbar.apply {
                tvTitle.text = getString(R.string.media)
                ivBack.setOnClickListener { onBackPressed() }
            }
        }

        observeMediaData()

        binding.srl.setOnRefreshListener {
            mediaViewModel.refresh()
        }
    }

    private fun observeMediaData() {

        mediaViewModel.media.observe(this) {

            when (it) {
                is Resource.Success -> {

                    val media = it.data?.backdrops ?: emptyList()
                    val posters = it.data?.posters ?: emptyList()

                    mediaAdapter.submitList(media.plus(posters))

                }
                is Resource.Error -> {
                    val error = it.message
                    showToast(error.toString())
                }
                else -> {}
            }
        }

    }
}