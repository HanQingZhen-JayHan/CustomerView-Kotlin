package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.utils.ScreenUtils

class PopupView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint


    private var popupColor: Int
    private var popupTriangleWidth: Float
    private var popupTriangleHeight: Float

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 10f
        viewWidth = 0f
        viewHeight = 0f

        val a = context.obtainStyledAttributes(R.styleable.PopupView)

        popupColor = a.getColor(R.styleable.PopupView_popupColor, 0xff6950a1.toInt())
        popupTriangleWidth = a.getDimension(R.styleable.PopupView_popupTriangleWidth, ScreenUtils.dp2px(context, 30f))
        popupTriangleHeight = a.getDimension(R.styleable.PopupView_popupTriangleWidth, ScreenUtils.dp2px(context, 10f))


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

        paint.color = popupColor
        val adjustHeight = viewHeight - popupTriangleHeight
        canvas?.drawRoundRect(0f, 0f, viewWidth, adjustHeight, adjustHeight * 0.1f, adjustHeight * 0.1f, paint)

        val path = Path()
        path.moveTo((viewWidth - popupTriangleWidth) / 2, adjustHeight)
        path.lineTo(viewWidth / 2, adjustHeight + popupTriangleHeight)
        path.lineTo((viewWidth + popupTriangleWidth) / 2, adjustHeight)
        path.close()

        canvas?.drawPath(path, paint)

        //canvas?.drawCircle((viewWidth - popupTriangleWidth) / 2, viewHeight,40f,paint)
    }
}