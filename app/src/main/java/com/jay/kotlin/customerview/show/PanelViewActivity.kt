package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.PanelView
import com.jay.kotlin.customerview.utils.ScreenUtils
import kotlin.random.Random

class PanelViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = PanelView(this)
        return setupContainer(view, ScreenUtils.screenWidth / 2, ScreenUtils.screenWidth / 2) {
            view.setScaleValue(Random.nextInt(0, 12).toFloat())
        }
    }
}
