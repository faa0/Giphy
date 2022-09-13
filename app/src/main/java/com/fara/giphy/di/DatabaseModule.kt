package com.fara.giphy.di

import android.content.Context
import com.fara.giphy.data.database.DatabaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseClient(
        @ApplicationContext context: Context
    ) = DatabaseClient(context)

    @Provides
    fun provideGiphyDao(
        databaseClient: DatabaseClient
    ) = databaseClient.provideGiphyDao()
}
