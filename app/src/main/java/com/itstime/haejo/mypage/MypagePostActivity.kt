package com.itstime.haejo.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostListArrayDTO
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.ActivityMypagePostBinding
import com.itstime.haejo.util.AdapterRecyclerPostList
import com.itstime.haejo.util.AppSetting
import com.itstime.haejo.util.PostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypagePostActivity : AppCompatActivity() {

    //data binding
    lateinit var binding : ActivityMypagePostBinding

    //recyclerView Post List 용
    private val postDataList: MutableList<PostListDTO> = mutableListOf()
    private lateinit var adapterRecyclerPostList: AdapterRecyclerPostList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypagePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.recycPost.apply {
            binding.recycPost.layoutManager = LinearLayoutManager(binding.root.context)
            adapterRecyclerPostList = AdapterRecyclerPostList()
            postDataList.add(PostListDTO(title = " "))
            adapterRecyclerPostList.listData = postDataList
            binding.recycPost.adapter = adapterRecyclerPostList
        }

        setRecyclerPostList(AppSetting.prefs.getMemberId().toLong())
    }

    private fun setRecyclerPostList(memberId: Long) {
        val api = APIS.create()
        api.getWroteStudyPostList(memberId).enqueue(object : Callback<PostListArrayDTO> {
            override fun onResponse(
                call: Call<PostListArrayDTO>,
                response: Response<PostListArrayDTO>
            ) {
                Log.d("mpa server succ", response.body()?.postListDTO?.size.toString())
                if(response.body()?.postListDTO?.size == 0) {
                    postDataList.clear()
                    //아무것도 없음을 표시하기 위한 리사이클러뷰 컴포넌트
                    postDataList.add(PostListDTO(title = ";"))
                    adapterRecyclerPostList.notifyDataSetChanged()
                } else {
                    val tmpDTO = response.body()
                    postDataList.clear()
                    postDataList.addAll(tmpDTO!!.postListDTO!!)
                    adapterRecyclerPostList.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<PostListArrayDTO>, t: Throwable) {
                postDataList.clear()
                //아무것도 없음을 표시하기 위한 리사이클러뷰 컴포넌트
                postDataList.add(PostListDTO(title = ";"))
                adapterRecyclerPostList.notifyDataSetChanged()
                Log.d("mpa server failure", t.message.toString())
                Toast.makeText(binding.root.context, "다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }

        })
    }

}