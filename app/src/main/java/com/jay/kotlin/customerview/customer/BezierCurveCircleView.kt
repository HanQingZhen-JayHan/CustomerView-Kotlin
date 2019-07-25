package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.jay.kotlin.customerview.utils.ScreenUtils


class BezierCurveCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint
    private var cx: Float
    private var cy: Float


    private var path: Path

    private var assistanceX: Float
    private var assistanceY: Float

    private val radius = 50f
    private val maxDistance = 150f

    private var distance: Float
    private var originalY: Float

    private var r: Float


    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        viewWidth = 0f
        viewHeight = 0f
        cx = 0f
        cy = 0f

        paint.strokeWidth = 10f
        paint.color = Color.RED
        paint.style = Paint.Style.FILL

        path = Path()

        assistanceX = 0f
        assistanceY = 0f

        originalY = 0f

        distance = 0f
        r = 0f

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

        //start point
        cx = viewWidth / 2
        cy = viewHeight / 4

        assistanceX = cx
        assistanceY = cy

        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        path.reset()
        r = radius
        if (distance > 0f) {
            if (distance <= maxDistance) {
                r = radius * (maxDistance - distance) / maxDistance
                path.moveTo(cx - r, cy)
                path.quadTo(cx, cy + distance * 0.5f, cx - radius, cy + distance)
                path.lineTo(cx + radius, cy + distance)
                path.quadTo(cx, cy + distance * 0.5f, cx + r, cy)
                path.close()
                canvas?.drawPath(path, paint)
                canvas?.drawCircle(cx, cy + distance, radius, paint)
            }else{
                r = 0f
                canvas?.drawCircle(cx, cy + maxDistance, radius, paint)
            }
        }
        Log.d("beizer curve", "distance: $distance, r:$r")
        if(r>1) {
            canvas?.drawCircle(cx, cy, r, paint)
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                originalY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.y > originalY) {
                    distance = event.y - originalY
                } else {
                    distance = 0f
                }

                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                if (event.y - originalY > maxDistance) {
                    cy += maxDistance
                }
                distance = 0f
                invalidate()
            }
        }
        return true

    }

    fun reset(){
        cy = viewHeight/4
        invalidate()
    }


}

