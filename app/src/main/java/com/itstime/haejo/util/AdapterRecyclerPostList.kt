package com.itstime.haejo.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.databinding.CustomRecyclerPostListBinding

class AdapterRecyclerPostList()
    : RecyclerView.Adapter<AdapterRecyclerPostList.ViewHolder>(){

    var listData = mutableListOf<PostData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterRecyclerPostList.ViewHolder {
        val binding = CustomRecyclerPostListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterRecyclerPostList.ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(val binding: CustomRecyclerPostListBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(data: PostData) {
                binding.tvTitle.setText("${data.title}")
                binding.tvTagRegion.setText("#${data.region}")
                binding.tvTagIsUntact.setText("#${data.isUntact}")
                binding.tvTimeStamp.setText("${data.timeStamp}")
                binding.tvCommentAmount.setText("${data.commentAmount.toString()}")
            }
        }
}
