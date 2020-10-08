package com.droiddevstar.historyline2.fragments


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.preference.*
import com.droiddevstar.historyline2.LocaleManager
import com.droiddevstar.historyline2.MusicService
import com.droiddevstar.historyline2.R
import com.droiddevstar.historyline2.activities.SettingsActivity
import timber.log.Timber

class SettingsFragment : PreferenceFragmentCompat() {

    private var languagePref : DropDownPreference? = null
    private var musicPref : SwitchPreferenceCompat? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?,
                                     rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        languagePref = findPreference("language")
        musicPref = findPreference("music")
        val selectedLanguageValue = languagePref?.value
        if (null == selectedLanguageValue) {
            selectDefaultLanguage()
        }

        val isMusicOn = preferenceManager
            .sharedPreferences
            .getBoolean("music", true)
        Timber.e("!!!isMusicOn: $isMusicOn")
        musicPref?.isChecked = isMusicOn

        languagePref?.onPreferenceChangeListener = getOnLanguagePrefChangeListener()
        musicPref?.onPreferenceChangeListener = getOnMusicPrefChangeListener()
    }

    private fun getOnLanguagePrefChangeListener() = Preference.OnPreferenceChangeListener {
            _, newValue ->
        context?.let{
            if (getCurrentLocaleLanguage(it) != newValue
                && newValue is String) {
                LocaleManager.updateResources(it, newValue)
                (activity as? SettingsActivity)?.restartActivity()
            }
        }

        true
    }

    private fun getOnMusicPrefChangeListener() = Preference.OnPreferenceChangeListener {
        _, newValue ->
        context?.let {
            val musicServiceIntent = Intent(it, MusicService::class.java)
            if (true == newValue) {
                it.startService(musicServiceIntent)
            } else {
                it.stopService(musicServiceIntent)
            }
        }

        true
    }

    private fun selectDefaultLanguage() {
        context?.let { context ->
            val currentLanguage = getCurrentLocaleLanguage(context)

            languagePref?.let {
                if (EN == currentLanguage) {
                    it.setValueIndex(0)
                } else {
                    it.setValueIndex(1)
                }
            }
        }
    }

    private fun getCurrentLocaleLanguage(context: Context): String? {
        val configuration = context.resources.configuration

        @Suppress("DEPRECATION")
        val currentLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales.get(0)
        } else {
            configuration.locale
        }

        return currentLocale?.language
    }
}

const val EN = "en"