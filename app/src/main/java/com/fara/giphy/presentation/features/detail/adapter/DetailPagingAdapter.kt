package com.fara.giphy.presentation.features.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fara.giphy.databinding.ItemDetailBinding
import com.fara.giphy.presentation.features.detail.adapter.utils.DetailDiffUtil
import com.fara.giphy.presentation.features.preview.model.GifUi

class DetailPagingAdapter :
    PagingDataAdapter<GifUi, DetailPagingAdapter.DetailViewHolder>(DetailDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailViewHolder(
        ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DetailViewHolder(
        private val binding: ItemDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GifUi?) {
            item?.let { gifUi ->
                Glide.with(binding.imageViewGif)
                    .load(gifUi.originUrl)
                    .into(binding.imageViewGif)
            }
        }
    }
}
