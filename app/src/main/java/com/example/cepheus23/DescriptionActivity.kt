package com.example.cepheus23

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cepheus23.APIs.CreateTeamApi
import com.example.cepheus23.APIs.RegisterEventApi
import com.example.cepheus23.EventsData.EventData
import com.example.cepheus23.databinding.FragmentEventDetailsBinding
import com.example.cepheus23.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        obj = intent.getParcelableExtra("Event")!!
        eventImg=intent.getIntExtra("EventImage",-1)


        Log.i("desc",obj.eventName.toString())
        Log.i("desc",obj.overview.toString())
        setData(obj, eventImg!!)

//        binding.buttonRegistration.setOnClickListener {
//            val intent = Intent(this, Homescreen::class.java)
//            startActivity(intent)
//        }

//        val eventid = obj.id!!.toInt()
//        var createTeamFlag = false

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://backendcepheus.cf/apiM1/")
            .build()


        if(obj.team==0){

            binding.registerButton.setOnClickListener {
                var teamcode = ""
                var team_name = "Fuckyou"
                // call create team api

                val CTapi = retrofitBuilder.create(CreateTeamApi::class.java)
                val teaminfo = CreateTeamInfo(obj.id!!,Token.token,team_name)

                val call1 = CTapi.teamCreate(teaminfo)

                call1.enqueue(object : Callback<CreateTeamResponse?> {
                    override fun onResponse(
                        call: Call<CreateTeamResponse?>,
                        response: Response<CreateTeamResponse?>
                    ) {
                        if(response.isSuccessful){
                            Log.i("response2", "team created")
                            teamcode = response.body()?.team_code.toString()
                        }
                        else{
                            Log.i("response2",response.message())
                            Log.i("response2",response.code().toString())
                        }


                    }

                    override fun onFailure(call: Call<CreateTeamResponse?>, t: Throwable) {
                        Log.i("failed2","Onfailure of ct")
                    }
                })



                // call register api
                val regeventapi = retrofitBuilder.create(RegisterEventApi::class.java)
                val data = RegisterEventInfo(Token.token,teamcode, obj.id!!)
                val call2 = regeventapi.eventregistration(data)

                call2.enqueue(object : Callback<RegisterEventResponse?> {
                    override fun onResponse(
                        call: Call<RegisterEventResponse?>,
                        response: Response<RegisterEventResponse?>
                    ) {
                        if(response.isSuccessful){
                            Log.i("response2","registered successfully")
                            binding.registerButton.setText("registered")
                        }
                        else{
                            Log.i("response2",response.message())
                        }


                    }

                    override fun onFailure(call: Call<RegisterEventResponse?>, t: Throwable) {
                        Log.i("failed2","failure")
                    }
                })

            }
        }

        else{
            binding.registerButton.setText("Create Team")
            binding.registerButton.setOnClickListener {


                // create team intent
                Log.i("response3",Token.token)


                val dialog = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.bottom_dialog, null)

                val createTeamCall = view.findViewById<Button>(R.id.create_team_button)
                val joinTeamCall = view.findViewById<Button>(R.id.join_team_button)

                createTeamCall.setOnClickListener {
                    var teamcode = ""
                    var team_name = "Fuckyou"

                    if (view.findViewById<TextView>(R.id.team_name).text.isEmpty()){
                        Toast.makeText(this@DescriptionActivity, "Please give a Team name.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    team_name = view.findViewById<TextView>(R.id.team_name).text.toString()

                    // Create team API called
                    val CTapi = retrofitBuilder.create(CreateTeamApi::class.java)
                    val teaminfo = CreateTeamInfo(obj.id!!,Token.token,team_name)

                    val call1 = CTapi.teamCreate(teaminfo)

                    call1.enqueue(object : Callback<CreateTeamResponse?> {
                        override fun onResponse(
                            call: Call<CreateTeamResponse?>,
                            response: Response<CreateTeamResponse?>
                        ) {
                            if(response.isSuccessful){
                                Log.i("response2", "team created")
                                teamcode = response.body()?.team_code.toString()
                                Log.i("response2",teamcode)
//                                Toast.makeText(this@DescriptionActivity,"Team created successfully: " + teamcode,Toast.LENGTH_LONG).show()


                                val regeventapi = retrofitBuilder.create(RegisterEventApi::class.java)
                                val data = RegisterEventInfo(Token.token,teamcode, obj.id!!)
                                Log.i("response2",data.toString())
                                val call2 = regeventapi.eventregistration(data)

                                call2.enqueue(object : Callback<RegisterEventResponse?> {
                                    override fun onResponse(
                                        call: Call<RegisterEventResponse?>,
                                        response1: Response<RegisterEventResponse?>
                                    ) {
                                        if(response1.isSuccessful){
                                            Log.i("response2","registered successfully")
                                            Toast.makeText(this@DescriptionActivity,"Team created successfully: " + teamcode,Toast.LENGTH_LONG).show()
//                                            binding.registerButton.setText("registered")
                                            dialog.dismiss()
                                        }
                                        else{
                                            Log.i("response2","error in registration")
                                            Log.i("response2",response.errorBody()?.charStream()?.readText().toString())
                                            Log.i("response2",response.message())
                                            Log.i("response2",response.code().toString())
                                            Toast.makeText(this@DescriptionActivity,"not registered team" + response.errorBody()?.charStream()?.readText().toString(),Toast.LENGTH_LONG).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<RegisterEventResponse?>, t: Throwable) {
                                        Log.i("failed2","failure of reg")
                                        Toast.makeText(this@DescriptionActivity,"Couldn't connect to backend while registering",Toast.LENGTH_LONG).show()

                                    }
                                })
                            }
                            else{
                                Log.i("response2","error in team creation")
                                Log.i("response2",response.body().toString())
                                Log.i("response2",response.errorBody()?.charStream()?.readText().toString())
                                Log.i("response2", response.message())
                                Log.i("response2", response.code().toString())
                                Toast.makeText(this@DescriptionActivity,"not made team" + response.errorBody()?.charStream()?.readText().toString(),Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<CreateTeamResponse?>, t: Throwable) {
                            Log.i("failed2","Onfailure of ct")
                            Toast.makeText(this@DescriptionActivity,"Couldn't connect to backend while create team",Toast.LENGTH_LONG).show()
                        }
                    })

//                    Toast.makeText(this@DescriptionActivity, "Create team text:" + view.findViewById<TextView>(R.id.team_name).text.toString(), Toast.LENGTH_SHORT).show()
//                    Toast.makeText(this@DescriptionActivity, "Join team text:" + view.findViewById<TextView>(R.id.team_id).text.toString(), Toast.LENGTH_SHORT).show()
                }
                joinTeamCall.setOnClickListener {
                    if (view.findViewById<TextView>(R.id.team_id).text.isEmpty()){
                        Toast.makeText(this@DescriptionActivity, "Please give a Team code.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    dialog.dismiss()
                    Toast.makeText(this@DescriptionActivity, "Please " + view.findViewById<TextView>(R.id.team_name).text.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@DescriptionActivity, "Join team text:" + view.findViewById<TextView>(R.id.team_id).text.toString(), Toast.LENGTH_SHORT).show()
                }

                dialog.setContentView(view)

                dialog.show()



                //create team api call
//                val CTapi = retrofitBuilder.create(CreateTeamApi::class.java)
//                val teaminfo = CreateTeamInfo(obj.id!!,Token.token,team_name)
//
//                val call1 = CTapi.teamCreate(teaminfo)
//
//                call1.enqueue(object : Callback<CreateTeamResponse?> {
//                    override fun onResponse(
//                        call: Call<CreateTeamResponse?>,
//                        response: Response<CreateTeamResponse?>
//                    ) {
//                        if(response.isSuccessful){
//                            Log.i("response2", "team created")
//                            val teamcode = response.body()?.team_code.toString()
//                            Log.i("response2",teamcode)
//                            Toast.makeText(this@DescriptionActivity,teamcode,Toast.LENGTH_SHORT).show()
//
//
//                            val regeventapi = retrofitBuilder.create(RegisterEventApi::class.java)
//                            val data = RegisterEventInfo(Token.token,teamcode, obj.id!!)
//                            Log.i("response2",data.toString())
//                            val call2 = regeventapi.eventregistration(data)
//
//                            call2.enqueue(object : Callback<RegisterEventResponse?> {
//                                override fun onResponse(
//                                    call: Call<RegisterEventResponse?>,
//                                    response: Response<RegisterEventResponse?>
//                                ) {
//                                    if(response.isSuccessful){
//                                        Log.i("response2","registered successfully")
//                                        binding.registerButton.setText("registered")
//                                    }
//                                    else{
//                                        Log.i("response2","error in registration")
//                                        Log.i("response2",response.errorBody()?.charStream()?.readText().toString())
//                                        Log.i("response2",response.message())
//                                        Log.i("response2",response.code().toString())
//                                    }
//
//
//                                }
//
//                                override fun onFailure(call: Call<RegisterEventResponse?>, t: Throwable) {
//                                    Log.i("failed2","failure of reg")
//                                }
//                            })
//                        }
//                        else{
//                            Log.i("response2","error in team creation")
//                            Log.i("response2",response.body().toString())
//                            Log.i("response2",response.errorBody()?.charStream()?.readText().toString())
//                            Log.i("response2", response.message())
//                            Log.i("response2", response.code().toString())
//
//                        }
//
//
//                    }
//
//                    override fun onFailure(call: Call<CreateTeamResponse?>, t: Throwable) {
//                        Log.i("failed2","Onfailure of ct")
//                    }
//                })



                // register event api call
//                val regeventapi = retrofitBuilder.create(RegisterEventApi::class.java)
//                val data = RegisterEventInfo(Token.token,teamcode, obj.id!!)
//                Log.i("response2",data.toString())
//                val call2 = regeventapi.eventregistration(data)
//
//                call2.enqueue(object : Callback<RegisterEventResponse?> {
//                    override fun onResponse(
//                        call: Call<RegisterEventResponse?>,
//                        response: Response<RegisterEventResponse?>
//                    ) {
//                        if(response.isSuccessful){
//                            Log.i("response2","registered successfully")
//                            binding.registerButton.setText("registered")
//                        }
//                        else{
//                            Log.i("response2","error in registration")
//                            Log.i("response2",response.errorBody()?.charStream()?.readText().toString())
//                            Log.i("response2",response.message())
//                            Log.i("response2",response.code().toString())
//                        }
//
//
//                    }
//
//                    override fun onFailure(call: Call<RegisterEventResponse?>, t: Throwable) {
//                        Log.i("failed2","failure of reg")
//                    }
//                })


            }
        }


    }





    private fun setData(obj:EventData,eventImg:Int)
    {
        binding.eventName.text = obj.eventName
        binding.eventdec.text = obj.overview
        binding.hostname.text = obj.host
        binding.contactno.text = obj.phone

    }
}