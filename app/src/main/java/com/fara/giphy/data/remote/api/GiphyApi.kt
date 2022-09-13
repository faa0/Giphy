package com.fara.giphy.data.remote.api

import com.fara.giphy.data.remote.responses.GifSearchPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = DEFAULT_RATING,
    ): GifSearchPageResponse

    @GET("v1/gifs/search")
    suspend fun searchGifs(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String = DEFAULT_RATING,
        @Query("lang") lang: String = DEFAULT_LANGUAGE
    ): GifSearchPageResponse

    companion object {
        const val DEFAULT_LANGUAGE = "en"
        const val DEFAULT_RATING = "g"
    }
}
