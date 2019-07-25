package com.jay.kotlin.customerview.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.jay.kotlin.customerview.app.MyApp

object ScreenUtils {

    /**
     * phone screen width.
     *
     * @return
     */
    val screenWidth: Int
        get() {
            val windowManager = MyApp.appContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

    /**
     * phone screen height.
     *
     * @return
     */
    val screenHeight: Int
        get() {
            val windowManager = MyApp.appContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }

    /**
     * convert sp to px for text.
     *
     * @param context
     * @param spValue
     * @return
     */

    fun sp2px(context: Context, spValue: Int?): Float {
        val fontScale = context.resources.displayMetrics.density
        return spValue!! * fontScale + 0.5f
    }

    /**
     * convert px to dp.
     *
     * @param pxValue
     * @return
     */

    fun px2dp(context: Context, pxValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxValue / scale + 0.5f
    }

    /**
     * convert dp to px.
     *
     * @param dpValue dp
     * @return px
     */
    fun dp2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }
}
