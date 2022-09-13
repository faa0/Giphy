package com.fara.giphy.domain.models

import com.fara.giphy.data.database.entities.GifEntity
import com.fara.giphy.data.remote.responses.GifInfoResponse

data class GifDomain(
    val id: String,
    val offset: Int,
    val previewUrl: String,
    val originUrl: String
)

fun GifInfoResponse.toDomain(offset: Int): GifDomain? {
    return GifDomain(
        id = id ?: return null,
        previewUrl = images?.previewGif?.url ?: return null,
        originUrl = images.originalGif?.url ?: return null,
        offset = offset
    )
}

fun GifEntity.toDomain(): GifDomain {
    return GifDomain(
        id = id,
        previewUrl = previewUrl,
        originUrl = originUrl,
        offset = offset
    )
}
