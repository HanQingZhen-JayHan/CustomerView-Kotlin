package com.jay.kotlin.customerview.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.jay.kotlin.customerview.R

abstract class BaseFragment : Fragment() {

    abstract fun showLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(showLayout(), container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //create action bar menu
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
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
    var fileName =
        "blob/master/app/src/main/java/com/jay/kotlin/customerview/geometric/PorterDuffXferMode.kt"
    private var sourceCodeLink =
        "https://github.com/HanQingZhen-JayHan/CustomerView-Kotlin"

    private fun showSourceCode() {
        activity?.let {
            val browserIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$sourceCodeLink/$fileName")
                )
            if (browserIntent.resolveActivity(it.packageManager) != null) {
                startActivity(browserIntent)
            }
        }

    }
}