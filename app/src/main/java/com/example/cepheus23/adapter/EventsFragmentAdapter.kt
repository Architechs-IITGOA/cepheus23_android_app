package com.example.cepheus23.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cepheus23.fragments.OfflineCompetitionsInEventFragment
import com.example.cepheus23.fragments.OnlineCompetitionsInEventFragment
import com.example.cepheus23.fragments.WorkshopsInEventsFragment

class EventsFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> OnlineCompetitionsInEventFragment()
            1 -> OfflineCompetitionsInEventFragment()
            2 -> WorkshopsInEventsFragment()
            else -> OnlineCompetitionsInEventFragment()
        }
    }
}