package com.fara.giphy.data.remote.responses


import com.google.gson.annotations.SerializedName

data class GifSearchPageResponse(
    @SerializedName("data")
    val content: List<GifInfoResponse?>?,
    @SerializedName("pagination")
    val pagination: PaginationResponse?
)
