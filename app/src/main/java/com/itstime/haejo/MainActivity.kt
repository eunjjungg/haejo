package com.itstime.haejo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.itstime.haejo.databinding.ActivityMainBinding
import com.itstime.haejo.fragment.MainApplicationFragment
import com.itstime.haejo.fragment.MainChatFragment
import com.itstime.haejo.fragment.MainHomeFragment
import com.itstime.haejo.fragment.MainMypageFragment

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
            }
            binding.btnApplication.id -> {
                transaction.replace(binding.fragmentView.id, MainApplicationFragment())
                transaction.commit()
            }
            binding.btnChat.id -> {
                transaction.replace(binding.fragmentView.id, MainChatFragment())
                transaction.commit()
            }
            binding.btnMypage.id -> {
                transaction.replace(binding.fragmentView.id, MainMypageFragment())
                transaction.commit()
            }
        }
    }
}