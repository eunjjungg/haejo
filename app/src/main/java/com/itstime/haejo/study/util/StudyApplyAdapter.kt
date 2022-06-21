package com.itstime.haejo.study.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itstime.haejo.R
import kotlinx.android.synthetic.main.activity_study_apply.view.*
import kotlinx.android.synthetic.main.custom_recycler_apply.view.*

class StudyApplyAdapter(val surveyData: SurveyData, val context: Context) :
    RecyclerView.Adapter<StudyApplyAdapter.ViewHolder>() {
    private val activity : Activity = context as Activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyApplyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_recycler_apply, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: StudyApplyAdapter.ViewHolder, position: Int) {
        holder.bind(surveyData.items.get(position))
        holder.itemView.setOnClickListener {


//            activity.finish()
        }
    }

    override fun getItemCount(): Int {
        return surveyData.items.count()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Item) {
            itemView?.tvSurveyTitle?.text = data.SurveyTitle
        }
    }
}