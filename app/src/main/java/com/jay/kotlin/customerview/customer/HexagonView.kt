package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.jay.kotlin.customerview.utils.ScreenUtils

class HexagonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint


    private var rowCount: Int
    private var columnCount: Int
    private var radius: Float
    private var paddingX: Float
    private var paddingY: Float

    private val strokeWidth = 2f
    private val halfSqrt3Radius: Float
    private val halfRadius: Float

    private val colors: Array<Int>


    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = strokeWidth
        viewWidth = 0f
        viewHeight = 0f

        paddingX = 0f
        paddingY = 0f
        radius = 100f
        rowCount = 4
        columnCount = 5

        halfSqrt3Radius = (Math.sqrt(3.0) * 0.5f * radius).toFloat()
        halfRadius = radius * 0.5f

        colors = arrayOf(
            Color.parseColor("#49C247"),
            Color.parseColor("#3784A4"),
            Color.parseColor("#ECC6DD"),
            Color.parseColor("#C2A7E2"),
            Color.parseColor("#3696A1"),
            Color.parseColor("#24306B"),
            Color.parseColor("#C16B44"),
            Color.parseColor("#10260D"),
            Color.parseColor("#123536")
        )
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

        val totalWidth = 2 * halfSqrt3Radius * columnCount
        val totalHeight = 2 * radius * rowCount
        if (totalWidth < viewWidth) {
            paddingX = (viewWidth - totalWidth) / 2
        }
        if (totalHeight < viewHeight) {
            paddingY = (viewHeight - totalHeight) / 2
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var cy = paddingY + radius
        var cx = 0f

        for (h in 0 until rowCount) {
            val isEven: Boolean = (h % 2) == 0
            if (isEven) {
                cx = paddingX + halfSqrt3Radius
            } else {
                cx = paddingX + 2 * halfSqrt3Radius
            }
            val column = columnCount - h % 2 - 1
            for (v in 0..column) {
                paint.color = colors[h % colors.size]
                drawHexagon(canvas, cx, cy, radius, paint)
                cx += 2 * halfSqrt3Radius
            }
            cy += 3 * halfRadius
        }
    }

    fun drawHexagon(canvas: Canvas?, cx: Float, cy: Float, r: Float, p: Paint) {
        if (cx + halfSqrt3Radius > viewWidth || cy + r > viewHeight) {
            return
        }
        val path = Path()
        path.moveTo(cx, cy - r)
        path.lineTo(cx - halfSqrt3Radius, cy - halfRadius)
        path.lineTo(cx - halfSqrt3Radius, cy + halfRadius)
        path.lineTo(cx, cy + r)
        path.lineTo(cx + halfSqrt3Radius, cy + halfRadius)
        path.lineTo(cx + halfSqrt3Radius, cy - halfRadius)
        path.close()
        canvas?.drawPath(path, p)
    }

    fun draw(r: Int, c: Int) {
        rowCount = r
        columnCount = c
        invalidate()
    }
}