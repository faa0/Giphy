package com.fara.giphy.di

import com.fara.giphy.data.remote.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkClient() = NetworkClient()

    @Singleton
    @Provides
    fun provideGiphyApiService(
        networkClient: NetworkClient
    ) = networkClient.provideGiphyApiService()
}
