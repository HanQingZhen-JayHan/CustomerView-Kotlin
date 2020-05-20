package com.jay.kotlin.customerview.show

import android.graphics.Color
import android.view.View
import com.jay.kotlin.customerview.customer.Compass3DView

class Compass3DViewActivity : BaseActivity() {

    override fun showCustomerView(): View {
        val view = Compass3DView(this)
        view.setBackgroundColor(Color.BLACK)
        return view
    }
}
