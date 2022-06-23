package com.itstime.haejo.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.itstime.haejo.R
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostResult
import com.itstime.haejo.api.UserModel
import com.itstime.haejo.databinding.FragmentMainHomeBinding
import com.itstime.haejo.study.StudyMakeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainHomeFragment : Fragment() {

    lateinit var binding : FragmentMainHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainHomeBinding.inflate(inflater, container, false)

        spinnerAdapterConnect()
        spinnerSelection()
        checkApi()

        binding.fltbtnMakeStudy.setOnClickListener {
            startActivity(Intent(binding.root.context, StudyMakeActivity::class.java))
        }

        return binding.root
    }

    //button setting
    fun checkApi() {
        binding.btnApiCheck.setOnClickListener {
            val mUser = FirebaseAuth.getInstance().currentUser
            val userData = UserModel(mUser!!.email, mUser!!.displayName)
            val api = APIS.create()

            api.post_user(userData).enqueue(object: Callback<PostResult>{
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    Log.d("api",response.toString())
                    Log.d("api", response.body().toString())
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
    
    //region, week spinner 연결
    private fun spinnerAdapterConnect() {
        makeSpinnerAdapter(binding.spinnerRegion, R.array.region)
        makeSpinnerAdapter(binding.spinnerWeek, R.array.week)
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
    }

}