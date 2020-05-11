package com.jay.kotlin.customerview.show

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jay.kotlin.customerview.R
import com.jay.kotlin.customerview.demo.Xfermodes

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = getString(R.string.app_name)
        fab.setOnClickListener { view ->
            startActivity(
                Intent(
                    this, Xfermodes::class.java
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_basic -> {
                startActivity(
                    Intent(
                        this, GeometricActivity::class.java
                    )
                )
                return true
            }
            R.id.action_percent -> {
                startActivity(
                    Intent(
                        this, CircleViewActivity::class.java
                    )
                )
                return true
            }
            R.id.action_test -> {
                startActivity(
                    Intent(
                        this, GeometricActivity::class.java
                    )
                )
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
