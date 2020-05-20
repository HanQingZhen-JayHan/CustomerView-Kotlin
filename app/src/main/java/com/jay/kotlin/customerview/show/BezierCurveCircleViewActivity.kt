package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.BezierCurveCircleView

class BezierCurveCircleViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        return BezierCurveCircleView(this)
    }
}
