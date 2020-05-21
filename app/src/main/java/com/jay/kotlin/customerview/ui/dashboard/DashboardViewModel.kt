package com.jay.kotlin.customerview.ui.dashboard

import androidx.lifecycle.ViewModel
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.show.*
import com.jay.kotlin.customerview.ui.adapter.ItemEntity

class DashboardViewModel : ViewModel() {

    val data: List<ItemEntity>
        get() {
            val list = ArrayList<ItemEntity>()
            list.add(
                ItemEntity(
                    GeometricActivity::class.java,
                    R.drawable.geometric,
                    "Geometric View",
                    "Basic geometric view"
                )
            )
            list.add(
                ItemEntity(
                    BezierCurveActivity::class.java,
                    R.raw.bezier_curve,
                    "Bezier Curve",
                    "Three points curve"
                )
            )
            list.add(
                ItemEntity(
                    BezierCurveCircleViewActivity::class.java,
                    R.raw.bezier_curve_circle_view,
                    "Bezier Curve Circle View",
                    "Bezier curve + circle"
                )
            )
            list.add(
                ItemEntity(
                    BezierCurveEyeViewActivity::class.java,
                    R.raw.bezier_curve_eye_view,
                    "Bezier Curve Eye View",
                    "Bezier curve + circle"
                )
            )
            list.add(
                ItemEntity(
                    BezierWaveViewActivity::class.java,
                    R.raw.bezier_wave_view,
                    "Bezier Wave View",
                    "Bezier curve + circle"
                )
            )
            list.add(
                ItemEntity(
                    CirclePercentViewActivity::class.java,
                    R.raw.circle_percent_view,
                    "Circle Percent View",
                    "Circle + arc"
                )
            )


            list.add(
                ItemEntity(
                    Compass3DViewActivity::class.java,
                    R.raw.compass_3d_view,
                    "Compass 3D View",
                    "Circle + touch event"
                )
            )
            list.add(
                ItemEntity(
                    DownloadingViewActivity::class.java,
                    R.raw.downloading_view,
                    "Downloading View",
                    "Circle + line + arc"
                )
            )
            list.add(
                ItemEntity(
                    ErrorViewActivity::class.java,
                    R.raw.error_view,
                    "Error View",
                    "Arc + line + translate"
                )
            )

            list.add(
                ItemEntity(
                    HexagonViewActivity::class.java,
                    R.drawable.hexagon,
                    "Hexagon View",
                    "Path + coordinates"
                )
            )
            list.add(
                ItemEntity(
                    LineChartViewActivity::class.java,
                    R.raw.line_chart_view,
                    "Line Chart View",
                    "Path + coordinates"
                )
            )
            list.add(
                ItemEntity(
                    PanelViewActivity::class.java,
                    R.raw.panel_view,
                    "Panel View",
                    "Arc + interpolator + text"
                )
            )
            list.add(
                ItemEntity(
                    PopupViewActivity::class.java,
                    R.drawable.popup,
                    "Popup View",
                    "Path"
                )
            )

            list.add(
                ItemEntity(
                    SpringViewActivity::class.java,
                    R.raw.spring_view,
                    "Spring View",
                    "Touch event + path"
                )
            )
            list.add(
                ItemEntity(
                    WaveViewActivity::class.java,
                    R.drawable.wave,
                    "Wave View",
                    "Arc + rect"
                )
            )

            list.add(
                ItemEntity(
                    ArcMenuLayoutActivity::class.java,
                    R.raw.arc_menu_layout,
                    "Arc Menu Layout",
                    "Layout + rotate + translate"
                )
            )
            list.add(
                ItemEntity(
                    CouponGameLayoutActivity::class.java,
                    R.raw.coupon_game_layout,
                    "Coupon Game Layout",
                    "Layout + touch event"
                )
            )
            list.add(
                ItemEntity(
                    DrawerMenuLayoutActivity::class.java,
                    R.raw.drawer_menu_layout,
                    "Drawer Menu Layout",
                    "Layout + translate"
                )
            )
            return list
        }

}