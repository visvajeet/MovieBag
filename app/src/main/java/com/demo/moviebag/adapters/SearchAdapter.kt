package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.databinding.ListItemSearchBinding
import com.demo.moviebag.models.Search

class SearchAdapter(private val clickListener: ClickListener) :
    ListAdapter<Search, SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ListItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, clickListener)
    }

    class SearchViewHolder(private val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Search?, clickListener: ClickListener) {

            binding.search = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class ClickListener(val clickListener: (view: View, search: Search) -> Unit) {
        fun onClick(view: View, search: Search) = clickListener(view, search)
    }

}

class SearchDiffCallback : DiffUtil.ItemCallback<Search>() {
    override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
        return oldItem.id == newItem.id
    }
}