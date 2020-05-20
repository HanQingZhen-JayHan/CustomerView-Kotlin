package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.DownloadingView
import com.jay.kotlin.customerview.utils.ScreenUtils

class DownloadingViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = DownloadingView(this)
        return setupContainer(view, ScreenUtils.screenWidth / 2, ScreenUtils.screenWidth / 2) {
            view.showLoading()
        }
    }
}
