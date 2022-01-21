package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.databinding.ListItemCastBinding
import com.demo.moviebag.models.Cast

class CastAdapter() :
    ListAdapter<Cast, CastAdapter.CastViewHolder>(CastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(ListItemCastBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class CastViewHolder(private val binding: ListItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Cast?) {
            binding.cast = item
            binding.executePendingBindings()
        }
    }

}

class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }
}
