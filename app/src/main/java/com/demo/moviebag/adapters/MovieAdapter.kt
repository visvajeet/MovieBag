package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.databinding.ListItemMovieBinding
import com.demo.moviebag.models.Movie

class MovieAdapter(private val clickListener: ClickListener) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, clickListener)
    }

    class MovieViewHolder(private val binding: ListItemMovieBinding) :
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

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}