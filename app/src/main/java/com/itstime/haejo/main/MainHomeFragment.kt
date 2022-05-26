package com.itstime.haejo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.itstime.haejo.R
import com.itstime.haejo.databinding.FragmentMainHomeBinding

class MainHomeFragment : Fragment() {

    lateinit var binding : FragmentMainHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        return binding.root

        val regionArr = resources.getStringArray(R.array.region)
        val regionAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, regionArr)
        binding.spinnerRegion.adapter = regionAdapter

        val weekArr = resources.getStringArray(R.array.week)
        val weekAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, weekArr)
        binding.spinnerWeek.adapter = weekAdapter
    }


}