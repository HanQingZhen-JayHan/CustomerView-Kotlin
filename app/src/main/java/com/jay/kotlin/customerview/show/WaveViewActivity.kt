package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.WaveView
import com.jay.kotlin.customerview.utils.ScreenUtils

class WaveViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = WaveView(this)
        return setupContainer(view, ScreenUtils.screenWidth / 2, ScreenUtils.screenWidth / 2, null)
    }
}
