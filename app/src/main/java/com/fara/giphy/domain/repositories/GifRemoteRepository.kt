package com.fara.giphy.domain.repositories

import com.fara.giphy.domain.models.GifDomain

interface GifRemoteRepository {

    suspend fun getTrendingGifs(
        limit: Int,
        offset: Int
    ): List<GifDomain>

    suspend fun searchGifs(
        query: String,
        limit: Int,
        offset: Int
    ): List<GifDomain>
}
