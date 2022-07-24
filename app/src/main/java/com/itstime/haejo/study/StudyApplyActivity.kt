package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.api.*
import com.itstime.haejo.databinding.ActivityStudyApplyBinding
import com.itstime.haejo.study.util.AdapterStudyApplySurvey
import com.itstime.haejo.study.util.SurveyData
import com.itstime.haejo.util.AppSetting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudyApplyActivity : AppCompatActivity() {
    lateinit var binding : ActivityStudyApplyBinding

    private val surveyDataList: MutableList<SurveyData> = mutableListOf()
    lateinit var postApplicationDTO: PostApplicationDTO
    private val answerDTOList: MutableList<AnswerDTO> = mutableListOf()
    lateinit var content : PostContentDTO
    lateinit var surveyAdapter: AdapterStudyApplySurvey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        //recyclerview에서 작성하기 전에 먼저 null로 초기화 작업 필요
        AppSetting.prefs.setTmpString0(null)
        AppSetting.prefs.setTmpString1(null)
        AppSetting.prefs.setTmpString2(null)

        setRecyclerSurvey(intent.getIntExtra("questionAmount", 0))
        getDataFromDB(intent.getIntExtra("studyId", 0))

        binding.btnNext.setOnClickListener {
            getDataFromRecyc(intent.getIntExtra("questionAmount", 0))
        }
    }

    private fun setRecyclerSurvey(questionAmount: Int) {
        surveyAdapter = AdapterStudyApplySurvey()
        for(i in 1..questionAmount) {
            surveyDataList.add(SurveyData(i, "$i 번째 설문 제목"))
        }
        surveyAdapter.listData = surveyDataList
        binding.recycSurvey.adapter = surveyAdapter
        binding.recycSurvey.layoutManager = LinearLayoutManager(this)
    }

    //db로 넣어줄 부분 수정 필요
    private fun getDataFromRecyc(questionAmount: Int) {
        val answerList : MutableList<String?> = mutableListOf()

        if(questionAmount > 0) {
            answerList.add(AppSetting.prefs.getTmpString0())
            Log.d("txt", answerList[0].toString())
        }
        if(questionAmount > 1) {
            answerList.add(AppSetting.prefs.getTmpString1())
            Log.d("txt", answerList[1].toString())
        }
        if(questionAmount > 2) {
            answerList.add(AppSetting.prefs.getTmpString2())
            Log.d("txt", answerList[2].toString())
        }

        // 빈 값을 입력한 경우
        for(i in 0..(answerList.size - 1)) {
            if (answerList[i] == null || answerList[i] == "") {
                Toast.makeText(this, "답변을 작성해 주십시오", Toast.LENGTH_SHORT).show()
                break
            } else if (i == answerList.size - 1) {
                uploadApplicationToDB(questionAmount, answerList)
            }
        }

    }

    private fun getDataFromDB(studyId: Int) {
        val api = APIS.create()
        api.getPostContent(studyId).enqueue(object : Callback<PostContentDTO> {
            override fun onResponse(
                call: Call<PostContentDTO>,
                response: Response<PostContentDTO>
            ) {
                content = response.body()!!
                for(i in 1..content.questions.size) {
                    surveyDataList[i-1].SurveyTitle = content.questions[i-1].question!!
                    surveyAdapter.notifyItemChanged(i-1)
                }
            }

            override fun onFailure(call: Call<PostContentDTO>, t: Throwable) {
                Log.d("sapp server1 Error", t.message.toString())
            }

        })
    }

    private fun uploadApplicationToDB(questionAmount: Int, answerList: List<String?>) {

        for(i in 1..questionAmount){
            answerDTOList.add(AnswerDTO(i, surveyDataList[i-1].SurveyTitle, answerList[i-1]))
        }

        postApplicationDTO = PostApplicationDTO(null, null, answerDTOList)
        postApplicationDTO.apply {
            studyId = intent.getIntExtra("studyId", 0).toLong()
            memberId = AppSetting.prefs.getMemberId().toLong()
        }

        val api = APIS.create()
        api.postApplication(postApplicationDTO).enqueue(object : Callback<PostApplicationResultDTO> {
            override fun onResponse(
                call: Call<PostApplicationResultDTO>,
                response: Response<PostApplicationResultDTO>
            ) {
                Toast.makeText(binding.root.context, "지원이 완료 됐어요", Toast.LENGTH_SHORT).show()
                finish()
                Log.d("sapp server2 suc", response.toString())
            }

            override fun onFailure(call: Call<PostApplicationResultDTO>, t: Throwable) {
                Log.d("sapp server2 err", t.message.toString())
            }

        })

    }
}