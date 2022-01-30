package com.demo.moviebag.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.moviebag.databinding.ListItemMediaBinding
import com.demo.moviebag.models.MediaImages
import com.demo.moviebag.utils.copyToClipboard

class MediaAdapter() : ListAdapter<MediaImages, MediaAdapter.MediaViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(ListItemMediaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    class MediaViewHolder(private val binding: ListItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: MediaImages?) {
            binding.media = item
            binding.executePendingBindings()

            binding.llHQ.setOnClickListener {
                binding.root.context.copyToClipboard(item?.imageUrlOG.toString())
            }

            binding.llMQ.setOnClickListener {
                binding.root.context.copyToClipboard(item?.imageUrlW500.toString())
            }

            binding.llSQ.setOnClickListener {
                binding.root.context.copyToClipboard(item?.imageUrlW342.toString())
            }

        }
    }

}

class MediaDiffCallback : DiffUtil.ItemCallback<MediaImages>() {
    override fun areItemsTheSame(oldItem: MediaImages, newItem: MediaImages): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MediaImages, newItem: MediaImages): Boolean {
        return oldItem.filePath == newItem.filePath
    }
}
