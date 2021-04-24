package com.jrodriguezva.rickandmortykotlin.di

import android.content.Context
import androidx.room.Room
import com.jrodriguezva.rickandmortykotlin.data.datasource.LocalDataSource
import com.jrodriguezva.rickandmortykotlin.framework.local.RickAndMortyDatabase
import com.jrodriguezva.rickandmortykotlin.framework.local.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    private const val DATABASE_NAME = "rick_and_morty.db"

    @Provides
    @Singleton
    fun provideLocalDataSource(db: RickAndMortyDatabase): LocalDataSource = RoomDataSource(db)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RickAndMortyDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}