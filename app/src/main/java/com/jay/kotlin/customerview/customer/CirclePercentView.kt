package com.jay.kotlin.customerview.customer

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.utils.ScreenUtils

class CirclePercentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var paint: Paint
    private var circleColor: Int
    private var percent: Int
    private var indicatorWidth: Float
    private var indicatorColor: Int
    private var textSize: Int

    private var radius: Float
    private var centerX: Float
    private var centerY: Float
    private var viewWidth: Float
    private var viewHeight: Float

    init {
        paint = Paint()
        val a = context.obtainStyledAttributes(R.styleable.CirclePercentView)
        circleColor = a.getColor(R.styleable.CirclePercentView_backgroundColour, 0xff6950a1.toInt())
        indicatorColor = a.getColor(R.styleable.CirclePercentView_indicateColor, 0xffafb4db.toInt())
        indicatorWidth =
            a.getDimension(R.styleable.CirclePercentView_indicateWidth, ScreenUtils.dp2px(getContext(), 20f))
        textSize = a.getDimensionPixelSize(R.styleable.CirclePercentView_textSize, 20)

        a.recycle()

        paint.isAntiAlias = true
        paint.textSize = ScreenUtils.sp2px(context, textSize)

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        percent = 0
        radius = 0f
        centerX = 0f
        centerY = 0f
        viewWidth = 0f
        viewHeight = 0f
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
            radius = (Math.min(widthSize, heightSize) / 2).toFloat()
            centerX = (widthSize / 2).toFloat()
            centerY = (heightSize / 2).toFloat()

            viewWidth = widthSize.toFloat()
            viewHeight = heightSize.toFloat()

        } else if (widthMode == MeasureSpec.EXACTLY) {
            radius = (widthSize / 2).toFloat()
            centerX = (widthSize / 2).toFloat()
            centerY = centerX

            viewWidth = widthSize.toFloat()
            viewHeight = widthSize.toFloat()

        } else if (heightMode == MeasureSpec.EXACTLY) {

            radius = (heightSize / 2).toFloat()
            centerX = (heightSize / 2).toFloat()
            centerY = centerX

            viewWidth = heightSize.toFloat()
            viewHeight = heightSize.toFloat()

        } else {
            radius = ScreenUtils.dp2px(context, 100f)
            centerX = radius
            centerY = radius

            viewWidth = radius * 2
            viewHeight = radius * 2
        }

        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        backgroundColor = Color.WHITE
//        indicatorColor = Color.WHITE
        //background
        paint.color = circleColor
        canvas?.drawCircle(centerX, centerY, radius, paint)

        if (percent > 0) {


            val degree: Float = 3.6f * percent
            //solution1: two arcs
//        //arc
//            paint?.color = indicatorColor!!
//            canvas?.drawArc(
//                (centerX!! - radius!!),
//                (centerY!! - radius!!),
//                (centerX!! + radius!!),
//                (centerY!! + radius!!),
//                270f,
//                degree,
//                false,
//                paint
//            )
//        //small circle
//        paint?.color = backgroundColor!!
//        var r = radius!! - indicatorWidth!!
//        canvas?.drawArc(
//            (centerX!! - r),
//            (centerY!! - r),
//            (centerX!! + r),
//            (centerY!! + r),
//            270f,
//            degree,
//            false,
//            paint
//        )

            //solution 2 use xfermode
//            canvas?.drawBitmap(
//                generateArc(degree.toInt()), (centerX!! - radius!!),
//                (centerY!! - radius!!), paint
//            )

            //solution 3 best
            paint.color = indicatorColor
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = indicatorWidth

            val r = radius - indicatorWidth / 2
            canvas?.drawArc(
                (centerX - r),
                (centerY - r),
                (centerX + r),
                (centerY + r),
                270f,
                degree,
                false,
                paint
            )
        }
        //draw text
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        val text = String.format("%d%%", percent)
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        canvas?.drawText(text, centerX - (bounds.width() / 2), centerY + (bounds.height() / 2), paint)

    }

    fun generateArc(percent: Int): Bitmap {

        val targetBitmap = Bitmap.createBitmap(
            (radius + radius).toInt(),
            (radius + radius).toInt(), Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(targetBitmap)

        val p = Paint()
        p.isAntiAlias = true

        p.color = indicatorColor
        val bigArc = makeArcBitmap(radius + radius, radius + radius, indicatorColor, percent.toFloat())

        val r = radius - indicatorWidth
        val smallArc = makeArcBitmap(r + r, r + r, indicatorColor, percent.toFloat())

        canvas.drawBitmap(bigArc, centerX - radius, centerY - radius, p)
        p.setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
        canvas.drawBitmap(smallArc, centerX - r, centerY - r, p)

        return targetBitmap
    }

    internal fun makeArcBitmap(w: Float, h: Float, color: Int, percent: Float): Bitmap {
        val bm = Bitmap.createBitmap(w.toInt(), h.toInt(), Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = color
        c.drawArc(
            RectF(0f, 0f, w, h), 270f, percent, true, p
        )

        return bm
    }

    fun setPercent(percent: Int) {

        if (percent < 1) {
            this.percent = 0
            invalidate()
        } else {
            var p: Int = percent
            if (percent > 100) {
                p = 100
            }
            val animation = ValueAnimator.ofInt(0, p)
            animation.duration = 2000
            animation.addUpdateListener {
                this.percent = it.animatedValue as Int
                invalidate()
            }

            animation.interpolator = AccelerateDecelerateInterpolator()
            animation.start()
        }
    }
}