package com.itstime.haejo.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.GetRatingArrayDTO
import com.itstime.haejo.api.GetRatingDTO
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.ActivityMypageRatingBinding
import com.itstime.haejo.mypage.util.AdapterReceiveRating
import com.itstime.haejo.util.AppSetting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageRatingActivity : AppCompatActivity() {

    //data binding
    lateinit var binding : ActivityMypageRatingBinding

    //recyclerView Rating List 용
    private val ratingDataList: MutableList<GetRatingDTO> = mutableListOf()
    private lateinit var adapterReceiveRating: AdapterReceiveRating

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.recycRating.apply {
            binding.recycRating.layoutManager = LinearLayoutManager(binding.root.context)
            adapterReceiveRating = AdapterReceiveRating()
            ratingDataList.add(GetRatingDTO(" ", 3, null))
            adapterReceiveRating.listData = ratingDataList
            binding.recycRating.adapter = adapterReceiveRating
        }

        setRecyclerRatingList(AppSetting.prefs.getMemberId().toLong())
    }

    private fun setRecyclerRatingList(memberId: Long) {
        val api = APIS.create()
        api.getRatingList(memberId).enqueue(object : Callback<GetRatingArrayDTO> {
            override fun onResponse(
                call: Call<GetRatingArrayDTO>,
                response: Response<GetRatingArrayDTO>
            ) {
                Log.d("mra server succ", response.body().toString())
                if(response.body()?.postListDTO?.size == 0) {
                    ratingDataList.clear()
                    //아무것도 없음을 표시하기 위한 리사이클러뷰 컴포넌트
                    ratingDataList.add(GetRatingDTO(nickname = ";"))
                    adapterReceiveRating.notifyDataSetChanged()
                } else {
                    val tmpDTO = response.body()
                    ratingDataList.clear()
                    ratingDataList.addAll(tmpDTO!!.postListDTO!!)
                    adapterReceiveRating.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<GetRatingArrayDTO>, t: Throwable) {
                Log.d("mra server fail", t.message.toString())
                Toast.makeText(binding.root.context, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
                ratingDataList.clear()
                //아무것도 없음을 표시하기 위한 리사이클러뷰 컴포넌트
                ratingDataList.add(GetRatingDTO(nickname = ";"))
                adapterReceiveRating.notifyDataSetChanged()
            }

        })
    }
}