package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.jay.kotlin.customerview.utils.ScreenUtils

class Compass3DView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var viewWidth: Float
    private var viewHeight: Float
    private var paint: Paint
    private var path: Path
    private var cx: Float
    private var cy: Float
    private var radius: Float


    private val strokeWidth = 2f

    private val colors: Array<Int>

    private val camera: Camera
    private val mMatrix: Matrix
    private var rotateX: Float
    private var rotateY: Float


    private val MAX_ROTATE_DEGREE = 20
    private val scaleLength = 20f

    private var beta: Double
    private var touchX: Float
    private var touchY: Float

    private val smallBallRadius = 10f
    private val centerBallRadius = 25f


    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.textSize = ScreenUtils.sp2px(context, 32)
        path = Path()
        viewWidth = 0f
        viewHeight = 0f
        cx = 0f
        cy = 0f

        radius = 0f

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
        paint.color = colors[3]

        camera = Camera()
        mMatrix = Matrix()

        rotateX = 0f
        rotateY = 0f

        beta = 0.0
        touchX = 0f
        touchY = 0f
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

        cx = viewWidth / 2
        cy = viewHeight / 2
        radius = cx * 0.8f
        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //
        canvas?.rotate(beta.toFloat(), cx, cy)
        beta = Math.atan(((touchX - cx) / (cy - touchY)).toDouble())
        beta = Math.toDegrees(beta)

        Log.d("3d compass", "beta:$beta")
        if (touchY > cy) {
            beta += 180
        }


        rotateCanvas(canvas)
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas?.drawText("N", cx - 35f, cy - radius - 30f, paint);

        drawScale(canvas)
        drawCircle(canvas)

        drawPath(canvas)


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchX = event?.x!!
        touchY = event?.y!!
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                handleRotateDegree(event)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                rotateX = 0f
                rotateY = 0f
                invalidate()
            }
            MotionEvent.ACTION_DOWN -> {
                handleRotateDegree(event)
                invalidate()
            }
        }
        return true
    }

    fun handleRotateDegree(event: MotionEvent) {
        val dx = (event.x - cx) / viewWidth
        val dy = (event.y - cy) / viewWidth
        var percentX = dx
        if (dx > 1) {
            percentX = 1f
        } else if (dx < -1) {
            percentX = -1f
        }

        var percentY = dy
        if (dy > 1) {
            percentY = 1f
        } else if (dy < -1) {
            percentY = -1f
        }
        rotateX = -percentY * MAX_ROTATE_DEGREE
        rotateY = percentX * MAX_ROTATE_DEGREE
    }

    fun rotateCanvas(canvas: Canvas?) {
        mMatrix.reset()
        camera.save()

        camera.rotateX(rotateX)
        camera.rotateY(rotateY)
        camera.getMatrix(mMatrix)
        camera.restore()
        mMatrix.preTranslate(-cx, -cy)
        mMatrix.postTranslate(cx, cy)
        canvas?.concat(mMatrix)
    }

    fun drawScale(canvas: Canvas?) {
        canvas?.save()
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        for (i in 1..120) {
            paint.alpha = 255 - 150 * i / 120
            canvas?.drawLine(cx, cy - radius, cx, cy - radius + scaleLength, paint)
            canvas?.rotate(3f, cx, cy)
        }
        canvas?.restore()
    }

    fun drawCircle(canvas: Canvas?) {
        paint.alpha = 255
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas?.drawCircle(cx, cy - radius + scaleLength + smallBallRadius, smallBallRadius, paint)
    }


    fun drawPath(canvas: Canvas?) {
        val width = 40f
        path.reset()
        path.moveTo(cx, cy - radius + scaleLength + smallBallRadius * 2);
        path.lineTo(cx - width, cy);
        path.lineTo(cx, cy + radius - scaleLength - smallBallRadius * 2);
        path.lineTo(cx + width, cy);
        path.close();
        canvas?.drawPath(path, paint);
        paint.setColor(Color.parseColor("#55227BAE"));
        canvas?.drawCircle(cx, cy, centerBallRadius, paint);
    }
}