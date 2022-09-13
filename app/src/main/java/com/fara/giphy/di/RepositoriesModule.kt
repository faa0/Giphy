package com.fara.giphy.di

import com.fara.giphy.data.database.repository.GifLocalRepositoryImpl
import com.fara.giphy.data.remote.repositories.GifRemoteRepositoryImpl
import com.fara.giphy.domain.repositories.GifLocalRepository
import com.fara.giphy.domain.repositories.GifRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoriesModule {

    @Binds
    abstract fun provideGifRemoteRepository(
        gifRemoteRepositoryImpl: GifRemoteRepositoryImpl
    ): GifRemoteRepository

    @Binds
    abstract fun provideGifLocalRepository(
        gifLocalRepositoryImpl: GifLocalRepositoryImpl
    ): GifLocalRepository
}
