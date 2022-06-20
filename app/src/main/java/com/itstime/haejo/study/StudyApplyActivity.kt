package com.itstime.haejo.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.R
import com.itstime.haejo.databinding.ActivityStudyApplyBinding
import com.itstime.haejo.study.util.Item
import com.itstime.haejo.study.util.StudyApplyAdapter
import com.itstime.haejo.study.util.SurveyData

class StudyApplyActivity : AppCompatActivity() {
    val binding by lazy { ActivityStudyApplyBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        val str1 = Item("1st Survey")
        val str3 = Item("3rd Survey")
        val str2 = Item("2nd Survey")

        val lst = listOf<Item>(str1, str2, str3)
        val sd = SurveyData(lst)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = StudyApplyAdapter(sd, this)
    }
}