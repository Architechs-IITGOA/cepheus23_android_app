package com.example.cepheus23.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cepheus23.EventsData.ScheduleEvent
import com.example.cepheus23.EventsData.setData
import com.example.cepheus23.R
import com.example.cepheus23.adapter.EventAdapter
import com.example.cepheus23.adapter.EventsFragmentAdapter
import com.example.cepheus23.adapter.SchedulesAdapter
import com.example.cepheus23.adapter.SchedulesFragmentAdapter
import com.example.cepheus23.databinding.FragmentEventsBinding
import com.example.cepheus23.databinding.FragmentSchedulesBinding
import com.google.android.material.tabs.TabLayoutMediator

class Schedules : Fragment() {

    companion object {
        fun newInstance() = Schedules()
    }
    private lateinit var binding: FragmentSchedulesBinding
    private var tabTitle = arrayOf("Wed\n08", "Thu\n09", "Fri\n10", "Sat\n11", "Sun\n12")
    val schedulesEventList: ArrayList<ScheduleEvent> = ArrayList<ScheduleEvent>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchedulesBinding.inflate(inflater, container, false)
//        binding.viewScheduleRecycler.layoutManager = LinearLayoutManager(activity)

        var pagerSchedules = binding.viewpager2
        var tlSchedules = binding.tabLayout
        pagerSchedules.adapter = SchedulesFragmentAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(tlSchedules, pagerSchedules) {
                tab, position ->
            tab.text = tabTitle[position]
        }.attach()




//        val adapter = SchedulesAdapter(context!!, schedulesEventList)
//        binding.viewScheduleRecycler.adapter=adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}