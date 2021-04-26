package com.jrodriguezva.rickandmortykotlin.utils.extensions

fun String.getIdFromUrl(): Int {
    return substring(lastIndexOf("/") + 1).toIntOrNull() ?: 0
}