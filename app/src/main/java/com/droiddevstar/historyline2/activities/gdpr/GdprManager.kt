package com.droiddevstar.historyline2.activities.gdpr


import android.content.Context

class GdprManager {

    companion object {
        private const val RESULT_GDPR = "result_gdpr"
        private const val APPODEAL = "appodeal"

        fun getApplicationName(context: Context): String{
            val applicationInfo = context.applicationInfo
            val stringId = applicationInfo.labelRes
            return if (stringId == 0) {
                applicationInfo.nonLocalizedLabel.toString()
            } else {
                context.getString(stringId)
            }
        }

        fun wasConsentShowing(context: Context): Boolean {
            val sharedPreferences = context
                .getSharedPreferences(APPODEAL,
                    Context.MODE_PRIVATE)
            return sharedPreferences.contains(RESULT_GDPR)
        }

        fun getResultGDPR(context: Context): Boolean {
            val sharedPreferences = context
                .getSharedPreferences(APPODEAL, Context.MODE_PRIVATE)
            return sharedPreferences
                .getBoolean(RESULT_GDPR, false)
        }

        fun saveResultGDPR(context: Context, resultGDPR: Boolean) {
            val sharedPreferences = context
                .getSharedPreferences(APPODEAL, Context.MODE_PRIVATE)
            sharedPreferences.edit()
                .putBoolean(RESULT_GDPR, resultGDPR)
                .apply()
        }
    }

}