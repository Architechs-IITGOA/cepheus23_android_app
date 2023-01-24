package com.iitgoacepheustwth.cepheus23

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import com.bumptech.glide.Glide
import com.iitgoacepheustwth.cepheus23.model.Login
import com.iitgoacepheustwth.cepheus23.model.Registration
import com.iitgoacepheustwth.cepheus23.R
import com.iitgoacepheustwth.cepheus23.databinding.ActivitySplashBinding

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

    }
    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }
}

