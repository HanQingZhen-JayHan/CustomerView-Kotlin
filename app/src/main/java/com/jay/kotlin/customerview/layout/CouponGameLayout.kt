package com.jay.kotlin.customerview.layout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.utils.ScreenUtils

class CouponGameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val textView: TextView
    private val image: ImageView
    private val subLayoutParams: FrameLayout.LayoutParams
    private var originalX: Float
    private var originalY: Float
    private var isDoingAnimation = false
    private var animation: ValueAnimator? = null
    private val container: FrameLayout
    private var count = 0
    private var canCount = false

    private val MAX_HEIGHT = ScreenUtils.dp2px(context, 150f)
    private val layoutParams: LayoutParams

    init {
        orientation = VERTICAL

        container = FrameLayout(context)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(container, layoutParams)

        image = ImageView(context)
        image.setImageResource(R.drawable.breathtaking)
        image.scaleType = ImageView.ScaleType.CENTER
        subLayoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0)
        container.addView(image, subLayoutParams)

        textView = TextView(context)
        textView.text = "Hello"
        textView.textSize = ScreenUtils.sp2px(context, 32)
        textView.setTextColor(Color.WHITE)
        val typeFace = Typeface.createFromAsset(context.assets, "font/sweet_hipster.ttf")
        textView.typeface = typeFace
        textView.gravity = Gravity.CENTER
        container.addView(textView, subLayoutParams)

        originalX = 0f
        originalY = 0f

        val btn = Button(context)
        btn.text = "hello btn"
        addView(btn)

    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isDoingAnimation) {
            return super.onTouchEvent(event)
        }
        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                originalX = event.x
                originalY = event.y

                textView.text = "Hello $count"
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - originalX
                val dy = event.y - originalY

                //pull action
                if (dy > 0 && dy < MAX_HEIGHT) {
                    subLayoutParams.height = dy.toInt()
                    container.requestLayout()
                    val h = subLayoutParams.height
                    Log.d("coupon", "ACTION_MOVE:$h")
                    canCount = true
                } else if (dy > 0 && canCount) {
                    canCount = false
                    count++
                    textView.text = "Hello $count"
                }

            }

            MotionEvent.ACTION_UP -> {
                var moveY = event.y - originalY
                if (moveY > MAX_HEIGHT) {
                    moveY = MAX_HEIGHT
                }
                count = 0
                doAnimation(moveY)
                val h = moveY
                Log.d("coupon", "ACTION_UP:$h")
            }
        }

        return true
    }

    fun doAnimation(maxValue: Float) {
        if (maxValue < 1) {
            return
        }
        if (animation == null) {
            animation = ValueAnimator.ofFloat(maxValue, 0f)
            animation?.duration = 200
            animation?.addUpdateListener {
                val h = it.animatedValue as Float
                subLayoutParams.height = h.toInt()
                container.requestLayout()
                Log.d("coupon", "Animation:$h")
            }
            animation?.doOnStart {
                isDoingAnimation = true
            }

            animation?.doOnEnd {
                isDoingAnimation = false
            }

            animation?.interpolator = LinearInterpolator()
        }
        animation?.setFloatValues(maxValue, 0f)
        animation?.start()
    }
}