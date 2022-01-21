package com.demo.moviebag.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide

@BindingAdapter("setImageUrl")
fun setImageUrl(iv: ImageView?, url: String?) {
    iv?.let {
        if (!url.isNullOrEmpty()) {
            Glide.with(it.context).load(url).into(iv)
        }
    }
}

@BindingAdapter("isRefreshing")
fun isRefreshing(swipeRefreshLayout: SwipeRefreshLayout?, isLoading: Boolean = false) {
    swipeRefreshLayout?.let {
        swipeRefreshLayout.isRefreshing = isLoading
    }
}