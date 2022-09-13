package com.fara.giphy.domain.repositories

import androidx.paging.PagingSource
import com.fara.giphy.data.database.entities.GifEntity
import com.fara.giphy.domain.models.GifDomain

interface GifLocalRepository {

    suspend fun insertInitialPage(
        page: List<GifDomain>,
        query: String
    )

    suspend fun insertPage(
        page: List<GifDomain>,
        query: String
    )

    fun getGifPagingSource(
        query: String
    ): PagingSource<Int, GifEntity>

    suspend fun getLastItemOffset(
        query: String
    ): Int

    suspend fun insertIgnoredGifId(
        id: String
    )
}
