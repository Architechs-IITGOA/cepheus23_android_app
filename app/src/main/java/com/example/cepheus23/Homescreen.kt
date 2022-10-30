package com.example.cepheus23

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController

import com.example.cepheus23.databinding.HomelayoutBinding
import com.example.cepheus23.fragments.EventsFragment
import com.google.android.material.navigation.NavigationView


class Homescreen : AppCompatActivity() {
    private lateinit var binding: HomelayoutBinding

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomelayoutBinding.inflate(layoutInflater)
        binding.cepheuslogo.setOnClickListener(EventsFragment)
        setContentView(binding.root)

        val drawerLayout:DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayShowTitleEnabled(false)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavView
        setupWithNavController(bottomNavigationView,navController)











    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}