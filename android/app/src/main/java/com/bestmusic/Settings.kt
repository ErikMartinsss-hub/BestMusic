package com.bestmusic

import android.content.Context
import android.content.SharedPreferences

object Settings {
    private const val PREFS = "bestmusic_prefs"
    const val DEFAULT_BASE_URL = "http://10.0.2.2:8000"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun baseUrl(context: Context): String =
        prefs(context).getString("base_url", null) ?: DEFAULT_BASE_URL

    fun setBaseUrl(context: Context, url: String) {
        prefs(context).edit().putString("base_url", url.trim().trimEnd('/')).apply()
    }
}