package com.iitgoacepheustwth.cepheus23

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout


import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.iitgoacepheustwth.cepheus23.databinding.HomelayoutBinding
import com.iitgoacepheustwth.cepheus23.model.Token
import com.iitgoacepheustwth.cepheus23.model.UserName
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.iitgoacepheustwth.cepheus23.APIs.GetEventsApi
import com.iitgoacepheustwth.cepheus23.EventsData.setData
import com.iitgoacepheustwth.cepheus23.adapter.EventAdapter
import com.iitgoacepheustwth.cepheus23.model.GetRegInfo
import com.iitgoacepheustwth.cepheus23.model.RegisteredEventList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Homescreen : AppCompatActivity() {
    private lateinit var binding: HomelayoutBinding

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        // Removes Dark mode
//        actionBar!!.setDisplayShowCustomEnabled(true)
//        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
//        val view = layoutInflater.inflate(R.layout.menu_image_center,null)
//        actionBar!!.setCustomView(view)

//        actionBar?.setDisplayShowCustomEnabled(true)
////        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
//        val view = layoutInflater.inflate(R.layout.menu_image_center,null)
//        actionBar?.setCustomView(view)
        // Removes Dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = HomelayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Token.token = getDefaults("JWToken").toString()
        val drawerLayout:DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayShowTitleEnabled(false)

//        val crashButton = findViewById<Button>(R.id.crash_button)
//        crashButton.setOnClickListener {
//            throw java.lang.RuntimeException("Test Crash")
//        }
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        ))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavView
        setupWithNavController(bottomNavigationView,navController)
        Log.i("message", "nav bars working")

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.sidenav_sponsors ->{

//                    val sponsorintent = Intent(this@Homescreen,SponsorActivity::class.java)
//                    startActivity(sponsorintent)
//                    val sponsorintent = Intent(this,SponsorFragment::class.java)
//                    startActivity(sponsorintent)
//
//                    val spFragment = SponsorFragment()
//                    val fragment : Fragment? =
//                        supportFragmentManager.findFragmentByTag(SponsorFragment::class.java.simpleName)
//                    Log.i("sponsors", "fragment found")
//                    if(fragment !is SponsorFragment){
//                        supportFragmentManager.beginTransaction()
//                            .add(R.id.mainContainer, spFragment, SponsorFragment::class.java.simpleName)
//                            .commit()
//
//                        Log.i("sponsors", "fragment switched")
//                    }
                    Toast.makeText(applicationContext,"Sponsors",Toast.LENGTH_SHORT).show()


                }
                R.id.sidenav_con -> {
                    val contactintent = Intent(this@Homescreen,AboutUs::class.java)
                    startActivity(contactintent)
                }

                R.id.sidenav_dev -> {
                    val contactintent = Intent(this@Homescreen,DeveloperActivity::class.java)
                    startActivity(contactintent)
                }

                
                R.id.sidenav_faq -> {
                    val intent = Intent(android.content.Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://discord.gg/jQsfbrtkzG")
                    startActivity(intent)
                }

                R.id.sidenav_signout-> {
                    val gso = GoogleSignInOptions.Builder(
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                    ).requestEmail()
                        .build()
                    val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
                    mGoogleSignInClient.signOut()

                    saveLoginStatuslocally("","")
                    val activityIntent = Intent(this@Homescreen, SigninActivity::class.java)
                    startActivity(activityIntent)
                    Toast.makeText(applicationContext, "Sign out", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }


//        implementing QR
        val header: View = navView.getHeaderView(0)
        val QRimageview : ImageView = header.findViewById(R.id.Qrbox)

        val drawericon : ImageView = header.findViewById(R.id.navicon)
        val navname : TextView = header.findViewById(R.id.nav_name)
        navname.text = getDefaults("Name").toString()
        UserName.name = getDefaults("Name").toString()
        drawericon.setImageResource(R.drawable.avtr1)

        val useremail : String = getDefaults("Email").toString()
//        var evlist = ""



        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://backendcepheus.cf/apiM1/")
            .build()

        val tkn = Token.token

        Log.i("QR",tkn)


        val geteventapi = retrofitBuilder.create(GetEventsApi::class.java)
        val tokenpara = GetRegInfo(tkn)
        val call = geteventapi.getEvents(tokenpara)

        call.enqueue(object : Callback<RegisteredEventList?> {
            override fun onResponse(
                call: Call<RegisteredEventList?>,
                response: Response<RegisteredEventList?>
            ) {
                if(response.isSuccessful){
                    Log.i("QR","success")
                    val eventlist = response.body()!!.regevents

                    val eventnames =arrayOf<String> ("Lorem Ipsum","HackOverFlow","Circuital Dilemma","Data Science Hackathon","HackTheGames","CTF","FizzBuzz","Online Treasure Hunt","Bridge Building Competition","Copy the Nature","Rule The Market","Launch Galaset","KBC Quiz Competition","Maze Amaze","Scratch","Treasure Hunt","Buy My Code","Pare It Down","Game Theory","Arduino Workshop","EV Bike Competition","Line Maze Fun Game","Dive the Boat")

//                    evlist = eventlist.toString()
                    var qrstring = ""
                    qrstring += UserName.name + "\n" + useremail + "\n\n"

                    var finaleventlist :MutableList<String> = mutableListOf<String>()
                    var j = 1
                    for (i in eventlist){
                        finaleventlist.add((j++).toString()+ ". " + eventnames[i-1])
                    }
                    Log.i("QR",eventlist.toString())

                    if (finaleventlist.isEmpty()){
                        finaleventlist = mutableListOf<String>()
                    }
                    else{
                        for (event in finaleventlist){
                            qrstring += event.toString() + "\n"
                        }
                    }

                    val writer = QRCodeWriter()
                    try {

                        val bitMatrix = writer.encode(qrstring, BarcodeFormat.QR_CODE, 250,250)
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
                else{
                    val resCode = response.code().toString() // IMP for check 401
                    if(!checkFor401(resCode)){
                        // Whatever action you've to perform

                    }
                    Log.i("QR",response.message().toString())
                    Log.i("QR",response.code().toString())
                }

            }

            override fun onFailure(call: Call<RegisteredEventList?>, t: Throwable) {
                Log.i("QR",t.message.toString())
                Log.i("QR",t.cause.toString())

            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feedback_menu,menu)
        return true
    }

    // override fun onOptionsItemSelected(item: MenuItem): Boolean {

    //     val intent = Intent(Intent.ACTION_VIEW)
    //     intent.data = Uri.parse("https://iitgoa.ac.in/")
    //     startActivity(intent)
    // }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return when(item.itemId){
            R.id.feedbackbtn -> {
                val intent = Intent(android.content.Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://forms.gle/CdszgvwSvs8YBVPz7")
                Log.i("error","3")
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

    private fun checkFor401(resCode: String): Boolean {
        // TO BE TESTED FOR 401----------------------------------------------------------------------
        if(resCode == "401") {
            val gso = GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
            ).requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            mGoogleSignInClient.signOut()

            saveLoginStatuslocally("","")
            val activityIntent = Intent(this@Homescreen, SigninActivity::class.java)
            startActivity(activityIntent)
            Toast.makeText(applicationContext, "Session Expired.", Toast.LENGTH_LONG).show()

            return true
        }
        return false
    }

    private fun saveLoginStatuslocally(currstatus_login: String, currstatus_token: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Login_status", currstatus_login)
        editor.putString("JWToken", currstatus_token)
        editor.apply()
    }

    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }

}






