package com.example.cepheus23

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController

import com.example.cepheus23.databinding.HomelayoutBinding
import com.google.android.material.navigation.NavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter


class Homescreen : AppCompatActivity() {
    private lateinit var binding: HomelayoutBinding

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomelayoutBinding.inflate(layoutInflater)
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
        Log.i("message", "nav bars working")

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.sidenav_sponsors ->Toast.makeText(applicationContext,"Sponsors",Toast.LENGTH_SHORT).show()
                R.id.sidenav_con -> Toast.makeText(applicationContext,"Contacts",Toast.LENGTH_SHORT).show()
                R.id.sidenav_merch -> {
                    Log.i("error","1")
                    val merchurl = Intent(android.content.Intent.ACTION_VIEW)
                    Log.i("error","2")

                    merchurl.data = Uri.parse("https://cepheusmemories.co.in/")
                    Log.i("error","3")

                    startActivity(merchurl)
                    Log.i("error","4")

                }
                R.id.sidenav_faqs -> Toast.makeText(applicationContext,"FAQS",Toast.LENGTH_SHORT).show()
                R.id.sidenav_signout-> Toast.makeText(applicationContext,"Sign out",Toast.LENGTH_SHORT).show()
            }
            true
        }


//        implementing QR
        val header: View = navView.getHeaderView(0)
        val QRimageview : ImageView = header.findViewById(R.id.Qrbox)
        val useremail: String = "vivek.bandrele.20031@iitgoa.ac.in"
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(useremail, BarcodeFormat.QR_CODE, 200,200)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for(x in 0 until width){
                for(y in 0 until height){
                    bmp.setPixel(x,y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
            QRimageview.setImageBitmap(bmp)

        }catch (e: WriterException){
            e.printStackTrace()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
