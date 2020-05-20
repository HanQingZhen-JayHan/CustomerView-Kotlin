package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.SpringView

class SpringViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        return SpringView(this)
    }
}
