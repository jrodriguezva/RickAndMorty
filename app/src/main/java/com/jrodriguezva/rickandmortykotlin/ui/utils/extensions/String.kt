package com.jrodriguezva.rickandmortykotlin.ui.utils.extensions

fun String.getIdFromUrl(): Int {
    return substring(lastIndexOf("/") + 1).toIntOrNull() ?: 0
}