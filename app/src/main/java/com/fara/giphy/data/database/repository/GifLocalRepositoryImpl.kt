package com.fara.giphy.data.database.repository

import androidx.paging.PagingSource
import com.fara.giphy.data.database.dao.GifDao
import com.fara.giphy.data.database.entities.GifEntity
import com.fara.giphy.data.database.entities.IgnoredGifEntity
import com.fara.giphy.data.database.entities.toEntity
import com.fara.giphy.domain.models.GifDomain
import com.fara.giphy.domain.repositories.GifLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class GifLocalRepositoryImpl @Inject constructor(
    private val gifDao: GifDao
) : GifLocalRepository {

    override suspend fun insertInitialPage(
        page: List<GifDomain>,
        query: String
    ) {
        withContext(Dispatchers.IO) {
            gifDao.insertRefreshPage(page.map { gifImage ->
                gifImage.toEntity(query)
            }, query)
        }
    }

    override suspend fun insertPage(
        page: List<GifDomain>,
        query: String
    ) {
        withContext(Dispatchers.IO) {
            gifDao.insertAll(page.map { gifDomain ->
                gifDomain.toEntity(query)
            })
        }
    }

    override fun getGifPagingSource(
        query: String
    ): PagingSource<Int, GifEntity> {
        return gifDao.getGifPagingSource(query)
    }

    override suspend fun getLastItemOffset(
        query: String
    ): Int {
        return withContext(Dispatchers.IO) {
            gifDao.getLastItemOffset(query)
        }
    }

    override suspend fun insertIgnoredGifId(
        id: String
    ) {
        withContext(Dispatchers.IO) {
            gifDao.insertIgnoredId(IgnoredGifEntity(id))
        }
    }
}
