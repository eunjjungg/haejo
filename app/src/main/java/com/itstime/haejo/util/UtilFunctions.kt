package com.itstime.haejo.util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.itstime.haejo.MainActivity

class UtilFunctions {
    fun clearStackAndGoMain(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}