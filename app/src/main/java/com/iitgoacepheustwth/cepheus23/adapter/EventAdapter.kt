package com.iitgoacepheustwth.cepheus23.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iitgoacepheustwth.cepheus23.APIs.GetEventsApi
import com.iitgoacepheustwth.cepheus23.APIs.TeamMatesApi
import com.iitgoacepheustwth.cepheus23.DescriptionActivity
import com.iitgoacepheustwth.cepheus23.EventsData.EventData
import com.iitgoacepheustwth.cepheus23.EventsData.setData
import com.iitgoacepheustwth.cepheus23.R
import com.iitgoacepheustwth.cepheus23.SigninActivity
import com.iitgoacepheustwth.cepheus23.databinding.EventsElementBinding
import com.iitgoacepheustwth.cepheus23.fragments.DashboardFragment
import com.iitgoacepheustwth.cepheus23.model.RegisteredEventList
import com.iitgoacepheustwth.cepheus23.model.TeamMates
import com.iitgoacepheustwth.cepheus23.model.Token
import com.iitgoacepheustwth.cepheus23.model.TokenNEventID
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import com.example.cepheus23.databinding.FragmentEvent1Binding

class EventAdapter(val context:Context,var event: List<EventData>, var eventPage: Int) : RecyclerView.Adapter<EventAdapter.eventViewHolder>(){

    inner class eventViewHolder(val binding: EventsElementBinding) : RecyclerView.ViewHolder(binding.root) {

        var eventName = binding.eventName
        var eventOverview = binding.eventDesc
        var eventListImage = binding.ivEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): eventViewHolder {
        var binding = EventsElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return eventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: eventViewHolder, position: Int) {
        var eventImage: Int? = null
        if(eventPage== 1){
            holder.itemView.setOnClickListener{

                val intent= Intent(holder.itemView.context,DescriptionActivity::class.java)
                intent.putExtra("Event",event[position])
//            intent.putExtra("EventImage",eventImage)
                holder.itemView.context.startActivity(intent)
                Log.i("adapter",event[position].overview.toString())
                Log.i("adapter",event[position].eventName.toString())
            }
        }else{
            holder.itemView.setOnClickListener{
//                Log.d("eve", event[position].team.toString())
                if(event[position].team == 1){
                    // Only do something if team event
                    val retrofitBuilder = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://backendcepheus.cf/apiM1/")
                        .build()

                    val tkn = Token.token
                    val eventId = event[position].id!!

                    val teamMatesApi = retrofitBuilder.create(TeamMatesApi::class.java)
                    val eventTokenParam = TokenNEventID(tkn, eventId)

                    val call = teamMatesApi.getteam(eventTokenParam)

                    call.enqueue(object :Callback<TeamMates?>{
                        override fun onResponse(
                            call: Call<TeamMates?>,
                            response: Response<TeamMates?>
                        ) {
                            if (response.isSuccessful) {
                                val teamcode = response.body()?.team_code
                                val teamname = response.body()?.team_name
                                val mates = response.body()?.data

                                // Shows a dialog box showing successful registration
                                val builder = AlertDialog.Builder(context)
                                var str = "Team name: "
                                str = str + teamname + "\n\n"
                                str = str + "Team code: " + teamcode + "\n\n"
                                for (i in 1..mates?.size!!) {
                                    str = str + "\n\t" + i + ". " + mates?.get(i - 1)?.user_name + "\n"
                                    str = str +"\t\t\t" + mates?.get(i - 1)?.email + "\n"
                                }
                                builder.setMessage(str)
                                    .setPositiveButton("Share",
                                        DialogInterface.OnClickListener { dialog, id ->
//                                                        Toast.makeText(this@DescriptionActivity, "Sending whatsapp", Toast.LENGTH_SHORT).show()
                                            // Sharing the team Code on Whatsapp / any other social media
                                            val shareIntent = Intent()
                                            shareIntent.action = Intent.ACTION_SEND
                                            shareIntent.type="text/plain"
                                            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey I have registered for "+event[position].eventName + "\n\nOur team code is: " + teamcode + "\n\nJoin from website https://iitgoa.ac.in/cepheus/ or from Cepheus'23 App (Available now on Google PlayStore).");
                                            context.startActivity(shareIntent)
                                        })
                                    .setNegativeButton("Cancel",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            // User cancelled the dialog
                                        })
                                builder.setTitle(event[position].eventName+" team")
                                val alert = builder.create()
                                alert.setOnShowListener({
                                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                                        context.resources.getColor(
                                            androidx.appcompat.R.color.primary_dark_material_dark
                                        )
                                    )
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                                        context.resources.getColor(
                                            androidx.appcompat.R.color.primary_dark_material_dark
                                        )
                                    );
                                })
                                alert.show()

                                mates?.size.toString()?.let { it1 -> Log.d("he", it1) }
                                mates?.get(0)?.user_name.toString()?.let { it1 -> Log.d("he", it1) }
                                Log.d("he", response.body()?.data.toString())
                                Log.d("he", response.body()?.team_name.toString())
                                Log.d("he", response.body()?.team_code.toString())

                            } else {
                                val resCode = response.code().toString()
                                if (!checkFor401(resCode)) {
                                    // Whatever action you've to perform
                                    Log.d("he", response.message().toString())
                                }
                            }
                        }


                        override fun onFailure(call: Call<TeamMates?>, t: Throwable) {
                            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_LONG).show()
                            Log.d("he", "internet down")
                        }
                    })
                }

