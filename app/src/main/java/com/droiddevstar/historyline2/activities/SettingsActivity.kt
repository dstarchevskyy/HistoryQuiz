package com.droiddevstar.historyline2.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.droiddevstar.historyline2.R
import timber.log.Timber

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = getString(R.string.settings)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (android.R.id.home == item.itemId) {
            val preferenceManager = getPreferences(Context.MODE_PRIVATE)
            val isMusicOn = preferenceManager.getBoolean("music", true)
            Timber.e("!!!BEFORE FINISH SettingsActivity::isMusicOn: $isMusicOn")
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    internal fun restartActivity() {
        val intent: Intent = intent
        finish()
        startActivity(intent)
    }

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

}
