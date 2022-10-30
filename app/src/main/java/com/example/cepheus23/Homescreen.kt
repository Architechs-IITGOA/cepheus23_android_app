package com.example.cepheus23

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.cepheus23.databinding.HomelayoutBinding
import com.example.cepheus23.fragments.EventsFragment
import com.google.android.material.navigation.NavigationView


class Homescreen : AppCompatActivity() {
    private lateinit var binding: HomelayoutBinding

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomelayoutBinding.inflate(layoutInflater)
        binding.cepheuslogo.setOnClickListener(EventsFragment)
        setContentView(binding.root)

        val drawerLayout:DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}