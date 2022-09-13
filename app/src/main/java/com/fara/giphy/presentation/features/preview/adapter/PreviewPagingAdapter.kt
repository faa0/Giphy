package com.fara.giphy.presentation.features.preview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.fara.giphy.databinding.ItemPreviewBinding
import com.fara.giphy.presentation.features.preview.adapter.utils.PreviewDiffUtil
import com.fara.giphy.presentation.features.preview.model.GifUi

class PreviewPagingAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onItemLongClick: (String) -> Unit
) : PagingDataAdapter<GifUi, PreviewPagingAdapter.PreviewViewHolder>(PreviewDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PreviewViewHolder(
        ItemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PreviewViewHolder(
        private val binding: ItemPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: GifUi? = null

        init {
            binding.root.setOnLongClickListener {
                currentItem?.let { item -> onItemLongClick(item.id) }
                true
            }
            binding.root.setOnClickListener {
                onItemClick(absoluteAdapterPosition)
            }
        }

        fun bind(item: GifUi?) {
            currentItem = item
            item?.also {
                Glide.with(binding.imageViewGif)
                    .load(it.previewUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(binding.imageViewGif)
            }
        }
    }
}
