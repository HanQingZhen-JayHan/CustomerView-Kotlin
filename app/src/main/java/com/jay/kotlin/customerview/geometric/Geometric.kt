package com.jay.kotlin.customerview.geometric

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class Geometric @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint: Paint
    private var path: Path

    init {
        paint = Paint()
        paint.color = Color.parseColor("#2EA4F2")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
        paint.isAntiAlias = true

        path = Path()
        path.moveTo(100f, 300f)
        path.lineTo(100f, 500f)
        path.lineTo(600f, 500f)
        path.close()

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //draw line
        canvas?.drawLine(100f, 100f, 200f, 200f, paint)

        //draw path
        canvas?.drawPath(path, paint)

        //draw rect
        val rect = RectF(100f, 600f, 800f, 700f)
        canvas?.drawRect(rect, paint)

        //draw circle
        canvas?.drawCircle(500f, 900f, 150f, paint)

        //draw arc
        val rectForArc = RectF(100f, 1150f, 500f, 1450f)
        paint.strokeWidth = 64f
        canvas?.drawArc(rectForArc, 270f, 90f, false, paint)

        //draw text
        val text = "Hello Canvas"
        paint.style = Paint.Style.FILL
        paint.textSize = 64f
        canvas?.drawText(text, 300f, 1450f, paint)

    }


}