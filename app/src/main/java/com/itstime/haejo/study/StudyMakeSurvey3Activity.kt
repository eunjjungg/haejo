package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itstime.haejo.databinding.ActivityStudyMakeSurvey3Binding

class StudyMakeSurvey3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var binding : ActivityStudyMakeSurvey3Binding

        super.onCreate(savedInstanceState)
        binding = ActivityStudyMakeSurvey3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            // 데이터 백엔드로 올리는 작업
        }
    }
}