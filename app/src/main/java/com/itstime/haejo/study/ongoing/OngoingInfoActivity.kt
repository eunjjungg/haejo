package com.itstime.haejo.study.ongoing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostContentDTO
import com.itstime.haejo.api.StudyMemberDTO
import com.itstime.haejo.api.StudyMemberListDTO
import com.itstime.haejo.databinding.ActivityOngoingInfoBinding
import com.itstime.haejo.study.util.AdapterOngoingRecyclerRatingList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OngoingInfoActivity : AppCompatActivity() {

    //data binding
    lateinit var binding : ActivityOngoingInfoBinding

    //recyclerView Rating List 용
    private var ratingDataList = ArrayList<StudyMemberDTO>()
    private lateinit var adapterOngoingRecyclerRatingList:
            AdapterOngoingRecyclerRatingList
    private var studyId: Long = 0
    private val api = APIS.create()
    //post content 채우기 용
    private lateinit var postContentDTO: PostContentDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOngoingInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.recycRating.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapterOngoingRecyclerRatingList = AdapterOngoingRecyclerRatingList()
            ratingDataList.add(StudyMemberDTO(nickname = " "))
            adapterOngoingRecyclerRatingList.listData = ratingDataList
            binding.recycRating.adapter = adapterOngoingRecyclerRatingList
        }


        studyId = intent.getLongExtra("studyId", 178)
        getRatingDataList(studyId)
        getPostContentData(studyId)
    }

    //studyMember 데이터 가져오기
    private fun getRatingDataList(studyId: Long) {
        api.getStudyMemberList(studyId).enqueue(object : Callback<StudyMemberListDTO> {
            override fun onResponse(
                call: Call<StudyMemberListDTO>,
                response: Response<StudyMemberListDTO>
            ) {
                Log.d("oia server1 succ", response.body().toString())
                val tmpDTO = response.body()
                ratingDataList.clear()
                ratingDataList.addAll(tmpDTO!!.studyMemberList!!)

                //dummy data start
                ratingDataList.apply {
                    add(StudyMemberDTO("해조요", "guest", 35, 0))
                    add(StudyMemberDTO("갱쥐", "guest", 95, 1))
                    add(StudyMemberDTO("왜요왜왜", "guest", 20, 2))
                    add(StudyMemberDTO("NOLY", "guest", 55, 3))
                    add(StudyMemberDTO("검정취마", "guest", 65, 2))
                }
                //dummy data end

                adapterOngoingRecyclerRatingList.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<StudyMemberListDTO>, t: Throwable) {
                Log.d("oia server1 fail", t.message.toString())
                Toast.makeText(binding.root.context, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getPostContentData(studyId: Long) {
        api.getPostContent(studyId.toInt()).enqueue(object : Callback<PostContentDTO> {
            override fun onResponse(
                call: Call<PostContentDTO>,
                response: Response<PostContentDTO>
            ) {
                Log.d("oia server2 succ", response.body().toString())
                val tmpDTO = response.body()
                if(tmpDTO != null)
                    initPostContent(tmpDTO!!)
                else
                    Toast.makeText(binding.root.context, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<PostContentDTO>, t: Throwable) {
                Log.d("oia server2 fail", t.message.toString())
                Toast.makeText(binding.root.context, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun initPostContent(postContentDTO: PostContentDTO) {
        binding.tvTitle.setText(postContentDTO.title)
        binding.tvNotification.setText(postContentDTO.notification)
    }
}