package com.fara.giphy.data.remote.responses

import com.google.gson.annotations.SerializedName

data class OriginalGifResponse(
    @SerializedName("url")
    val url: String?,
)
