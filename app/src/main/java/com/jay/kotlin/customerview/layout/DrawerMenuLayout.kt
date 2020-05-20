package com.jay.kotlin.customerview.layout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.core.view.get

class DrawerMenuLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), View.OnClickListener {


    private var isShow = false
    private val duration: Long = 200

    init {

    }

    override fun onClick(v: View?) {

        if (childCount < 2) {
            return
        }
        isShow = !isShow

        if (isShow) {
            for (index in 1 until childCount) {
                val childView = getChildAt(index)
                val translate = TranslateAnimation((-childView.measuredWidth).toFloat(), 0f, 0f, 0f)
                translate.duration = duration + index * 200
                translate.fillAfter = true
                childView.visibility = View.VISIBLE
                childView.startAnimation(translate)
            }
        } else {
            for (index in 1 until childCount) {
                val childView = getChildAt(index)
                val translate = TranslateAnimation(0f, (-childView.measuredWidth).toFloat(), 0f, 0f)
                translate.duration = duration + index * 200

                childView.startAnimation(translate)
                childView.visibility = View.GONE
            }
        }

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (index in 0 until childCount) {
            measureChild(get(index), widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        if (count == 0) {
            return
        }
        layoutAnchor()
        if (count == 1) {
            return
        }
        var positionY = measuredHeight - getChildAt(0).measuredHeight

        for (index in 1 until count) {
            val childView = getChildAt(index)
            childView.setOnClickListener(this)
            childView.layout(0, positionY - childView.measuredHeight, childView.measuredWidth, positionY)
            childView.visibility = View.GONE

            positionY -= childView.measuredHeight
        }
    }

    private fun layoutAnchor() {

        val view = getChildAt(0)
        view.setOnClickListener(this)
        view.layout(0, measuredHeight - view.measuredHeight, view.measuredWidth, measuredHeight)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }


}