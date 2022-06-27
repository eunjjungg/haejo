package com.itstime.haejo.study.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.databinding.CustomRecyclerSurveyBinding

class StudyInfoSurveyAdapter()
    : RecyclerView.Adapter<StudyInfoSurveyAdapter.ViewHolder>(){

    var listData = mutableListOf<SurveyData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudyInfoSurveyAdapter.ViewHolder {
        val binding = CustomRecyclerSurveyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudyInfoSurveyAdapter.ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(val binding: CustomRecyclerSurveyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SurveyData) {
            binding.tvSurveyNumber.setText("${binding.tvSurveyNumber.text}${item.surveyNum}")
            binding.tvSurveyContent.setText(item.SurveyTitle)
        }
    }
}