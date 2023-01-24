package com.iitgoacepheustwth.cepheus23.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitgoacepheustwth.cepheus23.adapter.SchedulesFragmentAdapter
import com.iitgoacepheustwth.cepheus23.databinding.FragmentSchedulesBinding
import com.google.android.material.tabs.TabLayoutMediator

class Schedules : Fragment() {

    companion object {
        fun newInstance() = Schedules()
    }
    private lateinit var binding: FragmentSchedulesBinding
    private var tabTitle = arrayOf("Wed\n08", "Thu\n09", "Fri\n10", "Sat\n11", "Sun\n12")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchedulesBinding.inflate(inflater, container, false)

        var pagerSchedules = binding.viewpager2
        var tlSchedules = binding.tabLayout
        pagerSchedules.adapter = SchedulesFragmentAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(tlSchedules, pagerSchedules) {
                tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}