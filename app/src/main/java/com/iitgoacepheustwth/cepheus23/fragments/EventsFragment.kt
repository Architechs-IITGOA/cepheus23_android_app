package com.iitgoacepheustwth.cepheus23.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.iitgoacepheustwth.cepheus23.adapter.EventAdapter
import com.iitgoacepheustwth.cepheus23.adapter.EventsFragmentAdapter
import com.iitgoacepheustwth.cepheus23.databinding.FragmentEventsBinding
import com.google.android.material.tabs.TabLayoutMediator

class EventsFragment : Fragment() {
    lateinit var eventRecycler: RecyclerView
    lateinit var eventsRecyclerLayoutManager: RecyclerView.LayoutManager
    lateinit var eventsRecyclerAdapter: EventAdapter
    private var tabTitle = arrayOf("Online\nCompetitions", "Offline\nCompetitions", "Workshops")

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