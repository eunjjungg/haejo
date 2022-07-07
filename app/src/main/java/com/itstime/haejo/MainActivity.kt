package com.itstime.haejo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.itstime.haejo.databinding.ActivityMainBinding
import com.itstime.haejo.main.MainApplicationFragment
import com.itstime.haejo.main.MainChatFragment
import com.itstime.haejo.main.MainHomeFragment
import com.itstime.haejo.main.MainMypageFragment
import com.itstime.haejo.util.AppSetting


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fragment 초기 화면 setting: 디폴트는 홈화면으로 
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.fragmentView.id, MainHomeFragment())
        transaction.commit()
        setThisViewIcUnselected()
        binding.btnHome.setImageResource(R.drawable.ic_home_selected)
    }


    //하단바 onClick: 각 프래그먼트로 이동
    override fun onClick(p0: View?) {
        val transaction = supportFragmentManager.beginTransaction()

        when(p0?.id) {
            binding.btnHome.id -> {
                transaction.replace(binding.fragmentView.id, MainHomeFragment())
                transaction.commit()
                setThisViewIcUnselected()
                binding.btnHome.setImageResource(R.drawable.ic_home_selected)
            }
            binding.btnApplication.id -> {
                transaction.replace(binding.fragmentView.id, MainApplicationFragment())
                transaction.commit()
                setThisViewIcUnselected()
                binding.btnApplication.setImageResource(R.drawable.ic_application_selected)
            }
            binding.btnChat.id -> {
                transaction.replace(binding.fragmentView.id, MainChatFragment())
                transaction.commit()
                setThisViewIcUnselected()
                binding.btnChat.setImageResource(R.drawable.ic_chat_selected)
            }
            binding.btnMypage.id -> {
                transaction.replace(binding.fragmentView.id, MainMypageFragment())
                transaction.commit()
                setThisViewIcUnselected()
                binding.btnMypage.setImageResource(R.drawable.ic_mypage_selected)
            }
        }
    }

    private fun setThisViewIcUnselected() {
        binding.btnApplication.setImageResource(R.drawable.ic_application)
        binding.btnChat.setImageResource(R.drawable.ic_chat)
        binding.btnHome.setImageResource(R.drawable.ic_home)
        binding.btnMypage.setImageResource(R.drawable.ic_mypage)
    }
}