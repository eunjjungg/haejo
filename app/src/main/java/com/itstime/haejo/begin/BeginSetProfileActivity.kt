package com.itstime.haejo.begin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.itstime.haejo.MainActivity
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.PostUserResult
import com.itstime.haejo.api.UploadUserModel
import com.itstime.haejo.databinding.ActivityBeginSetProfileBinding
import com.itstime.haejo.util.AppSetting
import com.itstime.haejo.util.UtilFunctions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BeginSetProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityBeginSetProfileBinding

    private val UNCHECK = -1
    private val PROFILE1 = 0
    private val PROFILE2 = 1
    private val PROFILE3 = 2
    private val PROFILE4 = 3

    private var userProfile: Int = UNCHECK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginSetProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            if(checkAnswerCorrectness()) {
                uploadUserDataToServer()
            }
        }
        binding.imgProfile1.setOnClickListener {
            setAllImageUnSelected()
            binding.imgProfile1.setBackgroundColor(Color.GRAY)
            userProfile = PROFILE1
        }
        binding.imgProfile2.setOnClickListener {
            setAllImageUnSelected()
            binding.imgProfile2.setBackgroundColor(Color.GRAY)
            userProfile = PROFILE2
        }
        binding.imgProfile3.setOnClickListener {
            setAllImageUnSelected()
            binding.imgProfile3.setBackgroundColor(Color.GRAY)
            userProfile = PROFILE3
        }
        binding.imgProfile4.setOnClickListener {
            setAllImageUnSelected()
            binding.imgProfile4.setBackgroundColor(Color.GRAY)
            userProfile = PROFILE4
        }
    }

    private fun setAllImageUnSelected() {
        binding.imgProfile1.setBackgroundColor(Color.WHITE)
        binding.imgProfile2.setBackgroundColor(Color.WHITE)
        binding.imgProfile3.setBackgroundColor(Color.WHITE)
        binding.imgProfile4.setBackgroundColor(Color.WHITE)
    }

    private fun checkAnswerCorrectness(): Boolean {
        if(userProfile == UNCHECK) {
            Toast.makeText(this, "프로필 사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (binding.etName.text.contains(" ")) {
            Toast.makeText(this, "닉네임에는 공백이 포함될 수 없습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        else if (binding.etName.text.length < 2) {
            Toast.makeText(this, "닉네임은 두 글자 이상입니다.", Toast.LENGTH_SHORT).show()
            return false
        }
        else
            return true
    }

    private fun uploadUserDataToServer() {
        AppSetting.prefs.setNickname(binding.etName.text.toString())
        AppSetting.prefs.setProfile(userProfile)
        val api = APIS.create()
        val uploadData = UploadUserModel(AppSetting.prefs.getEmail(), AppSetting.prefs.getName(), AppSetting.prefs.getNickname(), userProfile)
        val postUserData = api.postUser(uploadData)
        postUserData.enqueue(object : Callback<PostUserResult> {
            override fun onResponse(call: Call<PostUserResult>, response: Response<PostUserResult>) {
                Log.d("bsf server success", response.body().toString())
                AppSetting.prefs.setMemberId(response.body()!!.result!!.toInt())
                UtilFunctions().clearStackAndGoMain(binding.root.context)
            }

            override fun onFailure(call: Call<PostUserResult>, t: Throwable) {
                Toast.makeText(binding.root.context, t.message.toString(), Toast.LENGTH_SHORT).show()
                Log.d("bsf server error", t.message.toString())
            }

        })
    }

}