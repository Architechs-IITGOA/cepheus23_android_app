package com.iitgoacepheustwth.cepheus23.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitgoacepheustwth.cepheus23.EventsData.EventData
import com.iitgoacepheustwth.cepheus23.EventsData.setData
import com.iitgoacepheustwth.cepheus23.adapter.EventAdapter
import com.iitgoacepheustwth.cepheus23.databinding.FragmentWorkshopsInEventsBinding

class WorkshopsInEventsFragment : Fragment() {

    companion object {
        fun newInstance() = WorkshopsInEventsFragment()
    }
    private lateinit var binding : FragmentWorkshopsInEventsBinding

    private lateinit var eventInEventRecycler: RecyclerView
    private lateinit var eventsInEventRecyclerLayoutManager: RecyclerView.LayoutManager
    private lateinit var eventsInEventRecyclerAdapter: EventAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkshopsInEventsBinding.inflate(inflater, container, false)

        eventInEventRecycler=binding.workshopsInEventRecycler
        eventsInEventRecyclerLayoutManager = LinearLayoutManager(activity)
        eventInEventRecycler.layoutManager = eventsInEventRecyclerLayoutManager
//        val finalRes: MutableList<EventData> = mutableListOf()
        val workshopList : List<EventData> = setData.SetEvents().filter{ it.type == 3 }
        eventsInEventRecyclerAdapter = EventAdapter(context!!, workshopList,1)
        eventInEventRecycler.adapter = eventsInEventRecyclerAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        // TODO: Use the ViewModel
    }

}