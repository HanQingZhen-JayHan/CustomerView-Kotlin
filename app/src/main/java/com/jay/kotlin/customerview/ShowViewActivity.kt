package com.jay.kotlin.customerview

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jay.kotlin.customerview.layout.CouponGameLayout
import com.jay.kotlin.customerview.utils.ScreenUtils
import kotlin.random.Random

class ShowViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = FrameLayout(this)
        setContentView(container)

        //container.setBackgroundColor(Color.parseColor("#C16B44"))
        // test
        val view = CouponGameLayout(this)
        val layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        //val layoutParams = FrameLayout.LayoutParams(ScreenUtils.dp2px(this, 200f).toInt(), ScreenUtils.dp2px(this, 200f).toInt())
        layoutParams.gravity = Gravity.CENTER
        container.addView(view, layoutParams)

        val btn = Button(this)
        val btnLayoutParams =
            FrameLayout.LayoutParams(ScreenUtils.dp2px(this, 200f).toInt(), ScreenUtils.dp2px(this, 50f).toInt())
        btnLayoutParams.gravity = Gravity.BOTTOM + Gravity.CENTER_HORIZONTAL
        btnLayoutParams.bottomMargin = ScreenUtils.dp2px(this, 50f).toInt()
        container.addView(btn, btnLayoutParams)
        btn.setOnClickListener({
            val r = Random.nextInt(10)
            val c = Random.nextInt(20)
            //view.draw(r,c)
        })

        btn.text = "Click"
    }

}
