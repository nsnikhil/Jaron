package com.nsnik.nrs.jaron.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.nsnik.nrs.jaron.BuildConfig
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.R
import com.nsnik.nrs.jaron.util.ApplicationUtility.Companion.getCurrentMonth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        title = getCurrentMonth()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuItemAbout -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initialize() {
        setSupportActionBar(mainToolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (BuildConfig.DEBUG) MyApplication.getRefWatcher(this)?.watch(this)
    }

}
