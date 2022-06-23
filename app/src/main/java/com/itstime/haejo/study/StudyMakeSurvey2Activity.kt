package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itstime.haejo.databinding.ActivityStudyMakeSurvey2Binding

class StudyMakeSurvey2Activity : AppCompatActivity() {

    lateinit var binding : ActivityStudyMakeSurvey2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyMakeSurvey2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            // 데이터 백엔드로 올리는 작업
        }
    }
}