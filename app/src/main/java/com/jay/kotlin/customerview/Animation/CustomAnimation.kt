package com.jay.kotlin.customerview.Animation

import android.view.animation.Animation
import android.view.animation.Transformation

class CustomAnimation : Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        t.matrix.setTranslate(Math.sin((interpolatedTime * 50).toDouble()).toFloat() * 20, 0f)

        super.applyTransformation(interpolatedTime, t)
    }
}
