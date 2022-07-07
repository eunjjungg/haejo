package com.itstime.haejo.main.util

import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.api.PostListDTO
import com.itstime.haejo.databinding.CustomRecyclerLoadingBinding
import com.itstime.haejo.databinding.CustomRecyclerPostListBinding
import com.itstime.haejo.study.StudyInfoActivity
import com.itstime.haejo.util.PostData

class MainHomePostAdapter: RecyclerView.Adapter <RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var items = ArrayList<PostListDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerPostListBinding.inflate(layoutInflater, parent, false)
                PostViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PostViewHolder)
            holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position].title) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    inner class PostViewHolder(private val binding: CustomRecyclerPostListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostListDTO) {
            binding.tvTitle.setText(item.title)
            binding.tvTimeStamp.setText(item.postTime)
            binding.tvTagIsUntact.setText(item.isUntact)
            binding.tvTagRegion.setText(item.region)

            itemView.setOnClickListener {
                Intent(binding.root.context, StudyInfoActivity::class.java).apply {
                    putExtra("studyId", item.studyId!!.toInt())
                }.run {binding.root.context.startActivity(this)}
            }
        }
    }

    inner class LoadingViewHolder(private val binding: CustomRecyclerLoadingBinding): RecyclerView.ViewHolder(binding.root) {

    }

    fun deleteLoading() {
        items.removeAt(items.lastIndex)
    }
}