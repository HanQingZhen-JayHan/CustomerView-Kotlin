package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.CirclePercentView
import com.jay.kotlin.customerview.utils.ScreenUtils
import kotlin.random.Random

class CirclePercentViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = CirclePercentView(this)
        return setupContainer(view, ScreenUtils.screenWidth / 2, ScreenUtils.screenWidth / 2) {
            view.setPercent(Random.nextInt(0, 100))
        }
    }
}
