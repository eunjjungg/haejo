package com.itstime.haejo.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.databinding.ActivityStudyMakeSurveyBinding
import com.itstime.haejo.roomdb.AppDatabase
import com.itstime.haejo.study.util.StudyInfoSurveyAdapter
import com.itstime.haejo.study.util.StudyMakeSurveyAdapter
import com.itstime.haejo.study.util.SurveyData
import com.itstime.haejo.util.AppSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyMakeSurveyActivity : AppCompatActivity() {

    lateinit var binding : ActivityStudyMakeSurveyBinding

    private val surveyDataList: MutableList<String?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyMakeSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recyclerview에서 작성하기 전에 먼저 null로 초기화 작업 필요
        AppSetting.prefs.setTmpString0(null)
        AppSetting.prefs.setTmpString1(null)
        AppSetting.prefs.setTmpString2(null)

        setRecyclerSurvey(intent.getIntExtra("questionAmount", 5))

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            getDataFromRecyc(intent.getIntExtra("questionAmount", 5))
        }

    }

    private fun setRecyclerSurvey(questionAmount: Int) {
        val surveyAdapter = StudyMakeSurveyAdapter()
        for(i in 1..questionAmount) {
            surveyDataList.add(null)
        }
        surveyAdapter.listData = surveyDataList
        binding.recycSurvey.adapter = surveyAdapter
        binding.recycSurvey.layoutManager = LinearLayoutManager(this)
    }

    //db로 넣어줄 부분 수정 필요
    private fun getDataFromRecyc(questionAmount: Int) {
        val questionList : MutableList<String?> = mutableListOf()

        if(questionAmount > 0) {
            questionList.add(AppSetting.prefs.getTmpString0())
        }
        if(questionAmount > 1) {
            questionList.add(AppSetting.prefs.getTmpString1())
        }
        if(questionAmount > 2) {
            questionList.add(AppSetting.prefs.getTmpString2())
        }

        // 빈 값을 입력한 경우
        for(i in 0..questionList.size - 1)
            if(questionList[i] == null || questionList[i] == "") {
                Toast.makeText(this, "질문을 작성해 주십시오", Toast.LENGTH_SHORT).show()
                break
            } else if(i == questionList.size - 1) {
                storeQuestionsToRoomDB(questionList)
                startActivity(Intent(this, StudyMakeNotification::class.java))
            }

    }

    private fun storeQuestionsToRoomDB(questionList: List<String?>) {
        val db = AppDatabase.getInstance(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            val studyMakeEntity =
                db!!.studyMakeDao().getStudyMakeEntitiy(AppSetting.prefs.getMemberId().toLong())
            for(i in 0..questionList.size - 1) {
                if(i == 0)
                    studyMakeEntity.question0 = questionList[i]
                else if (i == 1)
                    studyMakeEntity.question1 = questionList[i]
                else if (i == 2)
                    studyMakeEntity.question2 = questionList[2]
            }
            db!!.studyMakeDao().updateStudyMakeEntity(studyMakeEntity)
        }

    }

    private fun getDataFromRoomDB() {
        val db = AppDatabase.getInstance(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            val studyMakeDAO = db!!.studyMakeDao().getStudyMakeEntitiy(AppSetting.prefs.getMemberId().toLong())
            Log.d("room", studyMakeDAO.toString())
        }
    }
}