package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itstime.haejo.databinding.ActivityStudyApply3Binding

class StudyApply3Activity : AppCompatActivity() {

    val binding by lazy { ActivityStudyApply3Binding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}