package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.ErrorView
import com.jay.kotlin.customerview.utils.ScreenUtils

class ErrorViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = ErrorView(this)
        return setupContainer(view, ScreenUtils.screenWidth / 2, ScreenUtils.screenWidth / 2) {
            view.showError()
        }
    }
}
