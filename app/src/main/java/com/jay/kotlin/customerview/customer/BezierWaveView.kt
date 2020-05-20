package com.jay.kotlin.customerview.customer

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.jay.kotlin.customerview.utils.ScreenUtils

class BezierWaveView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint
    private var cx: Float
    private var cy: Float
    private var path: Path

    private var moveX: Float
    private var waveColor: Int

    private var circleColor: Int

    private val strokeWidth = 2f
    private var radius: Float
    private var percent: Int

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = strokeWidth
        paint.textSize = ScreenUtils.sp2px(context, 48)
        viewWidth = 0f
        viewHeight = 0f
        cx = 0f
        cy = 0f
        radius = 0f
        moveX = 0f
        path = Path()
        percent = 50

        waveColor = Color.parseColor("#33b5e5")
        circleColor = Color.parseColor("#ffb5e5")

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
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
            viewHeight = viewWidth
        }
        radius = viewWidth * 0.3f
        cx = viewWidth / 2
        cy = viewHeight / 2
        moveX = cx - radius
        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    internal fun makeDst(r: Float): Bitmap {
        val w = 2 * r
        val bm = Bitmap.createBitmap(w.toInt(), w.toInt(), Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        p.color = circleColor
        //relative position
        c.drawCircle(r, r, r, p)
        return bm
    }

    // create a bitmap with a rect, used for the "src" image
    internal fun makeSrc(r: Float, percent: Int, moveX: Float): Bitmap {
        val w = 2 * r
        val bm = Bitmap.createBitmap(w.toInt(), w.toInt(), Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)

        val path = Path()
        val adjustY = r + r - r * percent / 50
        path.reset()
        path.moveTo(0f, adjustY)

        path.cubicTo(moveX, adjustY + r / 2, moveX + r, adjustY - r / 2, r + r, adjustY)
        path.lineTo(r + r, r + r)
        path.lineTo(0f, r + r)
        path.close()

        p.color = waveColor
        c.drawPath(path, p)
        return bm
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        if (percent in 1..99) {

            val circleBitmap = makeDst(radius)
            paint.xfermode = null
            canvas?.drawBitmap(circleBitmap, cx - radius, cy - radius, paint)

            val waveBitmap = makeSrc(radius, percent, moveX - cx + radius)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
            canvas?.drawBitmap(waveBitmap, cx - radius, cy - radius, paint)
        } else {

            if (percent == 0) {
                paint.color = circleColor
            } else {
                paint.color = waveColor
            }
            paint.style = Paint.Style.FILL
            canvas?.drawCircle(cx, cy, radius, paint)
        }

        val text = "$percent%"
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        paint.color = Color.WHITE
        canvas?.drawText(text, cx - bounds.width() / 2, cy + bounds.height() / 2, paint)

    }

    fun setData(percent: Int) {
        val percentAnimation = ValueAnimator.ofInt(0, percent)
        percentAnimation.duration = 1500
        percentAnimation.addUpdateListener {
            this.percent = it.animatedValue as Int
        }

        percentAnimation.interpolator = AccelerateDecelerateInterpolator()
        percentAnimation.start()

        if (percent == 0 || percent == 100) {
            invalidate()
            return
        }
        val animation = ValueAnimator.ofFloat(cx - radius, cx)
        animation.duration = 1500
        animation.addUpdateListener {
            this.moveX = it.animatedValue as Float
            invalidate()
        }

        animation.repeatMode = ValueAnimator.REVERSE
        animation.repeatCount = -1

        animation.interpolator = AccelerateDecelerateInterpolator()
        animation.start()
    }
}