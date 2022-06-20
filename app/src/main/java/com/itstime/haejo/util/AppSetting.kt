package com.itstime.haejo.util

import android.app.Application

class AppSetting  : Application() {
    companion object {
        lateinit var prefs: UtilSharedPreference
    }

    override fun onCreate() {
        prefs = UtilSharedPreference(applicationContext)
        super.onCreate()
    }
}
