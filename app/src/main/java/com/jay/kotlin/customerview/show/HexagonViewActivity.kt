package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.HexagonView

class HexagonViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        return HexagonView(this)
    }
}
