package com.example.cepheus23.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cepheus23.fragments.*

class SchedulesFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> Day1Schedule()
            1 -> Day2Schedule()
            2 -> Day3Schedule()
            3 -> Day4Schedule()
            4 -> Day5Schedule()
            else -> Day1Schedule()
        }
    }
}
