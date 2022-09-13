package com.fara.giphy.data.remote.repositories

import com.fara.giphy.data.remote.api.GiphyApi
import com.fara.giphy.data.remote.responses.GifSearchPageResponse
import com.fara.giphy.domain.models.GifDomain
import com.fara.giphy.domain.models.toDomain
import com.fara.giphy.domain.repositories.GifRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GifRemoteRepositoryImpl @Inject constructor(
    private val apiService: GiphyApi
) : GifRemoteRepository {

    override suspend fun getTrendingGifs(
        limit: Int,
        offset: Int
    ): List<GifDomain> {
        return withContext(Dispatchers.IO) {
            apiService.getTrendingGifs(
                limit,
                offset
            ).let { pageResponse ->
                pageResponse.content?.mapIndexedNotNull { index, gif ->
                    gif?.toDomain(
                        getGifPagingOffset(
                            index,
                            pageResponse
                        )
                    )
                }
            } ?: listOf()
        }
    }

    override suspend fun searchGifs(
        query: String,
        limit: Int,
        offset: Int
    ): List<GifDomain> {
        return withContext(Dispatchers.IO) {
            apiService.searchGifs(
                query,
                limit,
                offset
            ).let { pageResponse ->
                pageResponse.content?.mapIndexedNotNull { index, gif ->
                    gif?.toDomain(
                        getGifPagingOffset(
                            index,
                            pageResponse
                        )
                    )
                } ?: listOf()
            }
        }
    }

    private fun getGifPagingOffset(
        index: Int,
        gifResponse: GifSearchPageResponse
    ) = gifResponse.pagination?.offset?.plus(index) ?: 0
}
