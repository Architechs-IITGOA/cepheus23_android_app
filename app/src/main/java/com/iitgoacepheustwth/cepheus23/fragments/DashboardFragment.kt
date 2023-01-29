package com.iitgoacepheustwth.cepheus23.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.iitgoacepheustwth.cepheus23.APIs.GetEventsApi
import com.iitgoacepheustwth.cepheus23.EventsData.setData
import com.iitgoacepheustwth.cepheus23.SigninActivity
import com.iitgoacepheustwth.cepheus23.adapter.EventAdapter
import com.iitgoacepheustwth.cepheus23.databinding.FragmentDashboardBinding
import com.iitgoacepheustwth.cepheus23.model.GetRegInfo
import com.iitgoacepheustwth.cepheus23.model.RegisteredEventList
import com.iitgoacepheustwth.cepheus23.model.Token
import com.iitgoacepheustwth.cepheus23.model.UserName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory





class DashboardFragment : Fragment() {
    private lateinit var  dashboardBinding: FragmentDashboardBinding
    private var progressBar: ProgressBar? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardBinding = com.iitgoacepheustwth.cepheus23.databinding.FragmentDashboardBinding.inflate(inflater, container, false)
        progressBar = dashboardBinding.pgbar



        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://backendcepheus.cf/apiM1/")
            .build()

        val tkn = Token.token

        println(tkn)

//        val Tkn = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjEwMTIsImluaSI6IlBQIiwiZ3JhZGUiOjEwLCJyZWdpc3RlcmVkIjp0cnVlLCJpYXQiOjE2NzM3MDc3MTksImV4cCI6MTY3Mzk2NjkxOX0.yx_Kw2kdyoOn0k8JKNytTXA8V8PtWLu5kMBpoWZc_Sk"

        val geteventapi = retrofitBuilder.create(GetEventsApi::class.java)
        val tokenpara = GetRegInfo(tkn)
        val call = geteventapi.getEvents(tokenpara)

        call.enqueue(object : Callback<RegisteredEventList?> {
            override fun onResponse(
                call: Call<RegisteredEventList?>,
                response: Response<RegisteredEventList?>
            ) {
                progressBar!!.visibility = View.GONE
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

                    dashboardBinding.dashUsername.text = UserName.name


                    var oncnt = 0
                    var offcnt = 0
                    var wcnt = 0
                    for(i in eventlist){
                        if(i==19 || i==20){
                            wcnt += 1
                        }
                        else if(i<=11){
                            oncnt += 1
                        }
                        else{
                            offcnt += 1
                        }
                    }

                    dashboardBinding.onlinecnt.text = oncnt.toString()
                    dashboardBinding.offlinecnt.text = offcnt.toString()
                    dashboardBinding.workcnt.text = wcnt.toString()

                }
                else{
                    val resCode = response.code().toString() // IMP for check 401
                    if(!checkFor401(resCode)){
                        // Whatever action you've to perform

                    }
                    Log.i("response3",response.message().toString())
                    Log.i("response3",response.code().toString())
                }

            }

            override fun onFailure(call: Call<RegisteredEventList?>, t: Throwable) {
                progressBar!!.visibility = View.GONE
                Log.i("failed3",t.message.toString())
                Log.i("failed3",t.cause.toString())

//                Toast.makeText(this@DashboardFragment,"Failure occur",Toast.LENGTH_SHORT).show()

            }
        })

        return dashboardBinding.root



    }

    private fun checkFor401(resCode: String): Boolean {
        // TO BE TESTED FOR 401----------------------------------------------------------------------
        if(resCode == "401") {
//            val gso = GoogleSignInOptions.Builder(
//                GoogleSignInOptions.DEFAULT_SIGN_IN
//            ).requestEmail()
//                .build()
//            val mGoogleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)
//            mGoogleSignInClient.signOut()

            saveLoginStatuslocally("","")
            val activityIntent = Intent(context, SigninActivity::class.java)
            startActivity(activityIntent)
            Toast.makeText(context, "Session Expired.", Toast.LENGTH_LONG).show()

            return true
        }
        return false
    }
    private fun saveLoginStatuslocally(currstatus_login: String, currstatus_token: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString("Login_status", currstatus_login)
        editor.putString("JWToken", currstatus_token)
        editor.apply()
    }

//    fun getDefaults(key: String?): String? {
////        val preferences = PreferenceManager.getDefaultSharedPreferences(this@DashboardFragment)
//        val preferences = this.getActivity()?.getSharedPreferences("pref", Context.MODE_PRIVATE);
//        return preferences?.getString(key, null)
//    }


}