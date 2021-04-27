package com.jrodriguezva.rickandmortykotlin.framework.local.converters

import androidx.room.TypeConverter
import com.jrodriguezva.rickandmortykotlin.framework.local.Gender

class GenderConverters {
    @TypeConverter
    fun toGender(value: String) = enumValueOf<Gender>(value)

    @TypeConverter
    fun fromGender(value: Gender) = value.name
}
