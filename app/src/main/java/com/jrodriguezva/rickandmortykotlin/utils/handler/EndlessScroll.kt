package com.jrodriguezva.rickandmortykotlin.utils.handler

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class EndlessScroll(
    private val recyclerView: RecyclerView,
    private val visibleThreshold: Int = 15,
    private val loadMore: () -> Unit,
) : RecyclerView.OnScrollListener() {

    private var previousTotal = 1
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (this.recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            return
        }
        with(recyclerView) {
            when (layoutManager) {
                is StaggeredGridLayoutManager -> {
                    val lastVisibleItemPositions =
                        (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                    loading(getLastVisibleItem(lastVisibleItemPositions))
                }
                is GridLayoutManager -> loading((layoutManager as GridLayoutManager).findLastVisibleItemPosition())

                is LinearLayoutManager -> loading((layoutManager as LinearLayoutManager).findLastVisibleItemPosition())
            }
        }
    }

    private fun loading(firstVisibleItem: Int) {
        with(recyclerView) {
            val visibleItemCount = childCount
            val totalItemCount = layoutManager?.itemCount ?: 0

            if (loading && totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                loadMore()
                loading = true
            }
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}
