package com.droiddevstar.historyline2


import android.content.Context
import android.content.Intent
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import timber.log.Timber

@Suppress("unused")
class HistorylineApp : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(ActivityCallbacks())

        if (BuildConfig.DEBUG) {
            val tree = Timber.DebugTree()
            Timber.plant(tree)
            Timber.tag("HistoryLine")
        }
    }

    override fun onTerminate() {
        stopService(Intent(this, MusicService::class.java))
        super.onTerminate()
    }

}