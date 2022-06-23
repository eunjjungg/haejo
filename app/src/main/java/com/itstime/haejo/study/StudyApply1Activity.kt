package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.R
import com.itstime.haejo.databinding.ActivityStudyApply1Binding
import com.itstime.haejo.study.util.Item
import com.itstime.haejo.study.util.StudyApplyAdapter
import com.itstime.haejo.study.util.SurveyData

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