package com.jay.kotlin.customerview.customer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.utils.CollectionUtil
import com.jay.kotlin.customerview.utils.ScreenUtils

class LineChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var viewWidth: Float
    private var viewHeight: Float

    private var paint: Paint

    private var titleSize: Int
    private var titleColor: Int
    private var title: String = ""

    private var dotRadius: Float
    private var dotColor: Int

    private var lineWidth: Float
    private var lineColor: Int

    private var axisTextSize: Int
    private var axisTextColor: Int

    private var dataList: ArrayList<Dot>

    private var axisXTop: Float
    private var axisXBottom: Float

    private var axisYTop: Float
    private var axisYBottom: Float

    private var axisXLimit: Int
    private var axisYLimit: Int
    private val titleHeight: Float
    private val axisXWidth: Float
    private val axisYWidth: Float


    init {
        paint = Paint()
        val a = context.obtainStyledAttributes(R.styleable.LineChartView)

        titleColor = a.getColor(R.styleable.LineChartView_titleColor, 0xff6950a1.toInt())
        titleSize = a.getDimensionPixelSize(R.styleable.LineChartView_titleSize, 20)

        dotColor = a.getColor(R.styleable.LineChartView_dotColor, 0xff6950a1.toInt())
        dotRadius = a.getDimension(R.styleable.LineChartView_dotRadius, ScreenUtils.dp2px(context, 5f))

        lineColor = a.getColor(R.styleable.LineChartView_lineColor, 0xffafb4db.toInt())
        lineWidth = a.getDimension(R.styleable.LineChartView_lineWith, ScreenUtils.dp2px(context, 2f))


        axisTextColor = a.getColor(R.styleable.LineChartView_axisTextColor, 0xff6950a1.toInt())
        axisTextSize = a.getDimensionPixelSize(R.styleable.LineChartView_axisTextSize, 14)

        a.recycle()

        paint.isAntiAlias = true

        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        titleHeight = ScreenUtils.dp2px(context, 30f)
        axisXWidth = ScreenUtils.dp2px(context, 20f)
        axisYWidth = ScreenUtils.dp2px(context, 20f)

        viewWidth = 0f
        viewHeight = 0f

        dataList = ArrayList<Dot>()
        axisXLimit = 7
        axisYLimit = 5
        axisXTop = 0f
        axisXBottom = 0f
        axisYTop = 0f
        axisYBottom = 0f
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

        // set view size
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //canvas?.drawRect(0f, 0f, viewWidth, viewHeight, paint)
        //draw title
        paint.textSize = ScreenUtils.sp2px(context, titleSize)
        paint.color = titleColor
        paint.style = Paint.Style.FILL
        drawText(canvas, this.title, paint, 0f, 0f, viewWidth.toInt())

        //draw axis


        //draw data
        if (!CollectionUtil.isEmpty(this.dataList)) {

            val xMin = findMinimum(dataList, true)
            val xMax = findMaximum(dataList, true)
            val yMin = findMinimum(dataList, false)
            val yMax = findMaximum(dataList, false)

            val dividerX = (viewWidth - axisYWidth) / axisXLimit
            val dividerY = (viewHeight - axisXWidth - titleHeight) / axisYLimit

            val intervalX = (xMax - xMin) / (axisXLimit - 1)
            val intervalY = (yMax - yMin) / (axisYLimit - 1)

            Log.d("LineChartView", "xMin:$xMin, xMax: $xMax, dividerX:$dividerX, intervalX:$intervalX")
            Log.d("LineChartView", "yMin:$yMin, yMax: $yMax, dividerY:$dividerY, intervalY:$intervalY")

            paint.textSize = ScreenUtils.sp2px(context, axisTextSize)
            var anchorX = 0f
            var anchorY = titleHeight
            //draw axis Y
            var index = 0
            var valueX = xMin
            var valueY = yMax
            for (indexY in 1..axisYLimit) {
                drawText(canvas, "${valueY.toInt()}", paint, anchorX, anchorY, axisXWidth.toInt())
                Log.d("LineChartView", "yAxis:$valueY")
                anchorY += dividerY
                valueY -= intervalY


            }

            //draw axis X
            anchorX = axisYWidth
            anchorY = viewHeight - axisXWidth
            for (indexX in 1..axisXLimit) {
                drawText(canvas, "${valueX.toInt()}", paint, anchorX, anchorY, axisYWidth.toInt())
                Log.d("LineChartView", "xAxis:$valueX")
                anchorX += dividerX
                valueX += intervalX
            }
            //draw path
            val path = Path()
            var isFirst = true

            dataList.forEach {
                anchorX = axisYWidth + axisXWidth / 2 + (it.x - xMin) * dividerX / intervalX
                anchorY = viewHeight - titleHeight - axisXWidth - (it.y - yMin) * dividerY / intervalY - axisYWidth / 2
                if (isFirst) {
                    isFirst = false
                    path.moveTo(anchorX, anchorY)
                } else {
                    path.lineTo(anchorX, anchorY)
                }
            }
            paint.style = Paint.Style.STROKE
            paint.color = lineColor
            paint.strokeWidth = lineWidth
            canvas?.drawPath(path, paint)

            //draw dot
            paint.style = Paint.Style.FILL
            paint.color = dotColor
            dataList.forEach {
                anchorX = axisYWidth + axisXWidth / 2 + (it.x - xMin) * dividerX / intervalX
                anchorY = viewHeight - titleHeight - axisXWidth - (it.y - yMin) * dividerY / intervalY - axisYWidth / 2
                canvas?.drawCircle(anchorX, anchorY, dotRadius, paint)
            }


        } else {
            paint.textSize = ScreenUtils.sp2px(context, titleSize * 2)
            drawText(canvas, "No Data", paint, 0f, (viewHeight - titleHeight) / 2, viewWidth.toInt())
        }


    }


    private fun drawText(canvas: Canvas?, text: String, paint: Paint, x: Float, y: Float, w: Int) {
        if (TextUtils.isEmpty(text)) {
            return
        }
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        //draw base line
        canvas?.drawText(text, x + (w - bounds.width()) / 2, bounds.height() + y, paint)
        //val boundsWidth = bounds.width()
        //Log.d("LineChartView", "bouds_width:$boundsWidth, container_width:$w")

    }

    public fun setData(title: String, dataList: ArrayList<Dot>) {
        this.title = title
        this.dataList = dataList
        invalidate()
    }

    public fun addData(dataList: ArrayList<Dot>) {
        if (this.dataList != null) {
            this.dataList.addAll(dataList)
        } else {
            this.dataList = dataList
        }
        invalidate()
    }

    fun findMinimum(dataList: ArrayList<Dot>, isAxisX: Boolean): Float {
        var target: Float = Float.MAX_VALUE
        dataList.forEach {
            if (isAxisX) {
                if (it.x < target) {
                    target = it.x
                }
            } else {
                if (it.y < target) {
                    target = it.y
                }
            }
        }
        return target
    }

    fun findMaximum(dataList: ArrayList<Dot>, isAxisX: Boolean): Float {
        var target: Float = Float.MIN_VALUE
        dataList.forEach {
            if (isAxisX) {
                if (it.x > target) {
                    target = it.x
                }
            } else {
                if (it.y > target) {
                    target = it.y
                }
            }
        }
        return target
    }

    class Dot(
        var x: Float,
        var y: Float,
        var value: String = "",
        var radius: Float = 0.0f,
        var color: Int = 0
    ) {

    }

}