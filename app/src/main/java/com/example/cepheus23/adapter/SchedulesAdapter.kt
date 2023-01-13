package com.example.cepheus23.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cepheus23.EventsData.ScheduleEvent
import com.example.cepheus23.R
import java.lang.reflect.Constructor

class SchedulesAdapter(private val context: Context, private val schedulesEventList: ArrayList<ScheduleEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class scheduleEventViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
//        var event_name = binding.schedulesEventName
//        var time = binding.schedulesEventTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder = scheduleEventViewHolder(LayoutInflater.from(context).inflate(R.layout.schedule_element, parent, false))

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
