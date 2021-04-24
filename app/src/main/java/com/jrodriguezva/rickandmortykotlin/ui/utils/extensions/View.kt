package com.jrodriguezva.rickandmortykotlin.com.jrodriguezva.rickandmortykotlin.ui.utils.extensions

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.rickandmortykotlin.ui.utils.handler.EndlessScroll

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun RecyclerView.endless(visibleThreshold: Int = 10, loadMore: () -> Unit) {
    this.addOnScrollListener(EndlessScroll(this, visibleThreshold, loadMore))
}