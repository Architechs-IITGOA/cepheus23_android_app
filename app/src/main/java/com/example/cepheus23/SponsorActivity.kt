package com.example.cepheus23

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.cepheus23.databinding.ActivitySponsorBinding
import java.util.jar.Manifest

class SponsorActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySponsorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySponsorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        Log.i("spcheck","1")
        checkper()
        Log.i("spcheck","2")
        binding.sponsor1.setOnClickListener {
            val phone1 = "9770594125"
            val callintent1 = Intent(Intent.ACTION_VIEW)
            callintent1.data = Uri.parse("tel:$phone1")
            startActivity(callintent1)
        }

        binding.sponsor2.setOnClickListener {
            val phone2 = "9479558767"
            Log.i("spcheck","3")
            val callintent2 = Intent(Intent.ACTION_VIEW)
            callintent2.data = Uri.parse("tel:$phone2")
            Log.i("spcheck","4")
            startActivity(callintent2)

        }
    }


    private fun checkper(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),101)
        }
    }
}