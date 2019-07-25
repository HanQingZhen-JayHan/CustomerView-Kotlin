package com.jay.kotlin.customerview.customer

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.utils.ScreenUtils


class SpringView @JvmOverloads constructor(
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

    private var isDoingAnimation: Boolean
    private var progress: Int

    private var pic: Bitmap

    private var actionY: Float


    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        viewWidth = 0f
        viewHeight = 0f
        cx = 0f
        cy = 0f

        paint.strokeWidth = 10f
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE

        path = Path()

        assistanceX = 0f
        assistanceY = 0f

        isDoingAnimation = false
        progress = 0

        pic = getBitmapFromDrawable(context, R.mipmap.ic_launcher)

        actionY = 0f
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
        actionY = cy

        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        path.reset()

        path.moveTo(0.2f * viewWidth, cy)

        //assistance point ,and end point
        path.quadTo(cx, actionY, 0.8f * viewWidth, cy)

        paint.color = Color.YELLOW
        canvas?.drawPath(path, paint)

        paint.color = Color.GRAY
        canvas?.drawCircle(0.2f * viewWidth, cy, 7f, paint)
        canvas?.drawCircle(0.8f * viewWidth, cy, 7f, paint)

        var picY: Float
        if (isDoingAnimation) {
            picY = (actionY - cy) * (cy + assistanceY) * 0.5f / (assistanceY - cy) - pic.height
        } else {
            picY = (actionY + cy) * 0.5f - pic.height
        }

        canvas?.drawBitmap(pic, cx - pic.width / 2, picY, paint)


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                assistanceX = event.x
                assistanceY = event.y
                actionY = assistanceY
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                assistanceX = event.x
                assistanceY = event.y
                actionY = assistanceY
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                assistanceX = event.x
                assistanceY = event.y
                doAnimation(assistanceY)

            }
        }
        return true

    }

    fun getBitmapFromDrawable(c: Context, resId: Int): Bitmap {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val drawable = c.getDrawable(resId)
            val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } else {
            return BitmapFactory.decodeResource(c.resources, resId)
        }

    }

    fun doAnimation(maxValue: Float) {

        val animation = ValueAnimator.ofFloat(maxValue, cy)
        animation.duration = 500
        animation.addUpdateListener {
            this.actionY = it.animatedValue as Float
            invalidate()
        }
        animation.doOnStart {
            isDoingAnimation = true
        }

        animation.doOnEnd {
            isDoingAnimation = false
        }

        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.start()
    }

}

