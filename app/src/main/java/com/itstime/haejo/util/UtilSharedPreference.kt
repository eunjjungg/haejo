package com.itstime.haejo.util

import android.content.Context
import android.content.SharedPreferences

class UtilSharedPreference(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getEmail(): String {
        return prefs.getString("email", null).toString()
    }

    fun setEmail(str: String) {
        prefs.edit().putString("email", str).apply()
    }

    fun getName(): String {
        return prefs.getString("name", null).toString()
    }

    fun setName(str: String) {
        prefs.edit().putString("name", str).apply()
    }

    fun getAutoLogin(): String {
        return prefs.getString("autoLogin", null).toString()
    }

    fun setAutoLogin(response: String) {
        prefs.edit().putString("autoLogin", response).apply()
    }

}