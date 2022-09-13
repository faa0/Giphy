package com.fara.giphy.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.fara.giphy.data.database.entities.GifEntity
import com.fara.giphy.data.database.entities.IgnoredGifEntity

@Dao
interface GifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<GifEntity>)

    @Query("SELECT * FROM gifs WHERE `query` = :query AND id NOT IN (SELECT id FROM ignored_gifs) ORDER BY `offset` ASC")
    fun getGifPagingSource(query: String): PagingSource<Int, GifEntity>

    @Query("DELETE FROM gifs WHERE `query` = :query")
    suspend fun deleteGifsByQuery(query: String)

    @Query("SELECT CASE COUNT(*) WHEN 0 THEN 0 ELSE MAX(`offset`) END FROM gifs WHERE `query` = :query")
    suspend fun getLastItemOffset(query: String): Int

    @Transaction
    suspend fun insertRefreshPage(list: List<GifEntity>, query: String) {
        deleteGifsByQuery(query)
        insertAll(list)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnoredId(ignored: IgnoredGifEntity)
}
