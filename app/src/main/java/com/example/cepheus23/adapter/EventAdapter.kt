package com.example.cepheus23.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cepheus23.DescriptionActivity
import com.example.cepheus23.EventsData.EventData
import com.example.cepheus23.R
import com.example.cepheus23.databinding.EventsElementBinding
//import com.example.cepheus23.databinding.FragmentEvent1Binding

class EventAdapter(var event: List<EventData>) : RecyclerView.Adapter<EventAdapter.eventViewHolder>(){

    inner class eventViewHolder(val binding: EventsElementBinding) : RecyclerView.ViewHolder(binding.root) {

        var eventName = binding.eventName
        var eventOverview = binding.eventDesc
        var eventListImage = binding.ivEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): eventViewHolder {
        var binding = EventsElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return eventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: eventViewHolder, position: Int) {
        var eventImage: Int? = null
        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context,DescriptionActivity::class.java)
            intent.putExtra("Event",event[position])
//            intent.putExtra("EventImage",eventImage)
            holder.itemView.context.startActivity(intent)
        }
        holder.eventName.text = event[position].eventName
        holder.eventOverview.text = event[position].overview

//        when (event[position].eventName!!) {
//            "Event 1" -> {
//                eventImage = R.drawable.ic_mars
//            }
//            "Event 2" -> {
//                eventImage = R.drawable.ic_neptune
//            }
//            "Event 2" -> {
//                eventImage = R.drawable.ic_earth
//            }
//            "Event 3" -> {
//                eventImage = R.drawable.ic_moon
//            }
//            "Event 4" -> {
//                eventImage = R.drawable.ic_venus
//            }
//            "Event 5" -> {
//                eventImage = R.drawable.ic_venus
//            }
//            "Event 6" -> {
//                eventImage = R.drawable.ic_saturn
//            }
//            "Event 7" -> {
//                eventImage = R.drawable.ic_uranus
//            }
//            "Event 8" -> {
//                eventImage = R.drawable.ic_mercury
//            }
//            "Event 9" -> {
//                eventImage = R.drawable.ic_sun
//            }
        }
//        holder.eventListImage.setImageResource(eventImage!!)
//    }

    override fun getItemCount(): Int {
        return event.size
    }
}