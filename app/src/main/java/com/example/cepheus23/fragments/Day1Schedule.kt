package com.example.cepheus23.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cepheus23.EventsData.EventData
import com.example.cepheus23.EventsData.setData
import com.example.cepheus23.R
import com.example.cepheus23.adapter.EventAdapter
import com.example.cepheus23.adapter.SchedulesAdapter
import com.example.cepheus23.databinding.FragmentDay1ScheduleBinding
import com.example.cepheus23.databinding.FragmentOfflineCompetitionsInEventBinding

class Day1Schedule : Fragment() {

    companion object {
        fun newInstance() = Day1Schedule()
    }

    private lateinit var binding : FragmentDay1ScheduleBinding

    private lateinit var eventRecycler: RecyclerView
    private lateinit var eventsRecyclerLayoutManager: RecyclerView.LayoutManager
    private lateinit var eventsRecyclerAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDay1ScheduleBinding.inflate(inflater, container, false)
        eventRecycler=binding.day1Recycler
//        eventsRecyclerLayoutManager = LinearLayoutManager(activity)
//        eventRecycler.layoutManager = eventsRecyclerLayoutManager
////        val finalRes: MutableList<EventData> = mutableListOf()
//        val offlineCompetitionsList : List<EventData> = setData.SetEvents().filter{ it.type  == 1 }
////        eventsRecyclerAdapter = SchedulesAdapter(context!!, )
//        eventRecycler.adapter = eventsRecyclerAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}