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
import com.example.cepheus23.databinding.FragmentCompetitionsInEventBinding
import com.example.cepheus23.databinding.FragmentWorkshopInEventFragementBinding

class WorkshopInEventFragement : Fragment() {

    companion object {
        fun newInstance() = WorkshopInEventFragement()
    }
    private lateinit var binding:FragmentWorkshopInEventFragementBinding

    private lateinit var eventRecycler: RecyclerView
    private lateinit var eventsRecyclerLayoutManager: RecyclerView.LayoutManager
    private lateinit var eventsRecyclerAdapter: EventAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkshopInEventFragementBinding.inflate(inflater, container, false)
        eventRecycler=binding.workshopInEventRecycler
        eventsRecyclerLayoutManager = LinearLayoutManager(activity)
        eventRecycler.layoutManager = eventsRecyclerLayoutManager
//        val finalRes: MutableList<EventData> = mutableListOf()
        val workshopsList : List<EventData> = setData.SetEvents().filter{ it.id  == 3 }
        eventsRecyclerAdapter = EventAdapter(workshopsList)
        eventRecycler.adapter = eventsRecyclerAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}