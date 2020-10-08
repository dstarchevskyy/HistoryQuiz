package com.droiddevstar.historyline2


import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.preference.PreferenceManager
import com.appodeal.ads.Appodeal
import com.droiddevstar.historyline2.activities.gdpr.GdprManager

class ActivityCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity,
                                   savedInstanceState: Bundle?) {
        if (activity.javaClass.isAnnotationPresent(ActivityLayout::class.java)) {
            activity.setContentView(layoutId(activity))
            val gdprResult = GdprManager.getResultGDPR(activity)
            Appodeal.initialize(activity,
                APPODEAL_KEY,
                Appodeal.INTERSTITIAL or Appodeal.BANNER,
                gdprResult)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        activity.let{
            it.stopService(Intent(it, MusicService::class.java))
        }
    }

    override fun onActivityResumed(activity: Activity) {
        activity.let{
            Appodeal.onResume(it, Appodeal.BANNER)
            Appodeal.show(it, Appodeal.BANNER)

            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(it)
            val isMusicOn = sharedPreferences.getBoolean("music", true)
            if (isMusicOn) {
                it.startService(Intent(it, MusicService::class.java))
            }
        }
    }

    override fun onActivityStarted(activity: Activity) { }

    override fun onActivityDestroyed(activity: Activity) { }

    override fun onActivitySaveInstanceState(p0: Activity,
                                             p1: Bundle) { }

    override fun onActivityStopped(activity: Activity) { }

    @LayoutRes
    private fun  layoutId(activity : Activity) : Int {
        val annotation = activity.javaClass.annotations.find { it is ActivityLayout } as? ActivityLayout
        return annotation?.getLayoutId ?: 0
    }

}
