package com.itstime.haejo.study.ongoing

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioGroup
import android.widget.Toast
import com.itstime.haejo.R
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostRatingDTO
import com.itstime.haejo.api.PostRatingResultDTO
import com.itstime.haejo.databinding.CustomDialogRatingBinding
import com.itstime.haejo.databinding.CustomRecyclerRatingBinding
import com.itstime.haejo.util.AppSetting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OngoingRatingDialog() {
    fun showDialog(binding: CustomRecyclerRatingBinding, recipientId: Long) {
        val ratingDialogView = LayoutInflater.from(binding.root.context).inflate(R.layout.custom_dialog_rating, null)
        val ratingDialogBuilder = AlertDialog.Builder(binding.root.context)
            .setView(ratingDialogView)
        val radioGroup = ratingDialogView.findViewById<RadioGroup>(R.id.radioRating)
        var checked = -1

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.radio1 -> checked = 1
                R.id.radio2 -> checked = 2
                R.id.radio3 -> checked = 3
                R.id.radio4 -> checked = 4
                R.id.radio5 -> checked = 5
            }
        }
        ratingDialogBuilder.setPositiveButton("OK", { dialog, whichButton ->
            Toast.makeText(binding.root.context,
                "소중한 평가가 반영되었어요!",
                Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            postRatingToServer(checked, recipientId)
        })
        ratingDialogBuilder.setNegativeButton("Cancel", { dialog, whichButton ->
            dialog.dismiss()
        })
        ratingDialogBuilder.show()
    }

    fun postRatingToServer(checked: Int, recipientId: Long) {
        val api = APIS.create()
        val senderId = AppSetting.prefs.getMemberId()
        val tmpDTO = PostRatingDTO(senderId.toLong(), checked)

        api.postRating(recipientId, tmpDTO).enqueue(object : Callback<PostRatingResultDTO> {
            override fun onResponse(
                call: Call<PostRatingResultDTO>,
                response: Response<PostRatingResultDTO>
            ) {
                Log.d("ord server1 succ", response.body().toString())
            }

            override fun onFailure(call: Call<PostRatingResultDTO>, t: Throwable) {
                Log.d("ord server1 fail", t.message.toString())
            }

        })
    }
}