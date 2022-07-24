package com.itstime.haejo.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.R
import com.itstime.haejo.api.*
import com.itstime.haejo.databinding.ActivityStudyInfoBinding
import com.itstime.haejo.study.util.AdapterStudyInfoComment
import com.itstime.haejo.study.util.AdapterStudyInfoSurvey
import com.itstime.haejo.study.util.SurveyData
import com.itstime.haejo.util.AppSetting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudyInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityStudyInfoBinding

    lateinit var content : PostContentDTO

    var questionAmount: Int = 0

    //recyclerView Survey 용
    private val surveyDataList: MutableList<SurveyData> = mutableListOf()
    lateinit var surveyAdapter: AdapterStudyInfoSurvey

    //recyclerView Comment 용
    private val commentDataList: MutableList<CommentDTO> = mutableListOf()
    lateinit var commentAdapter: AdapterStudyInfoComment

    //profile binding 용
    private var ratingDataList = ArrayList<StudyMemberDTO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerSurvey()
        setRecyclerComment()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        getProfileData(intent.getIntExtra("studyId", 0))
        getData(intent.getIntExtra("studyId", 0))

        binding.btnNext.setOnClickListener {
            Intent(this, StudyApplyActivity::class.java).apply {
                putExtra("studyId", intent.getIntExtra("studyId", 0))
                putExtra("questionAmount", questionAmount)
                startActivity(this)
            }
        }

        binding.btnBookmark.setOnClickListener {
            binding.btnBookmark.setImageResource(R.drawable.ic_bookmark_selected)
        }

        binding.btnComment.setOnClickListener {
            commentDataList.add(CommentDTO(
                binding.etComment.text.toString(),
                0,
                0,
                0,
                AppSetting.prefs.getProfile(),
                AppSetting.prefs.getNickname(),
                AppSetting.prefs.getMemberId().toLong()
            ))
            binding.etComment.setText("")
            commentAdapter.notifyDataSetChanged()
        }

    }

    //recyclerView Survey 어댑터만 연결
    private fun setRecyclerSurvey() {
        surveyAdapter = AdapterStudyInfoSurvey()
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
        if(content.personLimit!! > 0)
            binding.tvPeopleAmount.setText("#${content.personLimit}자리 남음")
        else
            binding.tvPeopleAmount.setText("#0자리 남음")

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

    private fun getProfileData(studyId: Int) {
        val api = APIS.create()
        api.getStudyMemberList(studyId.toLong()).enqueue(object : Callback<StudyMemberListDTO> {
            override fun onResponse(
                call: Call<StudyMemberListDTO>,
                response: Response<StudyMemberListDTO>
            ) {
                Log.d("sia server1 succ", response.body().toString())
                val tmpDTO = response.body()

                //host일 경우 setting
                for(i in tmpDTO!!.studyMemberList!!) {
                    if(i.grade == "host") {
                        binding.tvWriterNickname.setText(i.nickname)
                        binding.tvBattery.setText(i.battery.toString() + "%")
                        when(i.battery) {
                            in 0..20 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_20))
                            in 21..40 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_40))
                            in 41..70 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_70))
                            in 71..100 -> binding.imgBattery.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_battery_90))
                        }
                        when(i.profile) {
                            0 -> binding.imgWriterProfileImage.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_0))
                            1 -> binding.imgWriterProfileImage.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_1))
                            2 -> binding.imgWriterProfileImage.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_2))
                            3 -> binding.imgWriterProfileImage.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_3))
                        }

                        //host와 앱 사용 user가 동일한 경우 지원하기가 아닌 종료로 버튼 변경
                        if(AppSetting.prefs.getMemberId() == i.memberId!!.toInt())
                            binding.btnNext.setText("모집 마감")
                    }
                }

            }

            override fun onFailure(call: Call<StudyMemberListDTO>, t: Throwable) {
                Log.d("sia server1 fail", t.message.toString())
                Toast.makeText(binding.root.context, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }

    //recyclerView Comment 어댑터만 연결
    private fun setRecyclerComment() {
        commentAdapter = AdapterStudyInfoComment()
        commentAdapter.listData = commentDataList
        binding.recycComment.adapter = commentAdapter
        binding.recycComment.layoutManager = LinearLayoutManager(this)
    }


}