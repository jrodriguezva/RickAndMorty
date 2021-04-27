package com.jrodriguezva.rickandmortykotlin.framework.di

import android.content.Context
import androidx.room.Room
import com.jrodriguezva.rickandmortykotlin.data.datasource.LocalDataSource
import com.jrodriguezva.rickandmortykotlin.di.LocalModule
import com.jrodriguezva.rickandmortykotlin.framework.local.RickAndMortyDatabase
import com.jrodriguezva.rickandmortykotlin.framework.local.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocalModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(db: RickAndMortyDatabase): LocalDataSource = RoomDataSource(db)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return Room.inMemoryDatabaseBuilder(
            context, RickAndMortyDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }
}
