package com.itstime.haejo.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.R
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.ActivityMypageStudyBinding
import com.itstime.haejo.util.AdapterRecyclerPostList
import com.itstime.haejo.util.PostData

class MypageStudyActivity : AppCompatActivity() {

    //data binding
    lateinit var binding : ActivityMypageStudyBinding

    //recyclerView Post List 용
    private val postDataList: MutableList<PostListDTO> = mutableListOf()

    //recyclerView 갱신을 위한 함수
    private val allPosts: Int = 0
    private val participatingOnPosts: Int = 1
    private val participatingEndPosts: Int = 2
    lateinit var postAdapter: AdapterRecyclerPostList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setRecyclerPostList()
        makeSpinnerAdapter(binding.spinnerStatus, R.array.participationStatus)
        spinnerSelection()
    }

    //db에서 받아오는 값으로 data 수정 필요
    private fun setRecyclerPostList() {
        postAdapter = AdapterRecyclerPostList()

        //아래 apply 부분이 db에서 받아오는 값으로 수정해야 되는 부분
        postDataList.apply {
            //dummy data
            add(PostListDTO(title = ";"))
        }

        postAdapter.listData = postDataList
        binding.recycPost.adapter = postAdapter
        binding.recycPost.layoutManager = LinearLayoutManager(this)
    }

    //스피너 어댑터 추가용 함수
    private fun makeSpinnerAdapter(spinner: Spinner, array: Int) {
        spinner.adapter =
            ArrayAdapter.createFromResource(binding.root.context, array, R.layout.custom_spinner_font)
                .also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
    }

    //spinner 선택에 따른 작업 함수
    private fun spinnerSelection() {
        binding.spinnerStatus.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                //각 스피너에 기본 값이 아닌 특정 값을 넣었을 때 보라색 스피너로 변화하도록 설정
                if(position != allPosts) {
                    binding.spinnerStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_selected
                    )

                    //사용자가 선택한 spinner 값에 맞게 recyclerView update
                    if(position == participatingOnPosts)
                        updateRecyclerPostList(participatingOnPosts)
                    else if(position == participatingEndPosts)
                        updateRecyclerPostList(participatingEndPosts)
                }
                else {
                    //기본 값 position == 0 일 때는 회색 스피너 그대로
                    binding.spinnerStatus.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_unselected
                    )

                    //사용자가 선택한 spinner 값에 맞게 recyclerView update
                    updateRecyclerPostList(allPosts)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }

    //spinner 선택에 따른 recyclerView 갱신 함수
    private fun updateRecyclerPostList(type: Int) {
        postDataList.clear()
        when(type) {
            allPosts -> {
                postDataList.apply {
                    //dummy data
                    add(PostListDTO(title = ";"))
                }
            }
            participatingEndPosts -> {
                postDataList.apply {
                    //dummy data
                    add(PostListDTO(title = ";"))
                }
            }
        }
        postAdapter.notifyDataSetChanged()
    }

    //dummy data 만드는 함수 (삭제 필요)
    private fun makeDummyData() {
        postDataList.apply {
            //dummy data
            postDataList.apply {
                //dummy data
                add(PostListDTO(title = ";"))
            }
        }
    }
}