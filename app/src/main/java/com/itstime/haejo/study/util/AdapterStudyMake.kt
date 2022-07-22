package com.itstime.haejo.study.util

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.itstime.haejo.R
import com.itstime.haejo.databinding.ActivityStudyMakeBinding

class AdapterStudyMake(val binding: ActivityStudyMakeBinding) {

    //스피너에 어댑터 추가 (기본 설정)
    fun spinnerAdapterConnect() {
        makeSpinnerAdapter(binding.spinnerRegion, R.array.region)
        makeSpinnerAdapter(binding.spinnerCategory, R.array.category)
        makeSpinnerAdapter(binding.spinnerWeek, R.array.week)
        makeSpinnerAdapter(binding.spinnerPeopleAmount, R.array.peopleAmount)
        makeSpinnerAdapter(binding.spinnerQuestionAmount, R.array.questionAmount)
        makeSpinnerAdapter(binding.spinnerisUntact, R.array.isUntact)
    }

    //스피너 어댑터 추가용 함수
    private fun makeSpinnerAdapter(spinner: Spinner, array: Int) {
        spinner.adapter =
            ArrayAdapter.createFromResource(binding.root.context, array, R.layout.custom_spinner_font)
                .also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                }
    }

    //각 스피너에 기본 값이 아닌 특정 값을 넣었을 때 보라색 스피너로 변화하도록 설정
    //기본 값 position == 0 일 때는 회색 스피너 그대로
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

        binding.spinnerisUntact.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0) {
                    binding.spinnerisUntact.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_selected
                    )
                }
                else {
                    binding.spinnerisUntact.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_unselected
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerQuestionAmount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0) {
                    binding.spinnerQuestionAmount.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_selected
                    )
                }
                else {
                    binding.spinnerQuestionAmount.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_unselected
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerPeopleAmount.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0) {
                    binding.spinnerPeopleAmount.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_selected
                    )
                }
                else {
                    binding.spinnerPeopleAmount.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_unselected
                    )
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.spinnerCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position != 0) {
                    binding.spinnerCategory.background = ContextCompat.getDrawable(
                        binding.root.context,
                        R.drawable.custom_spinner_selected
                    )
                }
                else {
                    binding.spinnerCategory.background = ContextCompat.getDrawable(
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
}