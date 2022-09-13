package com.fara.giphy.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.fara.giphy.data.database.entities.GifEntity
import com.fara.giphy.domain.models.GifDomain
import com.fara.giphy.domain.repositories.GifLocalRepository
import com.fara.giphy.domain.repositories.GifRemoteRepository
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GifSearchRemoteMediator(
    private val query: String,
    private val initialRefresh: Boolean,
    private val remoteDataSource: GifRemoteRepository,
    private val localDataSource: GifLocalRepository
) : RemoteMediator<Int, GifEntity>() {

    override suspend fun initialize(): InitializeAction {
        return if (initialRefresh) InitializeAction.LAUNCH_INITIAL_REFRESH else InitializeAction.SKIP_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                LoadType.APPEND -> {
                    localDataSource.getLastItemOffset(query)
                }
            }
            val pagingData = if (query.isEmpty()) {
                remoteDataSource.getTrendingGifs(state.config.pageSize, getOffset(loadKey))
            } else {
                remoteDataSource.searchGifs(query, state.config.pageSize, getOffset(loadKey))
            }
            insertPageData(loadType, pagingData)

            return MediatorResult.Success(
                endOfPaginationReached = pagingData.isEmpty()
            )

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private fun getOffset(loadKey: Int?) = loadKey?.plus(1) ?: 0

    private suspend fun insertPageData(
        loadType: LoadType,
        pagingData: List<GifDomain>
    ) {
        if (loadType == LoadType.REFRESH) {
            localDataSource.insertInitialPage(pagingData, query)
        } else {
            localDataSource.insertPage(pagingData, query)
        }
    }
}
