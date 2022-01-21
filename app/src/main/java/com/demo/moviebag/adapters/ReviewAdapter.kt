package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.databinding.ListItemReviewBinding
import com.demo.moviebag.models.Review

class ReviewAdapter() :
    ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(ReviewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(ListItemReviewBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class ReviewViewHolder(private val binding: ListItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Review?) {
            binding.review = item
            binding.executePendingBindings()
        }
    }

}

class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }
}
