package com.itstime.haejo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itstime.haejo.R
import com.itstime.haejo.databinding.FragmentMainApplicationBinding


class MainApplicationFragment : Fragment() {

    lateinit var binding : FragmentMainApplicationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainApplicationBinding.inflate(inflater, container, false)



        return binding.root
    }


}