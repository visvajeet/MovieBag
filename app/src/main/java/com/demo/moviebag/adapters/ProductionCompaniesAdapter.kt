package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.databinding.ListItemProductionImageBinding
import com.demo.moviebag.models.ProductionCompanies

class ProductionCompaniesAdapter() :
    ListAdapter<ProductionCompanies, ProductionCompaniesAdapter.SimilarMoviesViewHolder>(
        ProductionCompaniesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesViewHolder {
        return SimilarMoviesViewHolder(ListItemProductionImageBinding.inflate(LayoutInflater.from(
            parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: SimilarMoviesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class SimilarMoviesViewHolder(private val binding: ListItemProductionImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductionCompanies?) {
            binding.productionCompanies = item
            binding.executePendingBindings()
        }
    }

}

class ProductionCompaniesDiffCallback : DiffUtil.ItemCallback<ProductionCompanies>() {
    override fun areItemsTheSame(
        oldItem: ProductionCompanies,
        newItem: ProductionCompanies,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ProductionCompanies,
        newItem: ProductionCompanies,
    ): Boolean {
        return oldItem.id == newItem.id
    }
}