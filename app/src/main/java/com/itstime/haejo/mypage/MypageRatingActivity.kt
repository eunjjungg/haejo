package com.itstime.haejo.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itstime.haejo.databinding.ActivityMypageRatingBinding

class MypageRatingActivity : AppCompatActivity() {
    lateinit var binding : ActivityMypageRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}