package com.fara.giphy.domain.interactors

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.fara.giphy.domain.GifSearchRemoteMediator
import com.fara.giphy.domain.models.GifDomain
import com.fara.giphy.domain.models.toDomain
import com.fara.giphy.domain.repositories.GifLocalRepository
import com.fara.giphy.domain.repositories.GifRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class GifInteractor @Inject constructor(
    private val gifLocalRepository: GifLocalRepository,
    private val gifRemoteRepository: GifRemoteRepository
) {

    fun getPaging(
        query: String,
        initialRefresh: Boolean
    ): Flow<PagingData<GifDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE
            ),
            remoteMediator = GifSearchRemoteMediator(
                query,
                initialRefresh,
                gifRemoteRepository,
                gifLocalRepository
            )
        ) {
            gifLocalRepository.getGifPagingSource(query)
        }.flow.map { paging ->
            paging.map { gifEntity ->
                gifEntity.toDomain()
            }
        }
    }

    suspend fun ignoreGif(gifId: String) {
        gifLocalRepository.insertIgnoredGifId(gifId)
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}
