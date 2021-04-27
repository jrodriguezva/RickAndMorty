package com.jrodriguezva.rickandmortykotlin.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jrodriguezva.rickandmortykotlin.framework.local.converters.GenderConverters
import com.jrodriguezva.rickandmortykotlin.framework.local.converters.StatusConverters

@Database(entities = [Character::class], version = 1)
@TypeConverters(StatusConverters::class, GenderConverters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
