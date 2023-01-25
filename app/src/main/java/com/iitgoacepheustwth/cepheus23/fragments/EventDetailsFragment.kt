package com.iitgoacepheustwth.cepheus23.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iitgoacepheustwth.cepheus23.EventsData.EventData

class EventDetailsFragment : Fragment() {

    private lateinit var detailsBinding: com.iitgoacepheustwth.cepheus23.databinding.FragmentEventDetailsBinding
    private var obj: EventData ?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding = com.iitgoacepheustwth.cepheus23.databinding.FragmentEventDetailsBinding.inflate(inflater, container, false)
        obj?.let{
            detailsBinding.eventName.setText(it.eventName)
        }
        return detailsBinding.root


    }

}