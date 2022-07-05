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

    fun getNickname(): String {
        return prefs.getString("nickname", null).toString()
    }

    fun setNickname(str: String) {
        prefs.edit().putString("nickname", str).apply()
    }

    fun getTmpString0(): String? {
        return prefs.getString("tmpString1", null)
    }

    fun setTmpString0(str: String?) {
        prefs.edit().putString("tmpString1", str).apply()
    }

    fun getTmpString1(): String? {
        return prefs.getString("tmpString3", null)
    }

    fun setTmpString1(str: String?) {
        prefs.edit().putString("tmpString3", str).apply()
    }

    fun getTmpString2(): String? {
        return prefs.getString("tmpString2", null)
    }

    fun setTmpString2(str: String?) {
        prefs.edit().putString("tmpString2", str).apply()
    }



}