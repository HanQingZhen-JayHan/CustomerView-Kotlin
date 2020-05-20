package com.jay.kotlin.customerview.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridLayoutDecoration(private val offset: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)


        if (position % 2 == 0) {
            outRect.left = offset
            outRect.right = offset / 2
        } else {
            outRect.left = offset / 2
            outRect.right = offset
        }

        if (position < 2) {
            outRect.top = offset
        }
        outRect.bottom = offset

    }

}