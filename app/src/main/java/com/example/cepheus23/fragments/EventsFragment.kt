package com.example.cepheus23.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.cepheus23.EventsData.EventData
import com.example.cepheus23.EventsData.setData
import com.example.cepheus23.R
import com.example.cepheus23.adapter.EventAdapter
import com.example.cepheus23.adapter.EventsFragmentAdapter
import com.example.cepheus23.databinding.FragmentEventsBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import java.util.Arrays.toString

class EventsFragment : Fragment() {
    lateinit var eventRecycler: RecyclerView
    lateinit var eventsRecyclerLayoutManager: RecyclerView.LayoutManager
    lateinit var eventsRecyclerAdapter: EventAdapter
    private var tabTitle = arrayOf("Online Competitions", "Offline Competitions", "Workshops")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val eventsBinding = FragmentEventsBinding.inflate(inflater, container, false)

//        eventRecycler=eventsBinding.viewEventRecycler
//        eventsRecyclerLayoutManager = LinearLayoutManager(activity)
//        eventRecycler.layoutManager = eventsRecyclerLayoutManager
////        val finalRes: MutableList<EventData> = mutableListOf()
//        eventsRecyclerAdapter = EventAdapter(setData.SetEvents())
//        eventRecycler.adapter = eventsRecyclerAdapter

        var pager = eventsBinding.viewpager2
        var tl = eventsBinding.tabLayout
        pager.adapter = EventsFragmentAdapter(childFragmentManager, lifecycle)

        TabLayoutMediator(tl, pager) {
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        return eventsBinding.root
    }
//    private fun onItemClicked(eventData: EventData){
//        val detailsBundle = Bundle()
//        detailsBundle.putParcelable("Event", eventData)
//                Log.i("function", "onItemClicked: running")
//
//        findNavController().navigate(R.id.action_EventsFragment_to_EventDetailsFragment, detailsBundle)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}