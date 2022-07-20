package com.itstime.haejo.study.util

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.CustomRecyclerEmptyBinding
import com.itstime.haejo.databinding.CustomRecyclerLoadingBinding
import com.itstime.haejo.databinding.CustomRecyclerPostListBinding
import com.itstime.haejo.study.StudyInfoActivity
import com.itstime.haejo.study.ongoing.OngoingInfoActivity

class AdapterOngoingRecyclerPostList
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_CANCEL = 2
    var listData = mutableListOf<PostListDTO>()

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
                val binding = CustomRecyclerPostListBinding.inflate(layoutInflater, parent, false)
                PostViewHolder(binding)
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
        if(holder is PostViewHolder)
            holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(listData[position].title) {
            " " -> VIEW_TYPE_LOADING
            ";" -> VIEW_TYPE_CANCEL
            else -> VIEW_TYPE_ITEM
        }
    }


    inner class PostViewHolder(private val binding: CustomRecyclerPostListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostListDTO) {
            binding.tvTitle.setText(item.title)
            binding.tvTimeStamp.setText(item.postTime!!.replace("T", " "))
            binding.tvTagIsUntact.setText(item.isUntact)
            binding.tvTagRegion.setText(item.region)

            itemView.setOnClickListener {
                Intent(binding.root.context, OngoingInfoActivity::class.java).apply {
                    //putExtra("studyId", item.studyId!!.toLong())
                }.run {binding.root.context.startActivity(this)}
            }
        }
    }

    inner class LoadingViewHolder(private val binding: CustomRecyclerLoadingBinding): RecyclerView.ViewHolder(binding.root) {

    }

    inner class CancleViewHolder(private val binding: CustomRecyclerEmptyBinding): RecyclerView.ViewHolder(binding.root) {

    }
}