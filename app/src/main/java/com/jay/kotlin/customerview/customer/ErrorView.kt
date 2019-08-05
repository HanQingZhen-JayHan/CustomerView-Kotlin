package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import com.jay.kotlin.customerview.Animation.ShakeAnimation
import com.jay.kotlin.customerview.utils.ScreenUtils

class ErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint


    private var errorColor: Int
    private var radius: Float

    private var strokeWidth = 10f

    private var padding = 15f
    private var isDone = false
    private var isShaking = false
    private var circleProgress: Float
    private var leftProgress: Float
    private var rightProgress: Float

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        viewWidth = 0f
        viewHeight = 0f

        radius = 0f
        errorColor = Color.RED

        circleProgress = 0f
        leftProgress = 0f
        rightProgress = 0f

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // measure mode
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        // measure size
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        // fix size

        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {

            viewWidth = Math.min(widthSize.toFloat(), heightSize.toFloat())
            viewHeight = viewWidth

        } else if (widthMode == MeasureSpec.EXACTLY) {
            viewWidth = widthSize.toFloat()
            viewHeight = viewWidth

        } else if (heightMode == MeasureSpec.EXACTLY) {

            viewWidth = heightSize.toFloat()
            viewHeight = viewWidth

        } else {

            viewWidth = ScreenUtils.dp2px(context, 100f)
            viewHeight = viewWidth
        }

        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        isDone = false
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.color = errorColor

        val cx = viewWidth / 2
        val cy = viewHeight / 2
        val angle = 3.6f * circleProgress
        radius = cx - padding
        if (circleProgress > 100) {
            circleProgress = 100f
        }
        canvas?.drawArc(cx - radius, cy - radius, cx + radius, cy + radius, 180f, angle, false, paint)

        if (circleProgress >= 100) {
            if (leftProgress > 100) {
                leftProgress = 100f
            }
            canvas?.rotate(-45f, cx, cy)
            canvas?.drawLine(cx - 0.7f * radius, cy, cx + 0.007f * radius * leftProgress, cy, paint)
            canvas?.rotate(45f, cx, cy)
        }
        if (leftProgress >= 100) {
            if (rightProgress > 100) {
                rightProgress = 100f
            }
            canvas?.rotate(45f, cx, cy)
            canvas?.drawLine(cx - 0.7f * radius, cy, cx + 0.007f * radius * rightProgress, cy, paint)
            canvas?.rotate(-45f, cx, cy)
        }

        Log.d("errorView", "circleProgress:$circleProgress, leftProgress:$leftProgress, rightProgress:$rightProgress ")
        if (circleProgress < 100) {
            circleProgress += 5

        } else if (leftProgress < 100) {
            leftProgress += 5
        } else if (rightProgress < 100) {
            rightProgress += 5
        } else {
            isDone = true
        }

        if (!isDone) {
            postInvalidateDelayed(10)
        } else {
            shakeAnimation()
        }

    }

    fun shakeAnimation() {
        if (isShaking) {
            return
        }

        val shake = ShakeAnimation()
        shake.duration = 500
        shake.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                isShaking = true
            }

            override fun onAnimationEnd(p0: Animation?) {
                isShaking = false
            }
        })
        startAnimation(shake)
    }

    open fun showError() {
        circleProgress = 0f
        leftProgress = 0f
        rightProgress = 0f
        invalidate()
    }
}

