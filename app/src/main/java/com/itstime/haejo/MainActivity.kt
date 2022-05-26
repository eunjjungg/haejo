package com.itstime.haejo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.itstime.haejo.databinding.ActivityMainBinding
import com.itstime.haejo.main.MainApplicationFragment
import com.itstime.haejo.main.MainChatFragment
import com.itstime.haejo.main.MainHomeFragment
import com.itstime.haejo.main.MainMypageFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fragment setting
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.fragmentView.id, MainHomeFragment())
        transaction.commit()
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