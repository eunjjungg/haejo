package com.itstime.haejo.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.ActivityMypagePostBinding
import com.itstime.haejo.util.AdapterRecyclerPostList
import com.itstime.haejo.util.PostData

class MypagePostActivity : AppCompatActivity() {

    //data binding
    lateinit var binding : ActivityMypagePostBinding

    //recyclerView Post List 용
    private val postDataList: MutableList<PostListDTO> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypagePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setRecyclerPostList()
    }

    //db에서 받아오는 값으로 data 수정 필요
    private fun setRecyclerPostList() {
        val postAdapter = AdapterRecyclerPostList()
        postDataList.apply {
            //dummy data
            add(PostListDTO(title = ";"))

        }

        postAdapter.listData = postDataList
        binding.recycPost.adapter = postAdapter
        binding.recycPost.layoutManager = LinearLayoutManager(this)
    }

}