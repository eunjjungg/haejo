package com.itstime.haejo.main

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.annotations.SerializedName
import com.itstime.haejo.R
import com.itstime.haejo.api.*
import com.itstime.haejo.databinding.FragmentMainHomeBinding
import com.itstime.haejo.main.util.MainHomePostAdapter
import com.itstime.haejo.study.StudyMakeActivity
import com.itstime.haejo.util.PostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeFragment : Fragment() {

    lateinit var binding : FragmentMainHomeBinding

    //current page
    private var itemAmount = 0
    private var loading = false
    private var page = 1
    private var POST_PER_PAGE = 10
    private lateinit var mainHomePostAdapter: MainHomePostAdapter
    private var postDataList = ArrayList<PostListDTO>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainHomeBinding.inflate(inflater, container, false)

        //spinner setting
        spinnerAdapterConnect()
        spinnerSelection()


        binding.fltbtnMakeStudy.setOnClickListener {
            startActivity(Intent(binding.root.context, StudyMakeActivity::class.java))
        }

        binding.recycPost.apply {
            binding.recycPost.layoutManager = LinearLayoutManager(context)
            mainHomePostAdapter = MainHomePostAdapter()
            itemAmount++
            postDataList.add(PostListDTO(1, "2022-07-10 13:40:49", "비대면", "서울", "미드 영어 단어 스터디 구인"))
            mainHomePostAdapter.items = postDataList
            binding.recycPost.adapter = mainHomePostAdapter
            //here: 데이터 추가 작업 필요!
        }

        recycPostAddScrollListener()

        return binding.root
    }
    
    //region, week spinner 연결
    private fun spinnerAdapterConnect() {
        makeSpinnerAdapter(binding.spinnerRegion, R.array.region)
        makeSpinnerAdapter(binding.spinnerWeek, R.array.week)
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

    //각 스피너에 기본 값이 아닌 특정 값을 넣었을 때 보라색 스피너로 변화하도록 설정
    //기본 값 position == 0 일 때는 회색 스피너 그대로
    fun spinnerSelection() {
        binding.spinnerWeek.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0) {
                    binding.spinnerWeek.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_selected
                    )
                }
                else {
                    binding.spinnerWeek.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_unselected
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerRegion.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0) {
                    binding.spinnerRegion.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_selected
                    )
                }
                else {
                    binding.spinnerRegion.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_unselected
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun recycPostAddScrollListener() {
        binding.recycPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                //scroll 끝에 도달했는지 확인
                if(!binding.recycPost.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    //DATA 받아오는 작업 IF 남은 데이터가 있을 시에만 LOADING 삭제, 남은 데이터가 없는 경우에는 마지막을 구분할 수 있게 TITLE에 " "가 아닌 다른 값을 삽입
                    //mainHomePostAdapter.deleteLoading()

                    if(!loading)
                        getPostListData()
                    //데이터 추가 작업!
                }
            }
        })
    }



    private fun getPostListData() {
        loading = true
        val api = APIS.create()
        for(i in (page * POST_PER_PAGE - POST_PER_PAGE + 1)..(page * POST_PER_PAGE)){
            api.getPostList(i).enqueue(object : Callback<PostListDTO> {
                override fun onResponse(call: Call<PostListDTO>, response: Response<PostListDTO>) {
                    if(response.body() != null) {
                        Log.d("data", i.toString())
                        var tmpDTO = PostListDTO(
                            response.body()!!.studyId,
                            response.body()!!.postTime,
                            response.body()!!.isUntact,
                            response.body()!!.region,
                            response.body()!!.title
                        )
                        itemAmount++
                        postDataList.add(tmpDTO)
                        mainHomePostAdapter.notifyItemInserted(itemAmount)
                    }
                }

                override fun onFailure(call: Call<PostListDTO>, t: Throwable) {
                    //Log.d("server failure", t.message.toString())
                }
            })
        }
        page++
        loading = false
    }


}