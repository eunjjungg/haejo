package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itstime.haejo.databinding.ActivityStudyApply1Binding

class StudyApply1Activity : AppCompatActivity() {
    val binding by lazy { ActivityStudyApply1Binding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}