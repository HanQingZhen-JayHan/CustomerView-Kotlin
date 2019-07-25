package com.jay.kotlin.customerview.show

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jay.kotlin.customerview.customer.LineChartView
import com.jay.kotlin.customerview.utils.ScreenUtils
import kotlin.random.Random

class LineCharViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = FrameLayout(this)
        setContentView(container)

        // test
        val view = LineChartView(this)
        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dp2px(this, 300f).toInt())
        container.addView(view, layoutParams)

        val btn = Button(this)
        val btnLayoutParams =
            FrameLayout.LayoutParams(ScreenUtils.dp2px(this, 200f).toInt(), ScreenUtils.dp2px(this, 50f).toInt())
        btnLayoutParams.gravity = Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL
        btnLayoutParams.bottomMargin = ScreenUtils.dp2px(this, 50f).toInt()
        container.addView(btn, btnLayoutParams)
        btn.setOnClickListener({
            view.setData("LineChartView", getData())
        })
        btn.text = "Click"

        view.setData("LineChartView", getData())
    }

    fun getData(): ArrayList<LineChartView.Dot> {
        val data = ArrayList<LineChartView.Dot>()
        for (i in 1..7) {
            val x = i//Random.nextInt(100)
            val y = Random.nextInt(100)
            data.add(LineChartView.Dot(x.toFloat(), y.toFloat(), "($x,$y)"))
            Log.d("LineChartView", "($x,$y)")
        }

        return data
    }
}
