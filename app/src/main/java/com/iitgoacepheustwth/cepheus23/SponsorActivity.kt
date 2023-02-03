package com.iitgoacepheustwth.cepheus23

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitgoacepheustwth.cepheus23.adapter.SponsorAdapter
import com.iitgoacepheustwth.cepheus23.model.SponsorData
import com.google.firebase.database.*
import com.iitgoacepheustwth.cepheus23.databinding.ActivitySponsorBinding

class SponsorActivity : AppCompatActivity() {
//    private lateinit var recyclerView : RecyclerView
//    private lateinit var listSpo : ArrayList<SponsorData>
//    private lateinit var databaseReference : DatabaseReference

    private lateinit var binding: ActivitySponsorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
// Removes Dark mode
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    supportActionBar?.setDisplayShowTitleEnabled(false)
    super.onCreate(savedInstanceState)
        binding = ActivitySponsorBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setContentView(R.layout.fragment_sponsor)
//    setContentView(R.layout.sponsor_webview)
//    val intent = Intent(Intent.ACTION_VIEW)
//    intent.data = Uri.parse("https://iitgoa.ac.in/cepheus/#Sponsors")
//    startActivity(intent)
//
//    val contactintent = Intent(this@SponsorActivity,Homescreen::class.java)
//    startActivity(contactintent)
//    val intent3 = Intent(Intent.ACTION_VIEW)
//    intent.data = Uri.parse("https://iitgoa.ac.in/cepheus/#About")
//    startActivity(intent3)
//    val myWeb = findViewById<WebView>(R.id.MyWebView)
//    myWeb.webViewClient = WebViewClient()
//
//    // 3rd, Run webview loading code.
//    myWeb.apply {
//        loadUrl("https://iitgoa.ac.in/cepheus/")  //https://iitgoa.ac.in/cepheus/#Sponsors
//        settings.javaScriptEnabled = true
//    }
        }

}
//        Log.i("sponsors", "sponsor activity initialised")
//        recyclerView = findViewById(R.id.recycler)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        listSpo = arrayListOf<SponsorData>()



//        Log.i("spcheck","1")
//        checkper // Not needed
  //      Log.i("spcheck","2")
    //    binding.sponsor1.setOnClickListener {
      //      val phone1 = "9770594125"
        //    val callintent1 = Intent(Intent.ACTION_VIEW)
          //  callintent1.data = Uri.parse("tel:$phone1")
            //startActivity(callintent1)
       // }


//        Log.i("sponsors", "firebase call initialised")
//        databaseReference = FirebaseDatabase.getInstance().getReference("sponsor")
//        Log.i("sponsors", "firebase call complete")
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for (dataSnapShot in snapshot.children){
//                        val list = dataSnapShot.getValue(SponsorData::class.java)
//                        listSpo.add(list!!)
//                    }
//
//                    Log.i("sponsors", "found the adpter")
//
//                    val spAdapter = SponsorAdapter(listSpo)
//                    recyclerView.adapter = spAdapter
//
////                    recyclerView.adapter = SponsorAdapter(listSpo)
//
//                    Log.i("sponsors", "adapter initialised")
//                }
//                else{
//                    Log.i("sponsors", "data nhi aya")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.i("sponsors", "error")
//                Toast.makeText(this@SponsorActivity,error.toString(), Toast.LENGTH_SHORT)
//                    .show()
//            }

//        })

//        supportActionBar?.setDisplayShowTitleEnabled(false)
//
//
//        Log.i("spcheck","1")
//        checkper()
//        Log.i("spcheck","2")
//        binding.sponsor1.setOnClickListener {
//            val phone1 = "9770594125"
//            val callintent1 = Intent(Intent.ACTION_VIEW)
//            callintent1.data = Uri.parse("tel:$phone1")
//            startActivity(callintent1)
//        }
//
//        binding.sponsor2.setOnClickListener {
//            val phone2 = "9479558767"
//            Log.i("spcheck","3")
//            val callintent2 = Intent(Intent.ACTION_VIEW)
//            callintent2.data = Uri.parse("tel:$phone2")
//            Log.i("spcheck","4")
//            startActivity(callintent2)
//
//        }
//    }

//
//    private fun checkper(){
//        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),101)
//        }
//    }
//}