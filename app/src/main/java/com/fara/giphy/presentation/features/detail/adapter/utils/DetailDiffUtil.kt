package com.fara.giphy.presentation.features.detail.adapter.utils

import androidx.recyclerview.widget.DiffUtil
import com.fara.giphy.presentation.features.preview.model.GifUi

class DetailDiffUtil : DiffUtil.ItemCallback<GifUi>() {

    override fun areItemsTheSame(
        oldItem: GifUi,
        newItem: GifUi
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GifUi,
        newItem: GifUi
    ): Boolean {
        return oldItem.originUrl == newItem.originUrl
    }
}