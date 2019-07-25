package com.jay.kotlin.customerview.show

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jay.kotlin.customerview.geometric.PorterDuffXferModeView

class PorterDuffXferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var container = FrameLayout(this)
        setContentView(container)

        // test
        var view = PorterDuffXferModeView(this)
        container.addView(view)
    }
}
