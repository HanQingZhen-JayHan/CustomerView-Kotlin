package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.utils.ScreenUtils

class WaveView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint


    private var waveColor: Int
    private var waveRadius: Float

    private val strokeWidth = 2f

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = strokeWidth
        viewWidth = 0f
        viewHeight = 0f

        val a = context.obtainStyledAttributes(R.styleable.WaveView)

        waveColor = a.getColor(R.styleable.WaveView_waveColor, 0xff6950a1.toInt())
        waveRadius = a.getDimension(R.styleable.WaveView_waveRadius, ScreenUtils.dp2px(context, 5f))

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
            viewWidth = widthSize.toFloat()
            viewHeight = heightSize.toFloat()

        } else if (widthMode == MeasureSpec.EXACTLY) {
            viewWidth = widthSize.toFloat()
            viewHeight = viewWidth

        } else if (heightMode == MeasureSpec.EXACTLY) {

            viewWidth = heightSize.toFloat()
            viewHeight = viewWidth

        } else {

            viewWidth = ScreenUtils.dp2px(context, 100f)
            viewHeight = viewWidth * 0.7f
        }

        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        paint.color = waveColor
        val adjustWidth = viewWidth - waveRadius
        canvas?.drawRect(waveRadius - 1f, 0f, adjustWidth + 1f, viewHeight, paint)

        var h = 0f
        while (h < viewHeight) {
            //left
            canvas?.drawArc(0f, h, 2 * waveRadius, h + 2 * waveRadius, 90f, 180f, true, paint)

            //right
            canvas?.drawArc(adjustWidth - waveRadius, h, viewWidth, h + 2 * waveRadius, 270f, 180f, true, paint)

            h += 2 * waveRadius
        }
    }
}