package com.jrodriguezva.rickandmortykotlin.di

import com.jrodriguezva.rickandmortykotlin.BuildConfig
import com.jrodriguezva.rickandmortykotlin.data.datasource.RemoteDataSource
import com.jrodriguezva.rickandmortykotlin.framework.network.NetworkUtils
import com.jrodriguezva.rickandmortykotlin.framework.network.RetrofitDataSource
import com.jrodriguezva.rickandmortykotlin.framework.network.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .followRedirects(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
        .client(okHttpClient)
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideRetrofitDataSource(retrofit: Retrofit, networkUtils: NetworkUtils): RemoteDataSource =
        RetrofitDataSource(retrofit.create(RickAndMortyApi::class.java), networkUtils)
}
