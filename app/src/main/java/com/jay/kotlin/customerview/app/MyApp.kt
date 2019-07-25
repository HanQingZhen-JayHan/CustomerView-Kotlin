package com.jay.kotlin.customerview.app

import android.app.Application

/**
 * my application.
 *
 *
 */

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setInstance(this)

    }

    companion object {
        var appContext: MyApp? = null
            private set

        @Synchronized
        private fun setInstance(app: MyApp) {
            appContext = app
        }
    }


}
