package com.itstime.haejo.study.util

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.databinding.CustomRecyclerSurveyEdittableBinding
import com.itstime.haejo.util.AppSetting


class StudyMakeSurveyAdapter()
    :RecyclerView.Adapter<StudyMakeSurveyAdapter.ViewHolder>() {

    var listData = mutableListOf<String?>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudyMakeSurveyAdapter.ViewHolder {
        val binding = CustomRecyclerSurveyEdittableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudyMakeSurveyAdapter.ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind()
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(val binding: CustomRecyclerSurveyEdittableBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            when(position) {
                0 -> binding.tvSurveyNumber.setText("첫${binding.tvSurveyNumber.text}")
                1 -> binding.tvSurveyNumber.setText("두${binding.tvSurveyNumber.text}")
                2 -> binding.tvSurveyNumber.setText("세${binding.tvSurveyNumber.text}")
            }
            binding.etSurvey.addTextChangedListener(MemoTextWatcher(position))
            if(listData[position] != null)
                binding.etSurvey.setText(listData[position].toString())
        }
    }

    inner class MemoTextWatcher(var position: Int) : TextWatcher {

        override fun afterTextChanged(p0: Editable?) {
            when(position) {
                0 -> AppSetting.prefs.setTmpString0(listData[position])
                1 -> AppSetting.prefs.setTmpString1(listData[position])
                2 -> AppSetting.prefs.setTmpString2(listData[position])
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            listData[position] = p0.toString()
        }
    }

}