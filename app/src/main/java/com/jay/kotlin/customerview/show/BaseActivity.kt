package com.jay.kotlin.customerview.show

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.jay.kotlin.customerview.R


abstract class BaseActivity : AppCompatActivity() {

    private val className = this.javaClass.simpleName
    private lateinit var contentView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = className

        contentView = FrameLayout(this)
        setContentView(contentView)
        contentView.addView(showCustomerView())
    }

    // handle back button
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    //set up container
    fun setupContainer(
        subview: View,
        width: Int = FrameLayout.LayoutParams.MATCH_PARENT,
        height: Int = FrameLayout.LayoutParams.MATCH_PARENT,
        listener: (() -> Unit)?
    ): FrameLayout {
        val container = View.inflate(this, R.layout.container_layout, null) as FrameLayout
        val layoutParams = FrameLayout.LayoutParams(width, height)
        layoutParams.gravity = Gravity.CENTER
        container.addView(subview, layoutParams)

        val btn = container.findViewById<Button>(R.id.btn)
        if (listener == null) {
            btn.visibility = View.GONE
        } else {
            btn.setOnClickListener {
                listener.invoke()
            }
        }

        return container
    }

    //create action bar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //item click
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_link -> {
            showSourceCode()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    //browser
    var subPackage = "customer"
    private var sourceCodeLink =
        "https://github.com/HanQingZhen-JayHan/CustomerView-Kotlin/blob/master/app/src/main/java/"
    private fun showSourceCode() {
        val packageName = this.packageName.replace('.', '/')
        val fileName = className.replace("Activity", "")
        //Log.e("package name", "$packageName/$fileName")
        val browserIntent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("$sourceCodeLink/$packageName/$subPackage/$fileName.kt")
            )
        if (browserIntent.resolveActivity(this.packageManager) != null) {
            startActivity(browserIntent)
        }
    }

    // customer view
    abstract fun showCustomerView(): View
}