//                val intent= Intent(holder.itemView.context,DescriptionActivity::class.java)
//                intent.putExtra("Event",event[position])
////            intent.putExtra("EventImage",eventImage)
//                holder.itemView.context.startActivity(intent)
//                Log.i("adapter",event[position].overview.toString())
//                Log.i("adapter",event[position].eventName.toString())
            }
        }

        holder.eventName.text = event[position].eventName
        holder.eventOverview.text = event[position].description





        when (event[position].eventName!!) {
            "Lorem Ipsum" -> {
                eventImage = R.drawable.loremipsum1
            }
            "HackOverFlow" -> {
                eventImage = R.drawable.hackoverflow1
            }
            "Circuital Dilemma" -> {
                eventImage = R.drawable.circuitaldilema1
            }
            "Data Science Hackathon" -> {
                eventImage = R.drawable.datascihack1
            }
            "HackTheGames" -> {
                eventImage = R.drawable.hackthegame1
            }
            "CTF" -> {
                eventImage = R.drawable.ctf1
            }
            "FizzBuzz" -> {
                eventImage = R.drawable.fizzbuzz1
            }
            "Online Treasure Hunt" -> {
                eventImage = R.drawable.onlinethunt1
            }
            "Bridge Building Competition" -> {
                eventImage = R.drawable.bridgebuilding1
            }
            "Copy the Nature" -> {
                eventImage = R.drawable.copythenature1
            }
            "Rule The Market" -> {
                eventImage = R.drawable.rulethemarket1
            }
            "Launch Galaset" -> {
                eventImage = R.drawable.launchgala1
            }
            "KBC Quiz Competition" -> {
                eventImage = R.drawable.kbc1
            }
            "Maze Amaze" -> {
                eventImage = R.drawable.mazeamaze1
            }
            "Scratch" -> {
                eventImage = R.drawable.scratch1
            }
            "Treasure Hunt" -> {
                eventImage = R.drawable.treashunt1
            }
            "Buy My Code" -> {
                eventImage = R.drawable.buymycode1
            }
            "Pare It Down" -> {
                eventImage = R.drawable.pareitdown1
            }
            "Game Theory" -> {
                eventImage = R.drawable.gametheory1
            }
            "Arduino's Trial" -> {
                eventImage = R.drawable.arduinotrial1
            }
            "EV Bike Competition" -> {
                eventImage = R.drawable.ebike1
            }

            else -> {
                eventImage = R.drawable.h
            }

        }

        holder.eventListImage.setImageResource(eventImage!!)
    }

    override fun getItemCount(): Int {
        return event.size
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
//            holder.itemView.context
            val intent = Intent(context, SigninActivity::class.java)
            context.startActivity(intent)
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
}




