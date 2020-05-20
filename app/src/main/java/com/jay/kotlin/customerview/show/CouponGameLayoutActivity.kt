package com.jay.kotlin.customerview.show

import android.view.View
import com.jay.kotlin.customerview.R

class CouponGameLayoutActivity : BaseActivity() {

    override fun showCustomerView(): View {
        subPackage = "layout" //for source link
        return View.inflate(this, R.layout.layout_coupon_game, null)
    }
}
