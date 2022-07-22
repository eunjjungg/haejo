package com.itstime.haejo.study.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.R
import com.itstime.haejo.api.CommentDTO
import com.itstime.haejo.databinding.CustomCommentBinding
import com.itstime.haejo.databinding.CustomCommentNestedBinding
import com.itstime.haejo.databinding.CustomRecyclerLoadingBinding

class AdapterStudyInfoComment
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val VIEW_TYPE_COMMENT = 0
    private val VIEW_TYPE_NESTED_COMMENT = 1
    private val VIEW_TYPE_LOADING = 2
    var listData = mutableListOf<CommentDTO>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_COMMENT -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomCommentBinding.inflate(layoutInflater, parent, false)
                CommentViewHolder(binding)
            }
            VIEW_TYPE_NESTED_COMMENT -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomCommentNestedBinding.inflate(layoutInflater, parent, false)
                NestedCommentViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomRecyclerLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CommentViewHolder)
            holder.bind(listData[position])
        else if(holder is NestedCommentViewHolder)
            holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int) : Int {
        return when(listData[position].layer) {
            VIEW_TYPE_COMMENT -> VIEW_TYPE_COMMENT
            else -> VIEW_TYPE_NESTED_COMMENT
        }
    }

    inner class CommentViewHolder(private val binding: CustomCommentBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: CommentDTO) {
                binding.tvComment.setText(item.comment)
                binding.tvWriterNickname.setText(item.nickname)
                when(item.profile) {
                    0 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_0))
                    1 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_1))
                    2 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_2))
                    3 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_3))
                }
            }
        }

    inner class NestedCommentViewHolder(private val binding: CustomCommentNestedBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: CommentDTO) {
                binding.tvComment.setText(item.comment)
                binding.tvWriterNickname.setText(item.nickname)
                when(item.profile) {
                    0 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_0))
                    1 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_1))
                    2 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_2))
                    3 -> binding.imgProfile.setImageDrawable(binding.root.resources.getDrawable(R.drawable.ic_profile_3))
                }
            }
        }

    inner class LoadingViewHolder(private val binding: CustomRecyclerLoadingBinding): RecyclerView.ViewHolder(binding.root) {

    }
}