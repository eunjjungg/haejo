package com.itstime.haejo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.itstime.haejo.R
import com.itstime.haejo.databinding.FragmentMainChatBinding
import com.itstime.haejo.databinding.FragmentMainHomeBinding


class MainChatFragment : Fragment() {

    lateinit var binding : FragmentMainChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainChatBinding.inflate(inflater, container, false)

        return binding.root





    }


}