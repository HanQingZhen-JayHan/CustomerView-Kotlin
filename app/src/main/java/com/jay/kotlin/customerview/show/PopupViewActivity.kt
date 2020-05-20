package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.PopupView
import com.jay.kotlin.customerview.utils.ScreenUtils

class PopupViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = PopupView(this)
        return setupContainer(
            view, ScreenUtils.dp2px(this, 200f).toInt(),
            ScreenUtils.dp2px(this, 100f).toInt(),
            null
        )
    }

}
