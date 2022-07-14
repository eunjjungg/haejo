package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itstime.haejo.databinding.ActivityStudyMakeNotificationBinding
import com.itstime.haejo.roomdb.AppDatabase
import com.itstime.haejo.util.AppSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyMakeNotification : AppCompatActivity() {
    lateinit var binding : ActivityStudyMakeNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyMakeNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromRoomDB()
    }

    private fun getDataFromRoomDB() {
        val db = AppDatabase.getInstance(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            val studyMakeDAO = db!!.studyMakeDao().getStudyMakeEntitiy(AppSetting.prefs.getMemberId().toLong())
            Log.d("room", studyMakeDAO.toString())
        }
    }
}