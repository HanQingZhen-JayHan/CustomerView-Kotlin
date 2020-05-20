package com.jay.kotlin.customerview.show

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.jay.kotlin.customerview.customer.LineChartView
import com.jay.kotlin.customerview.utils.ScreenUtils
import kotlin.random.Random

class LineChartViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = LineChartView(this)
        view.setData("LineChartView", getData())

        return setupContainer(
            view,
            FrameLayout.LayoutParams.MATCH_PARENT,
            ScreenUtils.dp2px(this, 300f).toInt()
        ) {
            view.setData("LineChartView", getData())
        }
    }

    private fun getData(): ArrayList<LineChartView.Dot> {
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
