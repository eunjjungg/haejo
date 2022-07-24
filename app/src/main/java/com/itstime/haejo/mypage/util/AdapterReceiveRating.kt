package com.itstime.haejo.mypage.util

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.R
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.GetRatingDTO
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.api.UserGetDTO
import com.itstime.haejo.databinding.*
import com.itstime.haejo.study.StudyInfoActivity
import com.itstime.haejo.util.AdapterRecyclerPostList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterReceiveRating
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_CANCEL = 2
    var listData = mutableListOf<GetRatingDTO>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CANCEL -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerEmptyBinding.inflate(layoutInflater, parent, false)
                CancleViewHolder(binding)
            }
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerReceiveRatingBinding.inflate(layoutInflater, parent, false)
                RatingViewHolder(binding)
            }
            //VIEW_TYPE_LOADING
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is RatingViewHolder)
            holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(listData[position].nickname) {
            " " -> VIEW_TYPE_LOADING
            ";" -> VIEW_TYPE_CANCEL
            else -> VIEW_TYPE_ITEM
        }
    }

    inner class RatingViewHolder(private val binding: CustomRecyclerReceiveRatingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetRatingDTO) {
            binding.tvWriterNickname.setText(item.nickname)
            when(getMemberProfile(item.senderId!!)) {
                0 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_0))
                1 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_1))
                2 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_2))
                3 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_3))
            }
            when(item.star) {
                5 -> binding.tvComment.setText(binding.root.resources.getText(R.string.rating5))
                4 -> binding.tvComment.setText(binding.root.resources.getText(R.string.rating4))
                3 -> binding.tvComment.setText(binding.root.resources.getText(R.string.rating3))
                2 -> binding.tvComment.setText(binding.root.resources.getText(R.string.rating2))
                1 -> binding.tvComment.setText(binding.root.resources.getText(R.string.rating1))
            }

        }
    }

    inner class LoadingViewHolder(private val binding: CustomRecyclerLoadingBinding): RecyclerView.ViewHolder(binding.root) {

    }

    inner class CancleViewHolder(private val binding: CustomRecyclerEmptyBinding): RecyclerView.ViewHolder(binding.root) {

    }

    private fun getMemberProfile(memeberId: Long) : Int {
        val api = APIS.create()
        var profile = 0
        api.getUser(memeberId).enqueue(object : Callback<UserGetDTO> {
            override fun onResponse(call: Call<UserGetDTO>, response: Response<UserGetDTO>) {
                Log.d("arr server succ", response.body().toString())
                val tmpDTO = response.body()
                profile = tmpDTO!!.profile!!
            }

            override fun onFailure(call: Call<UserGetDTO>, t: Throwable) {
                Log.d("arr server fail", t.message.toString())
            }

        })
        return profile
    }
}