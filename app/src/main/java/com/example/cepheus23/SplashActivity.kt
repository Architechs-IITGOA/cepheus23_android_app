package com.example.cepheus23

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cepheus23.databinding.ActivitySplashBinding
import com.example.cepheus23.model.Login
import com.example.cepheus23.model.Registration

class SplashActivity : AppCompatActivity() {
    private val splashTime = 3000L
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val loadingGif = binding.splashImg
        Glide.with(this).asGif().load(R.drawable.logo).into(loadingGif)

        val  Login_status=getDefaults("Login_status")
        val Register_status=getDefaults("register_status")



        val login = Login.login
        val register = Registration.registration

        Handler().postDelayed({
//            Log.i("splash",Login.login.toString())
//            val intent = Intent(this@SplashActivity, SigninActivity::class.java)
//            startActivity(intent)
//                                        if(!login){
//                                            Log.i("splash",Login.login.toString())
//                                            val intent = Intent(this@SplashActivity, SigninActivity::class.java)
//                                            startActivity(intent)
//                                        }
//                                        else if (login && !register){
//                                            Log.i("splash",Login.login.toString())
//                                            val intent = Intent(this@SplashActivity, DetailsActivity::class.java)
//                                            startActivity(intent)
//                                        }
//                                        else{
//                                            Log.i("splash",Login.login.toString())
//                                            val intent = Intent(this@SplashActivity, Homescreen::class.java)
//                                            startActivity(intent)
//                                        }
            if(Login_status!="true"){
                Log.i("splash",Login.login.toString())
                val intent = Intent(this@SplashActivity, SigninActivity::class.java)
                startActivity(intent)

            }
            else if(Login_status=="true" && Register_status!="true"){

                Log.i("splash",Login.login.toString())
                val intent = Intent(this@SplashActivity, DetailsActivity::class.java)
                startActivity(intent)
            }
            else{
                Log.i("splash",Login.login.toString())
                val intent = Intent(this@SplashActivity, Homescreen::class.java)
                startActivity(intent)

            }
            finish()
        }, splashTime)

//
//
//        if(login == "True"){
//
//            Handler().postDelayed({
//                val intent = Intent(this@SplashActivity, SigninActivity::class.java)
//                startActivity(intent)
//                finish()
//            }, splashTime)
//        }
//        else if (login == "True" && register == "False" ){
//
//            Handler().postDelayed({
//                val intent = Intent(this@SplashActivity, DetailsActivity::class.java)
//                startActivity(intent)
//                finish()
//            }, splashTime)
//
//        }
//        else{
//
//            Handler().postDelayed({
//                val intent = Intent(this@SplashActivity, Homescreen::class.java)
//                startActivity(intent)
//                finish()
//            }, splashTime)
//
//        }
    }
    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }
}


//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Handler
//import com.example.touristpolicingapp.R
//import com.example.touristpolicingapp.databinding.ActivitySplashBinding
//
//class SplashActivity : AppCompatActivity() {
//    private val splashTime = 1000L
//    lateinit var binding: ActivitySplashBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySplashBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        supportActionBar?.hide()
//        Handler().postDelayed({
//            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, splashTime)
//    }
//}