package com.fara.giphy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fara.giphy.data.database.dao.GifDao
import com.fara.giphy.data.database.entities.GifEntity
import com.fara.giphy.data.database.entities.IgnoredGifEntity

@Database(
    entities = [GifEntity::class, IgnoredGifEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GiphyDatabase : RoomDatabase() {

    abstract fun getGifDao(): GifDao
}
