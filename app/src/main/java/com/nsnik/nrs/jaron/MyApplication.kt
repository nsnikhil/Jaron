package com.nsnik.nrs.jaron

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.appcompat.app.AppCompatDelegate
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import timber.log.Timber

class MyApplication : Application() {

    companion object {

        init {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        fun getRefWatcher(context: Context): RefWatcher? {
            val application = context.applicationContext as MyApplication
            return application.refWatcher
        }

    }

    private var refWatcher: RefWatcher? = null

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) return
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
            refWatcher = LeakCanary.install(this)
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        }
        moduleSetter()
    }

    private fun moduleSetter() {

    }

}