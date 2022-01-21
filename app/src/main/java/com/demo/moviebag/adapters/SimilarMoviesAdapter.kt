package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.databinding.ListItemSimilarMoviesBinding
import com.demo.moviebag.models.Movie

class SimilarMoviesAdapter(private val clickListener: ClickListener) :
    ListAdapter<Movie, SimilarMoviesAdapter.SimilarMoviesViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesViewHolder {
        return SimilarMoviesViewHolder(ListItemSimilarMoviesBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: SimilarMoviesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, clickListener)
    }

    class SimilarMoviesViewHolder(private val binding: ListItemSimilarMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie?, clickListener: ClickListener) {
            binding.movie = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class ClickListener(val clickListener: (view: View, movie: Movie) -> Unit) {
        fun onClick(view: View, movie: Movie) = clickListener(view, movie)
    }

}
