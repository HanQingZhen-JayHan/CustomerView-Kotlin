package com.jay.kotlin.customerview.show

import android.util.Log
import android.view.View
import com.jay.kotlin.customerview.customer.CirclePercentView
import com.jay.kotlin.customerview.utils.ScreenUtils
import kotlin.random.Random

class CircleViewActivity : BaseActivity() {


    override fun showCustomerView(): View {
        val view = CirclePercentView(this)
        view.setPercent(100)

        return setupContainer(
            view,
            ScreenUtils.dp2px(this, 200f).toInt(),
            ScreenUtils.dp2px(this, 200f).toInt()
        ) {
            val p = Random.nextInt(0, 100)
            view.setPercent(p)
            Log.d("CircleViewActivity", "percent: " + p)
        }
    }
}
