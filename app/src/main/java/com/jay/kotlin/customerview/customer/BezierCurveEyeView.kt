package com.jay.kotlin.customerview.customer

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.jay.kotlin.customerview.utils.ScreenUtils


class BezierCurveEyeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint
    private var cx: Float
    private var cy: Float

    private val strokeWidth = 2f
    private val eyeStrokeWidth = 40f

    private var path: Path

    private var isDoingAnimation: Boolean
    private var process: Float


    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        viewWidth = 0f
        viewHeight = 0f
        cx = 0f
        cy = 0f

        path = Path()
        isDoingAnimation = false
        process = 100f

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
        cy = viewHeight / 2


        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        path.reset()
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        canvas?.drawRect(0f, 0f, viewWidth, viewHeight, paint)
        if (process < 1) {
            return
        }

        //


        val radius = 0.15f * viewWidth
        val r = radius * 0.7f
        paint.style = Paint.Style.STROKE
        val rectEyeBall = RectF(cx - r, cy - r, cx + r, cy + r)

        paint.color = Color.WHITE

        paint.alpha = (process*255/100).toInt()

        if(process<=33){

            paint.strokeWidth = eyeStrokeWidth * process / 33
            canvas?.drawArc(rectEyeBall, 180f, 10f, false, paint)
            canvas?.drawArc(rectEyeBall, 205f, 25f, false, paint)
        }else{
            paint.strokeWidth = eyeStrokeWidth
            canvas?.drawArc(rectEyeBall, 180f, 10f, false, paint)
            canvas?.drawArc(rectEyeBall, 205f, 25f, false, paint)
            paint.strokeWidth = 10f
            canvas?.drawCircle(cx, cy, radius, paint)
        }



        if (process >= 66) {
            val distance = 2f * radius
            val percent = (process - 66) * 3 / 100
            val newDistance = distance + radius * percent
            val bezierY = (distance + radius) * 0.9f
            path.moveTo(cx - newDistance, cy)
            path.quadTo(cx, cy - bezierY, cx + newDistance, cy)
            path.moveTo(cx + newDistance, cy)
            path.quadTo(cx, cy + bezierY, cx - newDistance, cy)

            canvas?.drawPath(path, paint)
        }

    }


    fun reset() {
        process = 0f
        doAnimation()
    }

    fun doAnimation() {

        val animation = ValueAnimator.ofFloat(0f, 100f)
        animation.duration = 2000
        animation.addUpdateListener {
            this.process = it.animatedValue as Float
            invalidate()
        }
        animation.doOnStart {
            isDoingAnimation = true
        }

        animation.doOnEnd {
            isDoingAnimation = false
        }

        animation.interpolator = LinearInterpolator()
        animation.start()
    }


}

