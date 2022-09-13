package com.fara.giphy.data.remote

import com.fara.giphy.BuildConfig
import com.fara.giphy.data.remote.api.GiphyApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient {

    private val provideRetrofit = provideRetrofit(provideOkHttpClientBuilder().build())

    fun provideGiphyApiService(): GiphyApi = provideRetrofit.create(GiphyApi::class.java)

    private fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideOkHttpClientBuilder() = OkHttpClient()
        .newBuilder()
        .addInterceptor(provideAuthInterceptor())
        .addInterceptor(provideLoggingInterceptor())
        .callTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)

    private fun provideAuthInterceptor() = Interceptor { chain ->
        val request = chain.request()
        val updatedUrl = request.url.newBuilder()
            .addQueryParameter(
                KEY_QUERY,
                BuildConfig.GIPHY_KEY
            )
            .build()
        val updatedRequest = request.newBuilder()
            .url(updatedUrl)
            .build()
        return@Interceptor chain.proceed(updatedRequest)

    }

    private fun provideLoggingInterceptor() = HttpLoggingInterceptor().setLevel(
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    )

    private companion object {
        const val KEY_QUERY = "api_key"
        const val DEFAULT_TIMEOUT = 30L
    }
}