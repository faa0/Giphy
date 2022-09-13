package com.fara.giphy.data.remote.responses


import com.google.gson.annotations.SerializedName

data class GifInfoResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("images")
    val images: ImagesResponse?,
)
