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
import com.itstime.haejo.databinding.ActivityMypageStudyBinding
import com.itstime.haejo.util.AdapterRecyclerPostList
import com.itstime.haejo.util.PostData

class MypageStudyActivity : AppCompatActivity() {

    //data binding
    lateinit var binding : ActivityMypageStudyBinding

    //recyclerView Post List 용
    private val postDataList: MutableList<PostData> = mutableListOf()

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
            add(
                PostData("이것은 첫 번째 제목입니다.", "지역 무관",
                "비대면", "11/13 12:24", 4)
            )
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
                //postDataList에 post 맞게 추가 아래는 dummy Data, 아래 쭉 삭제
                postDataList.add(
                    PostData("이것은 첫 번째 제목입니다.", "지역 무관",
                        "비대면", "11/13 12:24", 4)
                )
                postDataList.add(
                    PostData("이것은 첫 번째 제목입니다.", "지역 무관",
                        "비대면", "11/13 12:24", 4)
                )
            }
            participatingOnPosts -> {
                //postDataList에 post 맞게 추가 아래는 dummy Data, 아래 쭉 삭제
                makeDummyData()
                postDataList.add(
                    PostData("This is second title.", "지역 무관",
                        "비대면", "11/13 12:24", 4)
                )
            }
            participatingEndPosts -> {
                //postDataList에 post 맞게 추가 아래는 dummy Data, 아래 쭉 삭제
                postDataList.add(
                    PostData("세 번째 셀렉션입니다.", "지역 무관",
                        "비대면", "11/13 12:24", 4)
                )
                makeDummyData()
                makeDummyData()
            }
        }
        postAdapter.notifyDataSetChanged()
    }

    //dummy data 만드는 함수 (삭제 필요)
    private fun makeDummyData() {
        postDataList.apply {
            //dummy data
            add(
                PostData("이것은 첫 번째 제목입니다.", "지역 무관",
                    "비대면", "11/13 12:24", 4)
            )
            add(
                PostData("이것은 두 번째 제목입니다.", "경북",
                    "대면", "11/13 12:24", 12)
            )
            add(
                PostData("이것은 세 번째 제목입니다.", "인천",
                    "비대면", "11/13 12:24", 123)
            )
            add(
                PostData("이것은 네 번째 제목입니다.", "전북",
                    "대면", "11/13 12:24", 0)
            )
            add(
                PostData("이것은 다섯 번째 제목입니다.", "제주",
                    "비대면", "11/13 12:24", 1)
            )
        }
    }
}