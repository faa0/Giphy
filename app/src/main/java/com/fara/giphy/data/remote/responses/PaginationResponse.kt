package com.fara.giphy.data.remote.responses


import com.google.gson.annotations.SerializedName

data class PaginationResponse(
    @SerializedName("offset")
    val offset: Int?,
)
