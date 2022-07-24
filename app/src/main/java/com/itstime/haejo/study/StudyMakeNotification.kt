package com.itstime.haejo.study

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itstime.haejo.MainActivity
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostContentUploadDTO
import com.itstime.haejo.api.PostStudyResultDTO
import com.itstime.haejo.api.questionsUploadDTO
import com.itstime.haejo.databinding.ActivityStudyMakeNotificationBinding
import com.itstime.haejo.roomdb.AppDatabase
import com.itstime.haejo.roomdb.StudyMakeEntity
import com.itstime.haejo.util.AppSetting
import com.itstime.haejo.util.UtilFunctions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudyMakeNotification : AppCompatActivity() {
    lateinit var binding : ActivityStudyMakeNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyMakeNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }



        //모두 작성하고 완료 버튼
        binding.btnNext.setOnClickListener {
            if(isCheckSurveyChecked()) {
                //Toast.makeText(this, "가능한", Toast.LENGTH_SHORT).show()
                storeNotificationToRoomDB()
            }
        }

    }
    private fun isCheckSurveyChecked():Boolean {
        if(binding.etNotification.text == null ||
            binding.etNotification.text.toString().length < 5) {
            Toast.makeText(this, "5글자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
        else return true
    }

    private fun storeNotificationToRoomDB() {
        val db = AppDatabase.getInstance(applicationContext)
        CoroutineScope(Dispatchers.IO).launch {
            val studyMakeEntity =
                db!!.studyMakeDao().getStudyMakeEntitiy(AppSetting.prefs.getMemberId().toLong())
            studyMakeEntity.notification = binding.etNotification.text.toString()
            db!!.studyMakeDao().updateStudyMakeEntity(studyMakeEntity)
            Log.d("wtf", studyMakeEntity.personLimit.toString())
            storeToServer(studyMakeEntity)
        }
    }

    private fun storeToServer(studyMakeEntity: StudyMakeEntity) {
        //data 옮겨 담기
        val questionList: MutableList<questionsUploadDTO> = mutableListOf()
        questionList.add(questionsUploadDTO(studyMakeEntity.question0!!))
        if(studyMakeEntity.question1 != null)
            questionList.add(questionsUploadDTO(studyMakeEntity.question1!!))
        if(studyMakeEntity.question2 != null)
            questionList.add(questionsUploadDTO(studyMakeEntity.question2!!))
        val postContentUploadDTO = PostContentUploadDTO(
            studyMakeEntity.memberId,
            studyMakeEntity.title,
            studyMakeEntity.region,
            studyMakeEntity.dayOfWeek,
            studyMakeEntity.isOnline,
            studyMakeEntity.categories,
            studyMakeEntity.personLimit,
            studyMakeEntity.content,
            studyMakeEntity.notification,
            questionList
        )

        //server로 데이터 보내기
        val api = APIS.create()
        val postStudy = api.postStudy(postContentUploadDTO)
        postStudy.enqueue(object : Callback<PostStudyResultDTO> {
            override fun onResponse(
                call: Call<PostStudyResultDTO>,
                response: Response<PostStudyResultDTO>
            ) {
                Log.d("smn server success", response.toString())
                Toast.makeText(binding.root.context, "스터디가 만들어졌어요!", Toast.LENGTH_SHORT).show()
                UtilFunctions().clearStackAndGoMain(binding.root.context)
            }

            override fun onFailure(call: Call<PostStudyResultDTO>, t: Throwable) {
                Log.d("smn server error", t.message.toString())
            }

        })
    }
}