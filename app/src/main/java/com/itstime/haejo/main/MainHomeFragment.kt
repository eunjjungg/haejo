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
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostResult
import com.itstime.haejo.api.UploadUserModel
import com.itstime.haejo.api.UserModel
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
    private var page = 1
    private var POST_PER_PAGE = 10
    private lateinit var mainHomePostAdapter: MainHomePostAdapter
    private val postDataList = ArrayList<PostData>()

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
            postDataList.addAll(dummy1("first"))
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
                    mainHomePostAdapter.deleteLoading()

                    postDataList.addAll(dummy1("$page"))
                    //데이터 추가 작업!
                    mainHomePostAdapter.notifyItemInserted(page++ * POST_PER_PAGE)
                }
            }
        })
    }

    fun dummy1(sequence: String): ArrayList<PostData> {
        val dummyList1 = ArrayList<PostData>()
        dummyList1.apply {
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(sequence, "부산", "대면", "20.02.11 20:29", 4))
            add(PostData(" ", "부산", "대면", "20.02.11 20:29", 4))
        }
        return dummyList1
    }
}