package com.jrodriguezva.rickandmortykotlin.di

import com.jrodriguezva.rickandmortykotlin.data.datasource.LocalDataSource
import com.jrodriguezva.rickandmortykotlin.data.datasource.RemoteDataSource
import com.jrodriguezva.rickandmortykotlin.data.repository.RickAndMortyRepositoryImpl
import com.jrodriguezva.rickandmortykotlin.domain.repository.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): RickAndMortyRepository = RickAndMortyRepositoryImpl(localDataSource, remoteDataSource)


    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
