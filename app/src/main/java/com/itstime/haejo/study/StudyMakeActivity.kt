package com.itstime.haejo.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.itstime.haejo.databinding.ActivityStudyMakeBinding
import com.itstime.haejo.study.util.StudyMakeAdapter

class StudyMakeActivity : AppCompatActivity() {

    lateinit var binding : ActivityStudyMakeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyMakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        StudyMakeAdapter(binding).spinnerAdapterConnect()
        StudyMakeAdapter(binding).spinnerSelection()

        //뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        //모두 작성하고 완료 버튼
        binding.btnNext.setOnClickListener {
            if(isCheckAllSurveyChecked()) {
                storeTmpDataToPrefs()
                moveSurveyPage()
            }
        }
    }


    //각 필수 기입 칸들 기입했는지 check
    private fun isCheckAllSurveyChecked(): Boolean {
        //지역 스피너 선택 완?
        if(binding.spinnerRegion.selectedItemPosition == 0) {
            Toast.makeText(this, "지역을 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        //대면 스피너 선택 완?
        else if(binding.spinnerisUntact.selectedItemPosition == 0) {
            Toast.makeText(this, "대면여부를 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        //요일 스피너 선택 완?
        else if(binding.spinnerWeek.selectedItemPosition == 0) {
            Toast.makeText(this, "스터디 진행 요일을 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        //분야 스피너 선택 완?
        else if(binding.spinnerCategory.selectedItemPosition == 0) {
            Toast.makeText(this, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        //질문 스피너 선택 완?
        else if(binding.spinnerQuestionAmount.selectedItemPosition == 0) {
            Toast.makeText(this, "설문 개수를 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        //인원 스피너 선택 완?
        else if(binding.spinnerPeopleAmount.selectedItemPosition == 0) {
            Toast.makeText(this, "인원을 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        //제목 4글자 이상 완?
        else if(binding.etSurveyTitle.text.toString().length <= 3) {
            Toast.makeText(this, "제목은 최소 4글자 이상 작성해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        //내용 4글자 이상 완?
        else if(binding.etSurveyContent.text.toString().length <= 3) {
            Toast.makeText(this, "내용은 최소 4글자 이상 작성해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        else
            return true
    }

    //각 데이터들 Shared Preference에 일시적으로 넣기 or 방법 생각...
    private fun storeTmpDataToPrefs() {

    }

    private fun moveSurveyPage() {
        val questionAmount = binding.spinnerQuestionAmount.selectedItemPosition
        when(questionAmount) {
            1 -> startActivity(Intent(this, StudyMakeSurvey1Activity::class.java))
            2 -> startActivity(Intent(this, StudyMakeSurvey2Activity::class.java))
            3 -> startActivity(Intent(this, StudyMakeSurvey3Activity::class.java))
        }
    }
}