package com.jrodriguezva.rickandmortykotlin.utils.extensions

fun String.getIdFromUrl(): Int = substring(lastIndexOf("/") + 1).toIntOrNull() ?: 0
