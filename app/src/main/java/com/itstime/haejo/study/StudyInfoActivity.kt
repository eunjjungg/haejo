package com.itstime.haejo.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.R
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostContentDTO
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.ActivityStudyInfoBinding
import com.itstime.haejo.study.util.StudyInfoSurveyAdapter
import com.itstime.haejo.study.util.SurveyData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudyInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityStudyInfoBinding

    lateinit var content : PostContentDTO

    var questionAmount: Int = 0

    //recyclerView Survey 용
    private val surveyDataList: MutableList<SurveyData> = mutableListOf()
    lateinit var surveyAdapter: StudyInfoSurveyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerSurvey()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        getData(intent.getIntExtra("studyId", 0))

        binding.btnNext.setOnClickListener {
            Intent(this, StudyApplyActivity::class.java).apply {
                putExtra("studyId", intent.getIntExtra("studyId", 0))
                putExtra("questionAmount", questionAmount)
                startActivity(this)
            }
        }

    }

    //recyclerView 어댑터만 연결
    private fun setRecyclerSurvey() {
        surveyAdapter = StudyInfoSurveyAdapter()


        surveyAdapter.listData = surveyDataList
        binding.recycSurvey.adapter = surveyAdapter
        binding.recycSurvey.layoutManager = LinearLayoutManager(this)
    }

    //db에서 받아온 값으로 recyclerView adapter에 값 넣기
    private fun getData(studyId: Int) {
        val api = APIS.create()
        api.getPostContent(studyId).enqueue(object : Callback<PostContentDTO> {
            override fun onResponse(
                call: Call<PostContentDTO>,
                response: Response<PostContentDTO>
            ) {
                content = response.body()!!
                setDataToScreen()
            }

            override fun onFailure(call: Call<PostContentDTO>, t: Throwable) {
                Log.d("stdInfo server Error", t.message.toString())
            }

        })
    }

    private fun setDataToScreen() {
        binding.tvTitle.setText(content.title)
        binding.tvContent.setText(content.content)
        binding.tvTagRegion.setText("#${content.region}")
        binding.tvTagIsUntact.setText("#${content.isUntact}")
        binding.tvTagWeek.setText("#${content.week}요일")
        binding.tvTagSector.setText("#${content.category}")

        //person Limit Tag
        if(content.personLimit == 20)
            binding.tvPeopleAmount.setText("#대규모")
        else
            binding.tvPeopleAmount.setText("#${content.personLimit}인 이하")

        //status Tag
        if(content.status == "recruit")
            binding.tvStatus.setText("#모집중")
        else
            binding.tvStatus.setText("#모집 완료")

        surveyDataList.clear()
        questionAmount = content.questions.size
        for(i in 1..content.questions.size)
            surveyDataList.add(SurveyData(i, content.questions[i-1].question!!))
        surveyAdapter.notifyDataSetChanged()


    }
}