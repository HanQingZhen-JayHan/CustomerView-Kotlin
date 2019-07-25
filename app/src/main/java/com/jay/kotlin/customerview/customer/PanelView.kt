package com.jay.kotlin.customerview.customer

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.utils.ScreenUtils

class PanelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint


    private var panelColor: Int
    private var coreRadius: Float

    private var strokeWidth = 5f

    private var scaleValue = 0.0f
    private val totalAngle = 250f
    private val extendAngle = 15f
    private val scaleCount = 12

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        viewWidth = 0f
        viewHeight = 0f

        val a = context.obtainStyledAttributes(R.styleable.PanelView)

        panelColor = a.getColor(R.styleable.PanelView_panelColor, 0xff6950a1.toInt())
        coreRadius = a.getDimension(R.styleable.PanelView_panelCoreRadius, ScreenUtils.dp2px(context, 20f))

        a.recycle()

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

        val angle = totalAngle / scaleCount

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.color = panelColor

        // 1. arc
        canvas?.drawArc(
            strokeWidth,
            strokeWidth,
            viewWidth - strokeWidth,
            viewHeight - strokeWidth,
            270f - totalAngle / 2,
            totalAngle,
            false,
            paint
        )

        // 2. arc

        val divider1 = 100f

        val thickArcWidth = divider1 * 0.7f
        paint.strokeWidth = thickArcWidth

        val thickArcMiddleAngle = angle * scaleValue
        if (scaleValue > 0f) {
            paint.color = panelColor
        } else {
            paint.color = Color.WHITE
        }
        canvas?.drawArc(
            strokeWidth + divider1,
            strokeWidth + divider1,
            viewWidth - strokeWidth - divider1,
            viewHeight - strokeWidth - divider1,
            270f - totalAngle / 2 - extendAngle,
            extendAngle + thickArcMiddleAngle,
            false,
            paint
        )

        if (scaleValue < scaleCount) {
            paint.color = Color.WHITE
            canvas?.drawArc(
                strokeWidth + divider1,
                strokeWidth + divider1,
                viewWidth - strokeWidth - divider1,
                viewHeight - strokeWidth - divider1,
                270f - totalAngle / 2 + thickArcMiddleAngle,
                totalAngle - thickArcMiddleAngle,
                false,
                paint
            )
        }

        if (scaleValue >= scaleCount) {
            paint.color = panelColor
        } else {
            paint.color = Color.WHITE
        }
        canvas?.drawArc(
            strokeWidth + divider1,
            strokeWidth + divider1,
            viewWidth - strokeWidth - divider1,
            viewHeight - strokeWidth - divider1,
            totalAngle / 2 - 90,
            extendAngle,
            false,
            paint
        )

        //3. circle

        val radiusBig = 40f
        paint.color = panelColor
        paint.strokeWidth = strokeWidth
        canvas?.drawCircle(viewWidth / 2, viewHeight / 2, radiusBig, paint)

        //4. circle
        val radiusSmall = 20f
        paint.color = 0xffafb4db.toInt()
        paint.strokeWidth = strokeWidth * 2
        canvas?.drawCircle(viewWidth / 2, viewHeight / 2, radiusSmall, paint)

        //5. rectangle
        val cx = 0.5f * viewWidth
        val cy = 0.8f * viewHeight
        paint.style = Paint.Style.FILL
        paint.color = panelColor
        canvas?.drawRect(
            cx - 1f * radiusBig,
            cy - 0.5f * radiusBig,
            cx + 1f * radiusBig,
            cy + 0.5f * radiusBig,
            paint
        )
        //6. draw text
        val text = "FINISHED"
        paint.textSize = ScreenUtils.sp2px(context, 10)
        paint.style = Paint.Style.FILL
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        canvas?.drawText(text, cx - (bounds.width() / 2), cy + 0.5f * radiusBig + 15f + bounds.height(), paint)

        //7.draw scale
        paint.strokeWidth = strokeWidth
        val scaleLength = ScreenUtils.dp2px(context, 10f)
        //draw right
        canvas?.drawLine(viewWidth / 2, strokeWidth, viewWidth / 2, scaleLength, paint)
        for (i in 1..scaleCount / 2) {
            canvas?.rotate(angle, viewWidth / 2, viewHeight / 2)
            canvas?.drawLine(viewWidth / 2, strokeWidth, viewWidth / 2, scaleLength, paint)
        }
        canvas?.rotate(-angle * scaleCount.toInt() / 2, viewWidth / 2, viewHeight / 2)

        //draw left
        for (i in 1..scaleCount / 2) {
            canvas?.rotate(-angle, viewWidth / 2, viewHeight / 2)
            canvas?.drawLine(viewWidth / 2, strokeWidth, viewWidth / 2, scaleLength, paint)
        }
        canvas?.rotate(angle * scaleCount.toInt() / 2, viewWidth / 2, viewHeight / 2)

        //draw indicator
        val rotateValue = angle * (scaleValue - scaleCount / 2)
        canvas?.rotate(rotateValue, viewWidth / 2, viewHeight / 2)
        val indicateStart = thickArcWidth * 2
        val indicateEnd = viewHeight / 2 - radiusSmall
        paint.color = 0xffafb4db.toInt()
        canvas?.drawLine(viewWidth / 2, indicateStart, viewWidth / 2, indicateEnd, paint)
        canvas?.rotate(-rotateValue, viewWidth / 2, viewHeight / 2)
    }

    fun setScaleValue(scaleValue: Float) {

        if (scaleValue < 1) {
            this.scaleValue = 0f
            invalidate()
        } else {
            var p: Float = scaleValue
            if (scaleValue > 12) {
                p = 12f
            }
            val animation = ValueAnimator.ofFloat(0f, p)
            animation.duration = 2000
            animation.addUpdateListener {
                this.scaleValue = it.animatedValue as Float
                invalidate()
            }

            animation.interpolator = AccelerateDecelerateInterpolator()
            animation.start()
        }
    }
}