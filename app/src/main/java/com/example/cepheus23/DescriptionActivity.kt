package com.example.cepheus23

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cepheus23.APIs.CreateTeamApi
import com.example.cepheus23.APIs.RegisterEventApi
import com.example.cepheus23.EventsData.EventData
import com.example.cepheus23.databinding.FragmentEventDetailsBinding
import com.example.cepheus23.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding : FragmentEventDetailsBinding
    private lateinit var obj : EventData
    private var eventImg:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        window.decorView.apply {
//            systemUiVisibility =
//                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
//        }

        obj = intent.getParcelableExtra("Event")!!
        eventImg=intent.getIntExtra("EventImage",-1)
        setData(obj, eventImg!!)

//        binding.buttonRegistration.setOnClickListener {
//            val intent = Intent(this, Homescreen::class.java)
//            startActivity(intent)
//        }

//        val eventid = obj.id!!.toInt()
//        var createTeamFlag = false
        binding.registerButton.setOnClickListener {

            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://backendcepheus.cf/apiM1/")
                .build()

            val ctapi = retrofitBuilder.create(CreateTeamApi::class.java)

            val teaminfo = CreateTeamInfo(Token.token,"bhomsdke")
            val call1 = ctapi.teamCreate(teaminfo)

//            var teamcode = ""

            call1.enqueue(object : Callback<CreateTeamResponse?> {
                override fun onResponse(
                    call: Call<CreateTeamResponse?>,
                    response: Response<CreateTeamResponse?>
                ) {
                    Toast.makeText(this@DescriptionActivity,"OnResponse",Toast.LENGTH_SHORT).show()
                    if(response.isSuccessful){
                        Log.i("response2","Team Created Successfully")
                        Toast.makeText(this@DescriptionActivity,"Success",Toast.LENGTH_SHORT).show()
//                        teamcode = response.body()?.team_code.toString()
//                        Log.i("respnse2",teamcode)
                    }
                    else{
                        Log.i("response2",response.code().toString())
                        Log.i("response2",response.message())
                    }
                }

                override fun onFailure(call: Call<CreateTeamResponse?>, t: Throwable) {
                    Log.i("failed2",t.message.toString())
                    Log.i("failed2",t.cause.toString())
                    Toast.makeText(this@DescriptionActivity,"Onfailure",Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    private fun setData(obj:EventData,eventImg:Int)
    {
        binding.eventName.text = obj.eventName
        binding.eventdec.text = obj.overview
//        binding.ivEventImage.setImageResource(eventImg)
    }
}