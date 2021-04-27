package com.jrodriguezva.rickandmortykotlin.framework.local.converters

import androidx.room.TypeConverter
import com.jrodriguezva.rickandmortykotlin.framework.local.Status

class StatusConverters {

    @TypeConverter
    fun toStatus(value: String) = enumValueOf<Status>(value)

    @TypeConverter
    fun fromStatus(value: Status) = value.name
}
