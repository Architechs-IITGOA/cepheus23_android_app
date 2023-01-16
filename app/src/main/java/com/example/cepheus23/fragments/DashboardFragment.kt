package com.example.cepheus23.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cepheus23.APIs.GetEventsApi
import com.example.cepheus23.EventsData.EventData
import com.example.cepheus23.EventsData.setData
import com.example.cepheus23.R
import com.example.cepheus23.adapter.EventAdapter
import com.example.cepheus23.databinding.FragmentDashboardBinding
import com.example.cepheus23.model.GetRegInfo
import com.example.cepheus23.model.RegisteredEventList
import com.example.cepheus23.model.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory





class DashboardFragment : Fragment() {
    private lateinit var  dashboardBinding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = com.example.cepheus23.databinding.FragmentDashboardBinding.inflate(inflater, container, false)




        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://backendcepheus.cf/apiM1/")
            .build()

        val tkn = Token.token

        println(tkn)

        val Tkn = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjEwMTIsImluaSI6IlBQIiwiZ3JhZGUiOjEwLCJyZWdpc3RlcmVkIjp0cnVlLCJpYXQiOjE2NzM3MDc3MTksImV4cCI6MTY3Mzk2NjkxOX0.yx_Kw2kdyoOn0k8JKNytTXA8V8PtWLu5kMBpoWZc_Sk"

        val geteventapi = retrofitBuilder.create(GetEventsApi::class.java)
        val tokenpara = GetRegInfo(tkn)
        val call = geteventapi.getEvents(tokenpara)

        call.enqueue(object : Callback<RegisteredEventList?> {
            override fun onResponse(
                call: Call<RegisteredEventList?>,
                response: Response<RegisteredEventList?>
            ) {
                if(response.isSuccessful){
                    Log.i("response3","success")
                    val eventlist = response.body()!!.regevents
                    Log.i("response3",eventlist.toString())
                    var eventRecycler = dashboardBinding.dashboardRecyclerView
                    var eventsRecyclerLayoutManager = LinearLayoutManager(activity)
                    eventRecycler.layoutManager = eventsRecyclerLayoutManager
                    val regEvents = setData.SetEvents().filter {eventlist.contains(it.id)}
                    var eventsRecyclerAdapter = EventAdapter(regEvents)
                    eventRecycler.adapter = eventsRecyclerAdapter

                }
                else{
                    Log.i("response3",response.message().toString())
                    Log.i("response3",response.code().toString())
                }

            }

            override fun onFailure(call: Call<RegisteredEventList?>, t: Throwable) {
                Log.i("failed3",t.message.toString())
                Log.i("failed3",t.cause.toString())

//                Toast.makeText(this@DashboardFragment,"Failure occur",Toast.LENGTH_SHORT).show()

            }
        })

        return dashboardBinding.root








    }


}