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
                    R.drawable.ic_launcher_background,
                    "Bezier Curve",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    BezierCurveCircleViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Bezier Curve Circle View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    BezierCurveEyeViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Bezier Curve Eye View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    BezierWaveViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Bezier Wave View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    CirclePercentViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Circle Percent View",
                    "test"
                )
            )


            list.add(
                ItemEntity(
                    Compass3DViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Compass 3D View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    DownloadingViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Downloading View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    ErrorViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Error View",
                    "test"
                )
            )

            list.add(
                ItemEntity(
                    HexagonViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Hexagon View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    LineCharViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Line Chart View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    PanelViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Panel View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    PopViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Popup View",
                    "test"
                )
            )

            list.add(
                ItemEntity(
                    SpringViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Spring View",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    WaveViewActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Wave View",
                    "test"
                )
            )

            list.add(
                ItemEntity(
                    ArcMenuLayoutActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Arc Menu Layout",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    CouponGameLayoutActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Coupon Game Layout",
                    "test"
                )
            )
            list.add(
                ItemEntity(
                    DrawerMenuLayoutActivity::class.java,
                    R.drawable.ic_launcher_background,
                    "Drawer Menu Layout",
                    "test"
                )
            )
            return list
        }

}