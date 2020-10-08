package com.droiddevstar.historyline2


import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

class LocaleManager {
    companion object {
        private var savedLanguage: String? = null

        @Suppress("DEPRECATION")
        fun updateResources(
            context: Context,
            language: String
        ) {
            savedLanguage = language
            val res: Resources = context.resources
            val conf: Configuration = res.configuration
            savedLanguage?.let {
                if (it.isEmpty()) {
                    conf.locale = Locale.getDefault()
                } else {
                    val idx = it.indexOf('-')
                    if (idx != -1) {
                        val split: List<String> = it.split("-")
                        conf.locale = Locale(split[0], split[1].substring(1))
                    } else {
                        conf.locale = Locale(language)
                    }
                }

                res.updateConfiguration(conf, null)
            }
        }

        fun updateResources(context: Context) {
            savedLanguage?.let {
                updateResources(context, it)
            }
        }
    }

}