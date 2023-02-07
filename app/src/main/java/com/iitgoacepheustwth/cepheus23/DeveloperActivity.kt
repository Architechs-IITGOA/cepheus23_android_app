package com.iitgoacepheustwth.cepheus23

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import com.iitgoacepheustwth.cepheus23.databinding.ActivitySponsorBinding

class DeveloperActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val vivek = findViewById<LinearLayout>(R.id.ll_vivek)
        vivek.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/in/vivek-bandrele-97790b240/")
            startActivity(intent)
        }

        val harsh = findViewById<LinearLayout>(R.id.ll_harsh)
        harsh.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/in/harshvardhangupta07/")
            startActivity(intent)
        }
        val pranav = findViewById<LinearLayout>(R.id.ll_pranav)
        pranav.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/in/pranav-todkar/")
            startActivity(intent)
        }
        val rushi = findViewById<LinearLayout>(R.id.ll_rushi)
        rushi.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/in/rushikesh-pawar-72246018a/")
            startActivity(intent)
        }
    }
}