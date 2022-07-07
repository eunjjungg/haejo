package com.itstime.haejo.study.util

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.databinding.CustomRecyclerApplyBinding
import com.itstime.haejo.util.AppSetting

class StudyApplySurveyAdapter()
    : RecyclerView.Adapter<StudyApplySurveyAdapter.ViewHolder>() {

    var listData = mutableListOf<SurveyData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudyApplySurveyAdapter.ViewHolder {
        val binding = CustomRecyclerApplyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudyApplySurveyAdapter.ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(val binding: CustomRecyclerApplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SurveyData) {
            binding.tvTitle.setText(item.SurveyTitle)
            binding.etAnswer.addTextChangedListener(MemoTextWatcher(position))
        }
    }

    inner class MemoTextWatcher(var position: Int) : TextWatcher {

        override fun afterTextChanged(p0: Editable?) {
            when(position) {
                0 -> AppSetting.prefs.setTmpString0(p0.toString())
                1 -> AppSetting.prefs.setTmpString1(p0.toString())
                2 -> AppSetting.prefs.setTmpString2(p0.toString())
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

}