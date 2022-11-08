package com.example.cepheus23.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cepheus23.EventsData.EventData

class EventDetailsFragment : Fragment() {

    private lateinit var detailsBinding: com.example.cepheus23.databinding.FragmentEventDetailsBinding
    private var obj: EventData ?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsBinding = com.example.cepheus23.databinding.FragmentEventDetailsBinding.inflate(inflater, container, false)
        obj?.let{
            detailsBinding.tvEventName.setText(it.eventName)
            detailsBinding.tvEventDetails.setText(it.overview)
        }
        return detailsBinding.root
    }

}