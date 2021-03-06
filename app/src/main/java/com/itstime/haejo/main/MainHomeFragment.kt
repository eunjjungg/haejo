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
    private var page = 2
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
            postDataList.add(PostListDTO(4, "", "", "", " "))
            mainHomePostAdapter.items = postDataList
            binding.recycPost.adapter = mainHomePostAdapter
            //here: ????????? ?????? ?????? ??????!
        }

        recycPostAddScrollListener()

        return binding.root
    }
    
    //region, week spinner ??????
    private fun spinnerAdapterConnect() {
        makeSpinnerAdapter(binding.spinnerRegion, R.array.region)
        makeSpinnerAdapter(binding.spinnerWeek, R.array.week)
    }

    //????????? ????????? ????????? ??????
    private fun makeSpinnerAdapter(spinner: Spinner, array: Int) {
        spinner.adapter =
            ArrayAdapter.createFromResource(binding.root.context, array, R.layout.custom_spinner_font)
                .also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
    }

    //??? ???????????? ?????? ?????? ?????? ?????? ?????? ????????? ??? ????????? ???????????? ??????????????? ??????
    //?????? ??? position == 0 ??? ?????? ?????? ????????? ?????????
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

                //scroll ?????? ??????????????? ??????
                if(!binding.recycPost.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    //DATA ???????????? ?????? IF ?????? ???????????? ?????? ????????? LOADING ??????, ?????? ???????????? ?????? ???????????? ???????????? ????????? ??? ?????? TITLE??? " "??? ?????? ?????? ?????? ??????
                    //mainHomePostAdapter.deleteLoading()

                    if(!loading)
                        getPostListData()
                    //????????? ?????? ??????!
                }
            }
        })
    }



    private fun getPostListData() {
        loading = true
        val api = APIS.create()
        for(i in (page * POST_PER_PAGE - POST_PER_PAGE)..(page * POST_PER_PAGE)){
            api.getPostList(i).enqueue(object : Callback<PostListDTO> {
                override fun onResponse(call: Call<PostListDTO>, response: Response<PostListDTO>) {
                    if(response.body() != null) {
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