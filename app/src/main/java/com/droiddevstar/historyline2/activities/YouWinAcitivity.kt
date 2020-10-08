package com.droiddevstar.historyline2.activities


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.droiddevstar.historyline2.ActivityLayout
import com.droiddevstar.historyline2.R
import com.droiddevstar.historyline2.data.CurrentLevelKeeper
import com.droiddevstar.historyline2.data.MAX_LEVEL_NUMBER
import com.droiddevstar.historyline2.startActivity
import kotlinx.android.synthetic.main.activity_you_lose.*
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@ActivityLayout(getLayoutId = R.layout.activity_you_win)
class YouWinActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = findViewById<WebView>(R.id.web_view)
        webView.loadUrl("file:///android_asset/firework.html")

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(5000)
            webView.visibility = View.GONE

            CurrentLevelKeeper.currentLevel.let {
                if (it <= MAX_LEVEL_NUMBER) {
                    resources.getString(R.string.next_level)
                    btn_play_again.text =
                        resources.getString(R.string.next_level) + " " + it + ". " + resources.getString(
                            R.string.play
                        )
                } else {
                    btn_play_again.text = resources.getString(R.string.play_again)
                }

                btn_play_again.setOnClickListener {
                    startActivity<MainActivity2>()
                    finish()
                }
            }
        }
    }

}