package com.nsnik.nrs.jaron.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nsnik.nrs.jaron.BuildConfig
import com.nsnik.nrs.jaron.MyApplication

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (BuildConfig.DEBUG) MyApplication.getRefWatcher(this)?.watch(this)
    }
}