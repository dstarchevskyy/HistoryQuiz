package com.droiddevstar.historyline2.activities.gdpr


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.droiddevstar.historyline2.ActivityLayout
import com.droiddevstar.historyline2.R
import com.droiddevstar.historyline2.activities.LandingActivity
import com.droiddevstar.historyline2.activities.MainActivity2
import com.droiddevstar.historyline2.activities.gdpr.GdprManager.Companion.getApplicationName
import com.droiddevstar.historyline2.activities.gdpr.GdprManager.Companion.getResultGDPR
import com.droiddevstar.historyline2.activities.gdpr.GdprManager.Companion.saveResultGDPR
import com.droiddevstar.historyline2.startActivity
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ActivityLayout(getLayoutId = R.layout.activity_gdpr)
class GDPRActivity : AppCompatActivity() {

    private var btnYes : AppCompatButton? = null
    private var btnNo : AppCompatButton? = null
    private var tvText : AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            val info = withContext(Dispatchers.Default) {
                return@withContext AdvertisingIdClient
                    .getAdvertisingIdInfo(this@GDPRActivity)
            }

            if (null != info) {
                onInfoReceived(info)
            }
        }
    }

    private fun onInfoReceived(info: AdvertisingIdClient.Info?) {
        if (null != info) {
            if (!info.isLimitAdTrackingEnabled) {
                if (!GdprManager.wasConsentShowing(this)) {
                    initViews()
                    prepareGDPR()
                } else {
                    val resultGDPR = getResultGDPR(this)
                    val intent = MainActivity2.getIntent(this)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = MainActivity2.getIntent(this)
                startActivity(intent)
                finish()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private fun prepareGDPR() {
        val learnMore = getString(R.string.learn_more)
        val mainText = getString(R.string.gdpr_main_text,
            getApplicationName(this))
        val startPosition = mainText.indexOf(learnMore)
        val endPosition = startPosition + learnMore.length
        val spannableMain = SpannableString(mainText)
        spannableMain.setSpan(
            URLSpan("https://www.appodeal.com/privacy-policy"),
            startPosition,
            endPosition,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvText?.movementMethod = LinkMovementMethod.getInstance()
        tvText?.text = spannableMain

        btnYes?.setOnClickListener {
            saveResultGDPR(this@GDPRActivity, true)

            val intent = GDPRResultActivity.getIntent(this@GDPRActivity,
                true)
            startActivity(intent)
            finish()
        }

        val no = getString(R.string.gdpr_disagree).toUpperCase()
        val spannableNo = SpannableString(no)
        spannableNo.setSpan(
            UnderlineSpan(),
            0,
            spannableNo.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        btnNo?.setText(spannableNo, TextView.BufferType.SPANNABLE)
        btnNo?.setOnClickListener {
            saveResultGDPR(this@GDPRActivity, false)

            val intent = GDPRResultActivity
                .getIntent(this@GDPRActivity, false)
            startActivity(intent)

            finish()
        }

        val close = getString(R.string.gdpr_close).toUpperCase()
        val spannableClose = SpannableString(close)
        spannableClose.setSpan(
            UnderlineSpan(),
            0,
            spannableClose.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, GDPRActivity::class.java)
        }
    }

    private fun initViews() {
        tvText = findViewById(R.id.tv_text)
        btnYes = findViewById(R.id.btn_yes)
        btnNo = findViewById(R.id.btn_no)
    }

    override fun onBackPressed() {
        startActivity<LandingActivity>()
        finish()
    }

}