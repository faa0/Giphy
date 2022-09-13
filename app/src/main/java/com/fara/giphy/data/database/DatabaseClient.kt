package com.fara.giphy.data.database

import android.content.Context
import androidx.room.Room
import javax.inject.Inject

class DatabaseClient @Inject constructor(
    context: Context
) {

    private val provideDatabase = provideGiphyDatabase(context)

    fun provideGiphyDao() = provideDatabase.getGifDao()

    private fun provideGiphyDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context,
        GiphyDatabase::class.java,
        "giphy_db"
    ).build()
}