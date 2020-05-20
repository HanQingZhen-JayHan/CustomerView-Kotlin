package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.geometric.PorterDuffXferMode

class PorterDuffXferModeActivity : BaseActivity() {
    override fun showCustomerView(): View {
        subPackage = "geometric" //for source link
        return PorterDuffXferMode(this)
    }
}
