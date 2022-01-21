package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.R
import com.demo.moviebag.databinding.ListItemLoadingAndRetryBinding


class ListLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState,
    ) = holder.bind(loadState)
}

class LoadStateViewHolder(parent: ViewGroup, retry: () -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.list_item_loading_and_retry, parent, false)
) {
    private val binding = ListItemLoadingAndRetryBinding.bind(itemView).also {
        it.btRetry.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) {
        binding.apply {
            if (loadState is LoadState.Error) {
                tvError.text = loadState.error.localizedMessage
            }
            progress.isVisible = loadState is LoadState.Loading
            btRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = loadState is LoadState.Error
        }

    }
}