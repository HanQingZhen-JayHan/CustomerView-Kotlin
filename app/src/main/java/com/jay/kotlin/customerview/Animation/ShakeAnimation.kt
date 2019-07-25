package com.jay.kotlin.customerview.Animation

import android.view.animation.Animation
import android.view.animation.Transformation

class ShakeAnimation : Animation() {

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        t.matrix.setTranslate(Math.sin(interpolatedTime * 100.0).toFloat() * 20f, 0f)
        super.applyTransformation(interpolatedTime, t)
    }
}