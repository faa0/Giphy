package com.fara.giphy.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("original")
    val originalGif: OriginalGifResponse?,
    @SerializedName("preview_gif")
    val previewGif: PreviewGifResponse?,
)
