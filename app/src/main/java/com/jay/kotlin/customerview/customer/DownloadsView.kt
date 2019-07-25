package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import com.jay.kotlin.customerview.Animation.ShakeAnimation
import com.jay.kotlin.customerview.utils.ScreenUtils

class DownloadsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint


    private var downloadColor: Int
    private var radius: Float

    private var strokeWidth = 20f

    private var padding = 15f
    private var isDone = false
    private var isShaking = false
    private var lineProgress: Float
    private var pathProgress: Float
    private var circleProgress: Float
    private var ballProgress: Float

    private var isShowDrawingLine = false
    private var isShowDrawingPath = false
    private var isShowDrawingCirlce = false

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        viewWidth = 0f
        viewHeight = 0f

        radius = 0f
        downloadColor = Color.parseColor("#4f2EA4F2")


        lineProgress = 0f
        pathProgress = 0f
        circleProgress = 0f
        ballProgress = 0f

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
        paint.color = downloadColor

        val cx = viewWidth / 2
        val cy = viewHeight / 2
        radius = viewWidth / 2 - strokeWidth
        if (isShowDrawingCirlce) {
            paint.color = Color.WHITE
            canvas?.drawArc(
                cx - radius,
                cy - radius,
                cx + radius,
                cy + radius,
                270f,
                -3.6f * circleProgress,
                false,
                paint
            )
            paint.color = downloadColor
            canvas?.drawArc(
                cx - radius,
                cy - radius,
                cx + radius,
                cy + radius,
                270f - 3.6f * circleProgress,
                3.6f * circleProgress - 360,
                false,
                paint
            )
            if (circleProgress < 100) {
                circleProgress += 10
            }

        } else {
            canvas?.drawCircle(cx, cy, radius, paint)
        }

        paint.color = Color.WHITE
        if (isShowDrawingLine) {


            if (lineProgress >= 95) {
                lineProgress = 95f
                if (pathProgress > 90) {
                    ballProgress += 10
                }
                if (ballProgress <= 100) {

                    paint.style = Paint.Style.FILL
                    canvas?.drawCircle(
                        cx,
                        cy - radius * ballProgress / 100,
                        strokeWidth,
                        paint
                    )
                } else {
                    isShowDrawingCirlce = true
                }
                isShowDrawingPath = true
            } else {
                canvas?.drawLine(
                    cx,
                    cy - 0.5f * radius + 0.005f * radius * lineProgress,
                    cx,
                    cy + 0.5f * radius - 0.005f * radius * lineProgress,
                    paint
                )
                lineProgress += 10
            }
        } else {
            canvas?.drawLine(cx, cy - 0.5f * radius, cx, cy + 0.5f * radius, paint)
        }


        val path = Path()
        path.moveTo(cx - 0.5f * radius, cy)
        if (isShowDrawingPath) {

            if (isShowDrawingCirlce) {
                path.lineTo(cx, cy + 0.5f * radius - 0.005f * radius * pathProgress)
                path.lineTo(cx + 0.5f * radius, cy - 0.5f * radius + 0.005f * radius * pathProgress)
                if (pathProgress < 1) {
                    pathProgress = 0f
                } else {
                    pathProgress -= 10
                }
            } else {
                path.lineTo(cx, cy + 0.5f * radius - 0.005f * radius * pathProgress)
                path.lineTo(cx + 0.5f * radius, cy)
                if (pathProgress >= 100) {
                    pathProgress = 100f
                } else {
                    pathProgress += 10
                }
            }
        } else {
            path.lineTo(cx, cy + 0.5f * radius)
            path.lineTo(cx + 0.5f * radius, cy)
        }
        paint.style = Paint.Style.STROKE
        canvas?.drawPath(path, paint)

        var delayTime = 200L
        if (isShowDrawingLine) {
            delayTime = 50L

        } else {
            isShowDrawingLine = true
        }
        if (circleProgress > 100) {
            lineProgress = 0f
            pathProgress = 0f
            circleProgress = 0f
            ballProgress = 0f
            isShowDrawingCirlce = false
            isShowDrawingLine = false
            isShowDrawingPath = false
        } else {
            postInvalidateDelayed(delayTime)
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

    open fun showLoading() {
        lineProgress = 0f
        pathProgress = 0f
        circleProgress = 0f
        ballProgress = 0f
        isShowDrawingCirlce = false
        isShowDrawingLine = false
        isShowDrawingPath = false
        invalidate()
    }
}

