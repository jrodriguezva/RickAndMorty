package com.jrodriguezva.rickandmortykotlin.utils.extensions

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

fun Context.themeColor(@AttrRes attrRes: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute (attrRes, typedValue, true)
    return typedValue.data
}