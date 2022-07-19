package com.itstime.haejo.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.itstime.haejo.R
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostListArrayDTO
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.FragmentMainOngoingBinding
import com.itstime.haejo.main.util.MainHomePostAdapter
import com.itstime.haejo.util.AdapterRecyclerPostList
import com.itstime.haejo.util.AppSetting
import com.itstime.haejo.util.PostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainOngoingFragment : Fragment() {

    //data binding
    lateinit var binding : FragmentMainOngoingBinding

    //recyclerView Post List 용
    private var postDataList = ArrayList<PostListDTO>()
    private lateinit var adpterRecyclerPostList: AdapterRecyclerPostList

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainOngoingBinding.inflate(inflater, container, false)

        binding.recycPost.apply {
            binding.recycPost.layoutManager = LinearLayoutManager(binding.root.context)
            adpterRecyclerPostList = AdapterRecyclerPostList()
            postDataList.add(PostListDTO(title=" "))
            adpterRecyclerPostList.listData = postDataList
            binding.recycPost.adapter = adpterRecyclerPostList
        }

        getPostDataList(AppSetting.prefs.getMemberId().toLong())


        return binding.root
    }


    private fun getPostDataList(memberId: Long) {
        val api = APIS.create()
        api.getOnGoingPostList(157).enqueue(object : Callback<PostListArrayDTO> {
            override fun onResponse(
                call: Call<PostListArrayDTO>,
                response: Response<PostListArrayDTO>
            ) {
                Log.d("mof server succ", response.body()?.postListDTO?.size.toString())
                if(response.body()?.postListDTO?.size == 0) {
                    val tmpDTO = PostListDTO(title = ";")
                    postDataList.clear()
                    postDataList.add(tmpDTO)
                    adpterRecyclerPostList.notifyDataSetChanged()
                }
                else {
                    val tmpDTO = response.body()
                    postDataList.clear()
                    postDataList.addAll(tmpDTO!!.postListDTO!!)
                    adpterRecyclerPostList.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<PostListArrayDTO>, t: Throwable) {
                val tmpDTO = PostListDTO(title = ";")
                postDataList.clear()
                postDataList.add(tmpDTO)
                adpterRecyclerPostList.notifyDataSetChanged()
                Log.d("mof server failure", t.message.toString())
            }

        })
    }
}