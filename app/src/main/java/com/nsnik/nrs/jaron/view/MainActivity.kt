package com.nsnik.nrs.jaron.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nsnik.nrs.jaron.BuildConfig
import com.nsnik.nrs.jaron.MyApplication
import com.nsnik.nrs.jaron.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }

    private fun initialize() {
        setSupportActionBar(mainToolbar)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (BuildConfig.DEBUG) MyApplication.getRefWatcher(this)?.watch(this)
    }

}
