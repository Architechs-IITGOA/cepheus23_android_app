package com.example.cepheus23.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
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
            Log.i("adapter",event[position].overview.toString())
            Log.i("adapter",event[position].eventName.toString())
        }
        holder.eventName.text = event[position].eventName
        holder.eventOverview.text = event[position].overview





        when (event[position].eventName!!) {
            "Loren Ipsum" -> {
                eventImage = R.drawable.loremipsum1
            }
            "HackOverFlow" -> {
                eventImage = R.drawable.hackoverflow1
            }
            "Circuital Dilemma" -> {
                eventImage = R.drawable.circuitaldilema1
            }
            "Data Science Hackathon" -> {
                eventImage = R.drawable.datascihack1
            }
            "HackTheGames" -> {
                eventImage = R.drawable.hackthegame1
            }
            "CTF" -> {
                eventImage = R.drawable.ctf1
            }
            "FizzBuzz" -> {
                eventImage = R.drawable.fizzbuzz1
            }
            "Online Treasure Hunt" -> {
                eventImage = R.drawable.onlinethunt1
            }
            "Bridge Building Comeptition" -> {
                eventImage = R.drawable.bridgebuilding1
            }
            "Copy the Nature" -> {
                eventImage = R.drawable.copythenature1
            }
            "Rule The Market" -> {
                eventImage = R.drawable.rulethemarket1
            }
            "Launch Galaset" -> {
                eventImage = R.drawable.launchgala1
            }
            "KBC Quiz Competition" -> {
                eventImage = R.drawable.kbc1
            }
            "Maze Amaze" -> {
                eventImage = R.drawable.mazeamaze1
            }
            "Scratch" -> {
                eventImage = R.drawable.scratch1
            }
            "Treasure Hunt" -> {
                eventImage = R.drawable.treashunt1
            }
            "Buy My Code" -> {
                eventImage = R.drawable.buymycode1
            }
            "Pare It Down" -> {
                eventImage = R.drawable.pairitdown1
            }
            "Game Theory" -> {
                eventImage = R.drawable.gametheory1
            }
            "Arduino's Trial" -> {
                eventImage = R.drawable.arduinotrial1
            }
            "EV Bike Competition" -> {
                eventImage = R.drawable.ebike1
            }

            else -> {
                eventImage = R.drawable.h
            }

        }

        holder.eventListImage.setImageResource(eventImage!!)
    }

    override fun getItemCount(): Int {
        return event.size
    }
}