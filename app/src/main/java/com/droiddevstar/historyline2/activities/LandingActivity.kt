package com.droiddevstar.historyline2.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.droiddevstar.historyline2.*
import hotchemi.android.rate.AppRate
import hotchemi.android.rate.StoreType
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_landing.*
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

@ActivityLayout(getLayoutId = R.layout.activity_landing)
class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRateUsDialog()
        btn_start.setOnClickListener {
            startActivity<MainActivity2>()
            finish()
        }

        btn_settings.setOnClickListener {
            startActivityForResult(SettingsActivity.getIntent(this), 0)
        }

        btn_share.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = getString(R.string.join_and_learn_dates_of_history_with_dates_of_history_app)
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)))
        }

        btn_rate.setOnClickListener {
            AppRate.with(this).showRateDialog(this)
        }

        btn_exit.setOnClickListener {
            stopService(Intent(this, MusicService::class.java))
            finish()
        }

        btn_search.setOnClickListener {
            search()
        }

//        val isMusicOn = getPreferences(Context.MODE_PRIVATE)
//            .getBoolean("music", true)
//        Timber.e("!!!LandingActivity::isMusicOn: $isMusicOn")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isMusicOn = sharedPreferences.getBoolean("music", true)
        Timber.e("!!!prefMusic: $isMusicOn")



        if (isMusicOn) {
            startService(Intent(this, MusicService::class.java))
        }
    }

    private fun search() {
        val cx = "008053821111467243454:apwt53ebkd6"
        val urlString =
            "https://www.googleapis.com/customsearch/v1?q="+"test"+"&key="+
                    GOOGLE_SEARCH_API_KET + "&cx=" + cx + "&alt=json"
        var url: URL? = null
        try {
            url = URL(urlString)
        } catch (e: MalformedURLException) {
            Timber.e( "ERROR converting String to URL " + e.toString())
        }
        Timber.e("Url = $urlString")


        // start AsyncTask


        // start AsyncTask
        val searchTask = googleSearchAsyncTask(url!!)

    }

    private fun googleSearchAsyncTask(url: URL) {
        Timber.e( "AsyncTask - doInBackground, url=$url")

        // Http connection

        // Http connection
        var conn: HttpURLConnection? = null
        try {
            conn = url.openConnection() as HttpURLConnection
        } catch (e: IOException) {
            Timber.e("Http connection ERROR " + e.toString())
        }

        Single.fromCallable {
            Timber.e("!!!${conn?.getResponseCode()}")

            val rd = BufferedReader(InputStreamReader(conn!!.inputStream))
            val sb = StringBuilder()
            var line: String

            val allText = rd.use(BufferedReader::readText)
            Timber.e("!!!%s", allText)
            rd.close()

            conn?.disconnect()

//            val result = sb.toString()
//
//            Timber.e("result=$result")

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.e("!!!SUCCESS")
            }, {Timber.e(it)})
//

    }

    private fun configureRateUsDialog() {
        AppRate.with(this)
            .setInstallDays(0) // default 10, 0 means install day.
            .setLaunchTimes(3) // default 10
            .setRemindInterval(2) // default 1
            .setShowLaterButton(true) // default true
            .setDebug(false) // default false
            .setStoreType(StoreType.GOOGLEPLAY) //default is Google, other option is Amazon
            .setShowLaterButton(true) // default true.
            .setCancelable(false) // default false.
            .setTitle(R.string.rate_dialog_title)
            .setTextLater(R.string.rate_dialog_cancel)
            .setTextNever(R.string.rate_dialog_no)
            .setTextRateNow(R.string.rate_dialog_ok)
            .monitor()
    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LocaleManager.updateResources(this)
        restartActivity()
    }

    private fun restartActivity() {
        val intent: Intent = intent
        finish()
        startActivity(intent)
    }

}