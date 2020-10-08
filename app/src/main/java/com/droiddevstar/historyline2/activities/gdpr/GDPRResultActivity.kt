package com.droiddevstar.historyline2.activities.gdpr


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.UnderlineSpan
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.droiddevstar.historyline2.ActivityLayout
import com.droiddevstar.historyline2.R
import com.droiddevstar.historyline2.activities.LandingActivity
import com.droiddevstar.historyline2.startActivity


@ActivityLayout(getLayoutId = R.layout.activity_gdpr_result)
class GDPRResultActivity : AppCompatActivity() {

    private var tvText: AppCompatTextView? = null
    private var llButtonClose: LinearLayoutCompat? = null
    private var tvClose: AppCompatTextView? = null
    private var resultGDPR: Boolean = false

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gdpr_result)
        resultGDPR = intent.getBooleanExtra(RESULT_GDPR, false)

        initViews()
        prepareResult()
    }

    private fun initViews() {
        tvText = findViewById(R.id.tv_text)
        llButtonClose = findViewById(R.id.ll_button_close)
        tvClose = findViewById(R.id.tv_close)
    }

    private fun prepareResult() {
        tvText!!.movementMethod = LinkMovementMethod.getInstance()
        if (resultGDPR) {
            tvText!!.text = getString(R.string.gdpr_agree_text)
        } else {
            tvText!!.text = getString(R.string.gdpr_disagree_text)
        }

        val close = getString(R.string.gdpr_close)
        val spannableClose = SpannableString(close)
        spannableClose.setSpan(UnderlineSpan(),
            0,
            spannableClose.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvClose!!.text = spannableClose
        llButtonClose!!.setOnClickListener {
            startActivity<LandingActivity>()
            finish()
        }
    }

    companion object {
        private const val RESULT_GDPR = "result_gdpr"

        fun getIntent(context: Context, resultGDPR: Boolean): Intent {
            val intent = Intent(context, GDPRResultActivity::class.java)
            intent.putExtra(RESULT_GDPR, resultGDPR)

            return intent
        }
    }

    override fun onBackPressed() {
        startActivity<LandingActivity>()
        finish()
    }

}