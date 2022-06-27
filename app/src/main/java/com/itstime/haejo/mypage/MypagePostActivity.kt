package com.itstime.haejo.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.databinding.ActivityMypagePostBinding
import com.itstime.haejo.util.AdapterRecyclerPostList
import com.itstime.haejo.util.PostData

class MypagePostActivity : AppCompatActivity() {

    //data binding
    lateinit var binding : ActivityMypagePostBinding

    //recyclerView Post List 용
    private val postDataList: MutableList<PostData> = mutableListOf()

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
            add(PostData("이것은 첫 번째 제목입니다.", "지역 무관",
                "비대면", "11/13 12:24", 4))
            add(PostData("이것은 두 번째 제목입니다.", "경북",
                "대면", "11/13 12:24", 12))
            add(PostData("이것은 세 번째 제목입니다.", "인천",
                "비대면", "11/13 12:24", 123))
            add(PostData("이것은 네 번째 제목입니다.", "전북",
                "대면", "11/13 12:24", 0))
            add(PostData("이것은 다섯 번째 제목입니다.", "제주",
                "비대면", "11/13 12:24", 1))
            add(PostData("이것은 첫 번째 제목입니다.", "지역 무관",
                "비대면", "11/13 12:24", 4))
            add(PostData("이것은 두 번째 제목입니다.", "경북",
                "대면", "11/13 12:24", 12))
            add(PostData("이것은 세 번째 제목입니다.", "인천",
                "비대면", "11/13 12:24", 123))
            add(PostData("이것은 네 번째 제목입니다.", "전북",
                "대면", "11/13 12:24", 0))
            add(PostData("이것은 다섯 번째 제목입니다.", "제주",
                "비대면", "11/13 12:24", 1))
        }

        postAdapter.listData = postDataList
        binding.recycPost.adapter = postAdapter
        binding.recycPost.layoutManager = LinearLayoutManager(this)
    }

}