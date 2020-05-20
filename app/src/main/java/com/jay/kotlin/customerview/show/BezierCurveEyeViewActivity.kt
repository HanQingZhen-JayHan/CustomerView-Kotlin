package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.customer.BezierCurveEyeView

class BezierCurveEyeViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = BezierCurveEyeView(this)
        return setupContainer(view){
            view.reset()
        }
    }
}
