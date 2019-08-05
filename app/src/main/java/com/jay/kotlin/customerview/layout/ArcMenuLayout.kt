package com.jay.kotlin.customerview.layout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import androidx.core.view.get

class ArcMenuLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr), View.OnClickListener {


    private var isShow = false
    private val duration: Long = 200
    private var cx: Float
    private var cy: Float
    private var radius: Float


    init {

        cx = 0f
        cy = 0f
        radius = 0f

    }

    override fun onClick(v: View?) {

        if (childCount < 2) {
            return
        }
        isShow = !isShow
        val angle = 180.0 / childCount
        if (isShow) {
            for (index in 1 until childCount) {
                val childView = getChildAt(index)
                val a = angle * index * Math.PI / 180
                val x = -Math.cos(a) * radius
                val y = -Math.sin(a) * radius

                Log.d("ArcMenu", "angle:$a, x:$x, y:$y")

                val animationDuration = duration + index * 100
                val translate = TranslateAnimation(0f, x.toFloat(), 0f, y.toFloat())
                translate.duration = animationDuration
                translate.fillAfter = true

                val rotate = RotateAnimation(0f, 360f)
                rotate.duration = animationDuration
                rotate.fillAfter = true

                val alphaAnimation = AlphaAnimation(0f, 255f)
                alphaAnimation.duration = animationDuration
                alphaAnimation.fillAfter = true

                val animationSet = AnimationSet(true)
                animationSet.addAnimation(translate)
                animationSet.addAnimation(rotate)
                animationSet.addAnimation(alphaAnimation)
                animationSet.fillAfter = true

                childView.visibility = View.VISIBLE
                childView.startAnimation(animationSet)
            }
        } else {
            for (index in 1 until childCount) {
                val childView = getChildAt(index)
                val a = angle * index * Math.PI / 180
                val x = -Math.cos(a) * radius
                val y = -Math.sin(a) * radius
                val translate = TranslateAnimation(x.toFloat(), 0f, y.toFloat(), 0f)
                translate.duration = duration + index * 100

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
        cx = measuredWidth / 2f
        cy = measuredHeight / 2f
        layoutAnchor()
        if (count == 1) {
            return
        }
        radius = (Math.min(measuredHeight, measuredWidth) / 2).toFloat() - getChildAt(count / 2).measuredHeight / 2


        for (index in 1 until count) {
            val childView = getChildAt(index)

            childView.layout(
                (cx - childView.measuredWidth / 2).toInt(),
                measuredHeight - childView.measuredHeight,
                (cx + childView.measuredWidth / 2).toInt(),
                measuredHeight
            )
            childView.visibility = View.GONE
        }
    }

    private fun layoutAnchor() {

        val view = getChildAt(0)
        view.setOnClickListener(this)
        view.layout(
            (cx - view.measuredWidth / 2).toInt(),
            measuredHeight - view.measuredHeight,
            (cx + view.measuredWidth / 2).toInt(),
            measuredHeight
        )
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }


}