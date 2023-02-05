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
import com.iitgoacepheustwth.cepheus23.databinding.FragmentOfflineCompetitionsInEventBinding

class OfflineCompetitionsInEventFragment : Fragment() {

    companion object {
        fun newInstance() = OfflineCompetitionsInEventFragment()
    }
    private lateinit var binding : FragmentOfflineCompetitionsInEventBinding

    private lateinit var eventRecycler: RecyclerView
    private lateinit var eventsRecyclerLayoutManager: RecyclerView.LayoutManager
    private lateinit var eventsRecyclerAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfflineCompetitionsInEventBinding.inflate(inflater, container, false)
        eventRecycler=binding.offlineCompetitionsInEventRecycler
        eventsRecyclerLayoutManager = LinearLayoutManager(activity)
        eventRecycler.layoutManager = eventsRecyclerLayoutManager
//        val finalRes: MutableList<EventData> = mutableListOf()
        val offlineCompetitionsList : List<EventData> = setData.SetEvents().filter{ it.type  == 2 }
        eventsRecyclerAdapter = EventAdapter(context!!, offlineCompetitionsList,1)
        eventRecycler.adapter = eventsRecyclerAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}