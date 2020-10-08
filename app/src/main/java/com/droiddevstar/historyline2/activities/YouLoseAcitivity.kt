package com.droiddevstar.historyline2.activities


import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.droiddevstar.historyline2.databinding.ActivityYouLoseBinding
import com.droiddevstar.historyline2.startActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class YouLoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYouLoseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYouLoseBinding.inflate(layoutInflater)
        binding.let {
            setContentView(it.rootView)
            it.btnPlayAgain.setOnClickListener {
                startActivity<MainActivity2>()
            }

            configureWebView()
        }
    }

    private fun configureWebView() {
        binding.webView.let {
            it.loadUrl("file:///android_asset/jenga.html")
            GlobalScope.launch(context = Dispatchers.Main) {
                delay(5000)
                it.visibility = GONE
            }
        }
    }

}