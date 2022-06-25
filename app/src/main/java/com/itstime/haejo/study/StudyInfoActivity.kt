package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.R
import com.itstime.haejo.databinding.ActivityStudyInfoBinding
import com.itstime.haejo.study.util.StudyInfoSurveyAdapter
import com.itstime.haejo.study.util.SurveyData

class StudyInfoActivity : AppCompatActivity() {

    lateinit var binding : ActivityStudyInfoBinding

    //recyclerView Survey 용
    private val surveyDataList: MutableList<SurveyData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        testCode()
        setRecyclerSurvey()
    }

    //db에서 받아오는 값으로 data 수정 필요
    private fun setRecyclerSurvey() {
        val surveyAdapter = StudyInfoSurveyAdapter()
        surveyDataList.apply {
            add(SurveyData(1, "첫 번째 질문 내용입니다"))
            add(SurveyData(2, "두 번째 질문 내용입니다"))
            add(SurveyData(3, "세 번째 질문 내용입니다"))

        }

        surveyAdapter.listData = surveyDataList
        binding.recycSurvey.adapter = surveyAdapter
        binding.recycSurvey.layoutManager = LinearLayoutManager(this)
    }

    private fun testCode() {
        binding.tvContent.setText(R.string.customSampleStudyContent)
    }
}