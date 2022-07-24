package com.itstime.haejo.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itstime.haejo.R
import com.itstime.haejo.api.APIS
import com.itstime.haejo.api.UserGetDTO
import com.itstime.haejo.databinding.FragmentMainMypageBinding
import com.itstime.haejo.mypage.*
import com.itstime.haejo.util.AppSetting
import com.itstime.haejo.util.UtilFunctions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainMypageFragment : Fragment() {

    lateinit var binding : FragmentMainMypageBinding
    lateinit var userGetDTO: UserGetDTO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainMypageBinding.inflate(inflater, container, false)

        getUserProfile(AppSetting.prefs.getMemberId().toLong())
        setEachTVtoPage()


        return binding.root
    }

    //mypage 연결 시 맨 처음으로 실행되는 부분 : 유저 프로필 초기화
    private fun getUserProfile(userId: Long) {
        var isSuccess: Boolean = false
        val api = APIS.create()
        api.getUser(userId).enqueue(object : Callback<UserGetDTO> {
            override fun onResponse(call: Call<UserGetDTO>, response: Response<UserGetDTO>) {
                Log.d("MMF server succ", response.toString())
                isSuccess = true
                userGetDTO = UserGetDTO(
                    response.body()!!.memberId,
                    response.body()!!.battery,
                    response.body()!!.email,
                    response.body()!!.name,
                    response.body()!!.nickname,
                    response.body()!!.profile
                )
                initUserProfile(userGetDTO)
            }

            override fun onFailure(call: Call<UserGetDTO>, t: Throwable) {
                Log.d("MMF server fail", t.message.toString())
            }

        })
    }

    private fun initUserProfile(userGetDTO: UserGetDTO) {
        Log.d("MMF server succ", "issuccess에 들어온 경우")
        when(userGetDTO.profile) {
            0 -> binding.imgProfile.setImageDrawable(resources.getDrawable(R.drawable.ic_profile_0))
            1 -> binding.imgProfile.setImageDrawable(resources.getDrawable(R.drawable.ic_profile_1))
            2 -> binding.imgProfile.setImageDrawable(resources.getDrawable(R.drawable.ic_profile_2))
            3 -> binding.imgProfile.setImageDrawable(resources.getDrawable(R.drawable.ic_profile_3))
        }
        binding.tvBattery.setText("${userGetDTO.nickname} 님의 열정 배터리는 ${userGetDTO.battery} % 입니다")

        when(userGetDTO.battery) {
            in 0..20 -> binding.imgBattery.setImageDrawable(resources.getDrawable(R.drawable.ic_battery_20))
            in 21..40 -> binding.imgBattery.setImageDrawable(resources.getDrawable(R.drawable.ic_battery_40))
            in 41..70 -> binding.imgBattery.setImageDrawable(resources.getDrawable(R.drawable.ic_battery_70))
            in 71..100 -> binding.imgBattery.setImageDrawable(resources.getDrawable(R.drawable.ic_battery_90))
        }
    }

    //각 tv click 시 페이지 넘어가도록 구현
    private fun setEachTVtoPage() {
        var intent: Intent

        binding.tvBookmark.setOnClickListener {
            intent = Intent(binding.root.context, MypageBookmarkActivity::class.java)
            startActivity(intent)
        }

        binding.tvComment.setOnClickListener {
            intent = Intent(binding.root.context, MypageCommentActivity::class.java)
            startActivity(intent)
        }

        binding.tvStudy.setOnClickListener {
            intent = Intent(binding.root.context, MypagePostActivity::class.java)
            startActivity(intent)
        }

        binding.tvCredit.setOnClickListener {
            intent = Intent(binding.root.context, MypageInfoActivity::class.java)
            startActivity(intent)
        }

        binding.tvReview.setOnClickListener {
            intent = Intent(binding.root.context, MypageRatingActivity::class.java)
            startActivity(intent)
        }

        binding.tvSignOut.setOnClickListener {
            AppSetting.prefs.apply {
                setEmail("null")
                setName("null")
                setNickname("null")
            }
            UtilFunctions().clearStackAndGoLoading(binding.root.context)
        }
    }

}