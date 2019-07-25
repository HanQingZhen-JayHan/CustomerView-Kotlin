package com.jay.kotlin.customerview.show

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jay.kotlin.customerview.customer.WaveView
import com.jay.kotlin.customerview.utils.ScreenUtils

class PopViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = FrameLayout(this)
        setContentView(container)

        // test
        val view = WaveView(this)
        val layoutParams =
            FrameLayout.LayoutParams(ScreenUtils.dp2px(this, 200f).toInt(), ScreenUtils.dp2px(this, 100f).toInt())
        layoutParams.gravity = Gravity.CENTER
        container.addView(view, layoutParams)

        val btn = Button(this)
        val btnLayoutParams =
            FrameLayout.LayoutParams(ScreenUtils.dp2px(this, 200f).toInt(), ScreenUtils.dp2px(this, 50f).toInt())
        btnLayoutParams.gravity = Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL
        btnLayoutParams.bottomMargin = ScreenUtils.dp2px(this, 50f).toInt()
        container.addView(btn, btnLayoutParams)
        btn.setOnClickListener({
        })
        btn.text = "Click"

    }

}
