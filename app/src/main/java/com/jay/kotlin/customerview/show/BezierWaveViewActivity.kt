package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.BezierWaveView
import kotlin.random.Random

class BezierWaveViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = BezierWaveView(this)
        return setupContainer(view) {
            view.setData(Random.nextInt(0,100))
        }
    }
}
