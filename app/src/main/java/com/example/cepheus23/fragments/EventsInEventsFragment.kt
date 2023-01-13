package com.example.cepheus23.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cepheus23.EventsData.EventData
import com.example.cepheus23.EventsData.setData
import com.example.cepheus23.adapter.EventAdapter
import com.example.cepheus23.databinding.FragmentEventsInEventsBinding

class EventsInEventsFragment : Fragment() {

    companion object {
        fun newInstance() = EventsInEventsFragment()
    }
    private lateinit var binding : FragmentEventsInEventsBinding

    private lateinit var eventInEventRecycler: RecyclerView
    private lateinit var eventsInEventRecyclerLayoutManager: RecyclerView.LayoutManager
    private lateinit var eventsInEventRecyclerAdapter: EventAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsInEventsBinding.inflate(inflater, container, false)

        eventInEventRecycler=binding.eventInEventRecycler
        eventsInEventRecyclerLayoutManager = LinearLayoutManager(activity)
        eventInEventRecycler.layoutManager = eventsInEventRecyclerLayoutManager
//        val finalRes: MutableList<EventData> = mutableListOf()
        val eventList : List<EventData> = setData.SetEvents().filter{ it.id == 1}
        eventsInEventRecyclerAdapter = EventAdapter(eventList)
        eventInEventRecycler.adapter = eventsInEventRecyclerAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        // TODO: Use the ViewModel
    }

}