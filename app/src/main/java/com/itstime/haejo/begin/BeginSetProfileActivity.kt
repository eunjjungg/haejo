package com.itstime.haejo.begin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itstime.haejo.databinding.ActivityBeginSetProfileBinding

class BeginSetProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityBeginSetProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginSetProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}