package com.fara.giphy.presentation.features.preview.model

import com.fara.giphy.domain.models.GifDomain

data class GifUi(
    val id: String,
    val pagingOffset: Int,
    val previewUrl: String,
    val originUrl: String
)

fun GifDomain.toUi() =
    GifUi(
        id = id,
        pagingOffset = offset,
        previewUrl = previewUrl,
        originUrl = originUrl
    )
