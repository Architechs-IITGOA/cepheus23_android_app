package com.iitgoacepheustwth.cepheus23

import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.iitgoacepheustwth.cepheus23.APIs.CreateTeamApi
import com.iitgoacepheustwth.cepheus23.APIs.RegisterEventApi
import com.iitgoacepheustwth.cepheus23.EventsData.EventData
import com.iitgoacepheustwth.cepheus23.databinding.FragmentEventDetailsBinding
import com.iitgoacepheustwth.cepheus23.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.NonCancellable.start
import kotlin.text.Typography.registered


class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding : FragmentEventDetailsBinding
    private lateinit var obj : EventData
    private var eventImg:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        // Removes Dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = FragmentEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
//        No need now
//        binding.contactno.setOnClickListener{
//            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val clipData = ClipData.newPlainText("text", obj.phone)
//            clipboardManager.setPrimaryClip(clipData)
//
//            Toast.makeText(this@DescriptionActivity, "Phone number copied.",Toast.LENGTH_SHORT).show()
//        }

        obj = intent.getParcelableExtra("Event")!!
        eventImg=intent.getIntExtra("EventImage",-1)


        Log.i("desc",obj.eventName.toString())
        Log.i("desc",obj.overview.toString())
        setData(obj, eventImg!!)


        binding.eventRules.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = Uri.parse(obj.rules_link)
            startActivity(intent)
        }

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
            binding.enterButton.visibility = View.GONE

            binding.registerButton.setOnClickListener {
                binding.registerButton.isEnabled = false
                binding.registerButton.text = "Registering..."
                var teamcode = ""
                val team_name : String = getDefaults("Email").toString()
                // call create team api
                Log.i("new response",team_name)

                val CTapi = retrofitBuilder.create(CreateTeamApi::class.java)
                val teaminfo = CreateTeamInfo(obj.id!!,Token.token,team_name)

                val call1 = CTapi.teamCreate(teaminfo)

                call1.enqueue(object : Callback<CreateTeamResponse?> {
                    override fun onResponse(
                        call: Call<CreateTeamResponse?>,
                        response: Response<CreateTeamResponse?>
                    ) {
                        if(response.isSuccessful){

                            Log.i("new response", "team created")
                            teamcode = response.body()?.team_code.toString()


                            val regeventapi = retrofitBuilder.create(RegisterEventApi::class.java)
                            val data = RegisterEventInfo(Token.token,teamcode, obj.id!!)
                            val call2 = regeventapi.eventregistration(data)

                            call2.enqueue(object : Callback<RegisterEventResponse?> {
                                override fun onResponse(
                                    call: Call<RegisterEventResponse?>,
                                    response1: Response<RegisterEventResponse?>
                                ) {
                                    binding.registerButton.isEnabled = true
                                    binding.registerButton.text = "Register now"

                                    Log.i("new response", "in response")
                                    if(response1.isSuccessful){
                                        Log.i("new response", "is success")
                                        // Shows a dialog box showing successful registration
                                        val builder = AlertDialog.Builder(this@DescriptionActivity)
                                        builder.setMessage("Please check your email inbox for further details about the event, noting that the email may be in your spam folder.")
                                            .setPositiveButton("OK",
                                                DialogInterface.OnClickListener { dialog, id ->
                                                    // On OK Clicked
                                                })
                                        builder.setTitle("Successfully Registered")
                                        val alert = builder.create()
                                        alert.setOnShowListener {
                                            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(androidx.appcompat.R.color.primary_dark_material_dark))
                                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(androidx.appcompat.R.color.primary_dark_material_dark))
                                        }
                                        alert.show()


//                                        Toast.makeText(this@DescriptionActivity,"Registered Successfully",Toast.LENGTH_SHORT).show()
                                    }
//                                    else if(response1.code().toString() == "401"){
//
////                                        saveLoginStatuslocally("","")
//                                        Log.i("new response", response1.code().toString())
//                                        val signActivityIntent = Intent(this@DescriptionActivity,SigninActivity::class.java)
//                                        startActivity(signActivityIntent)
//                                    }
                                    else{
                                        Log.i("new response", response1.message())
                                        Log.i("new response", response1.code().toString())
                                        Log.i("new response", response1.errorBody()?.charStream()?.readText().toString())
                                        // Only one option left That the grade doesn't fit
                                        Toast.makeText(this@DescriptionActivity,"Your age doesn't fit for this event.",Toast.LENGTH_LONG).show()
                                    }

                                }

                                override fun onFailure(
                                    call: Call<RegisterEventResponse?>,
                                    t: Throwable
                                ) {
                                    binding.registerButton.isEnabled = true
                                    binding.registerButton.text = "Register now"
                                    Log.i("new response", "onfailure")
                                    Toast.makeText(this@DescriptionActivity, "Please check internet connection or contact developers!",Toast.LENGTH_LONG).show()
                                }
                            })

                        }
                        else{
                            binding.registerButton.isEnabled = true
                            binding.registerButton.text = "Register now"

                            Log.i("new response",response.message())
                            Log.i("new response",response.code().toString())
                            Log.i("new response", response.errorBody()?.charStream()?.readText().toString())
                            Toast.makeText(this@DescriptionActivity,"You've already registered.",Toast.LENGTH_LONG).show()
                        }


                    }

                    override fun onFailure(call: Call<CreateTeamResponse?>, t: Throwable) {
                        binding.registerButton.isEnabled = true
                        binding.registerButton.text = "Register now"
                        Log.i("new response","Onfailure of ct")
                        Toast.makeText(this@DescriptionActivity, "Please check internet connection!",Toast.LENGTH_LONG).show()
                    }
                })



                // call register api
//                val regeventapi = retrofitBuilder.create(RegisterEventApi::class.java)
//                val data = RegisterEventInfo(Token.token,teamcode, obj.id!!)
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
//                            Log.i("response2",response.message())
//                        }
//
//
//                    }
//
//                    override fun onFailure(call: Call<RegisterEventResponse?>, t: Throwable) {
//                        Log.i("failed2","failure")
//                    }
//                })

            }
        }

        else{
            binding.enterButton.text = "Max. "+obj.teamsize+" members"
            binding.registerButton.setText("Create / Join Team")
            binding.registerButton.setOnClickListener {

                var team_name = "123vivek"

                // create team intent
                Log.i("response3",Token.token)


                val dialog = BottomSheetDialog(this)
                val view = layoutInflater.inflate(R.layout.bottom_dialog, null)

                val createTeamCall = view.findViewById<Button>(R.id.create_team_button)
                val joinTeamCall = view.findViewById<Button>(R.id.join_team_button)
                val contactDevsButton =  view.findViewById<TextView>(R.id.contact_devs)
                contactDevsButton.setOnClickListener {
                    // Open feedback form
                    val intent = Intent(android.content.Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://forms.gle/CdszgvwSvs8YBVPz7")
                    startActivity(intent)
                }

                createTeamCall.setOnClickListener {
                    var teamcode = ""
                    if (view.findViewById<TextView>(R.id.team_name).text.isEmpty()){
                        Toast.makeText(this@DescriptionActivity, "Please fill a Team name.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    createTeamCall.isEnabled = false
                    createTeamCall.text = "Registering..."

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
                                        createTeamCall.isEnabled = true
                                        createTeamCall.text = "Create team"
                                        if(response1.isSuccessful){
                                            Log.i("response2","registered successfully")
//                                            Toast.makeText(this@DescriptionActivity,"Registered successfully: " + teamcode,Toast.LENGTH_LONG).show()
//                                            binding.registerButton.setText("registered")
                                            dialog.dismiss()
                                            // Shows a dialog box showing Share option
                                            val builder = AlertDialog.Builder(this@DescriptionActivity)
                                            builder.setMessage("Team Code : "+ teamcode + "\n\nShare the team code now to your teammates so they may join the team.\n" +
                                                    "Also, please check your email inbox for further details about the event, noting that the email may be in your spam folder.")
                                                .setPositiveButton("Share",
                                                    DialogInterface.OnClickListener { dialog, id ->
//                                                        Toast.makeText(this@DescriptionActivity, "Sending whatsapp", Toast.LENGTH_SHORT).show()
                                                        // Sharing the team Code on Whatsapp / any other social media
                                                        val shareIntent = Intent()
                                                        shareIntent.action = Intent.ACTION_SEND
                                                        shareIntent.type="text/plain"
                                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey I have registered for "+obj.eventName + "\n\nOur team code is: " + teamcode + "\n\nJoin from website https://iitgoa.ac.in/cepheus/ or from Cepheus'23 App (Available now on Google PlayStore).");
                                                        startActivity(shareIntent)
                                                    })
                                                .setNegativeButton("Cancel",
                                                    DialogInterface.OnClickListener { dialog, id ->
                                                        // User cancelled the dialog
                                                    })
                                            builder.setTitle("Successfully Registered")
                                            val alert = builder.create()
                                            alert.setOnShowListener( {
                                                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(
                                                    androidx.appcompat.R.color.primary_dark_material_dark))
                                                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(
                                                    androidx.appcompat.R.color.primary_dark_material_dark));
                                            })
                                            alert.show()
                                        }
                                        else{
                                            val resCode = response1.code().toString() // IMP for check 401
                                            if(!checkFor401(resCode)) {
                                                val errorMessage = response1.errorBody()?.charStream()?.readText().toString() // IMP to be on 1st line

                                                Log.i("response2","error in registration")
                                                Log.i("response2",errorMessage)
                                                Log.i("response2",response1.message())
                                                Log.i("response2",resCode)

                                                if(errorMessage  == "{\"message\":\"user is already registered\"}")
                                                    Toast.makeText(this@DescriptionActivity,"You have already registered in other team.",Toast.LENGTH_LONG).show()
                                                if(errorMessage  == "{\"message\":\"team code not found\"}")
                                                    Toast.makeText(this@DescriptionActivity,"Incorrect team code.",Toast.LENGTH_LONG).show()
                                                if(errorMessage  == "{\"message\":\"team code is not applicable for this event\"}")
                                                    Toast.makeText(this@DescriptionActivity,"This team code is not valid for this event",Toast.LENGTH_LONG).show()
                                                if(errorMessage  == "{\"message\":\"this event is not meant for your grade\"}")
                                                    Toast.makeText(this@DescriptionActivity,"Event not for your grade/age.",Toast.LENGTH_LONG).show()
                                                if(errorMessage  == "{\"message\":\"team is full\"}")
                                                    Toast.makeText(this@DescriptionActivity,"This team is full!",Toast.LENGTH_LONG).show()
                                                if(errorMessage  == "{\"message\":\"Cepheus is no longer accepting new registrations\"}"){
                                                    Toast.makeText(this@DescriptionActivity,"Event registration is closed",Toast.LENGTH_LONG).show()
                                                }
//                                            Toast.makeText(this@DescriptionActivity,ee101,Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }

                                    override fun onFailure(call: Call<RegisterEventResponse?>, t: Throwable) {
                                        createTeamCall.isEnabled = true
                                        createTeamCall.text = "Create team"

                                        Log.i("failed2","failure of reg")
                                        Toast.makeText(this@DescriptionActivity,"Please check internet connection or contact developers!",Toast.LENGTH_LONG).show()

                                    }
                                })
                            }
                            else{
                                createTeamCall.isEnabled = true
                                createTeamCall.text = "Create team"
                                val resCode = response.code().toString() // IMP for check 401
                                if(!checkFor401(resCode)) {

                                    Log.i("response2", "error in team creation")
                                    Log.i("response2", response.body().toString())
                                    Log.i(
                                        "response2",
                                        response.errorBody()?.charStream()?.readText().toString()
                                    )
                                    Log.i("response2", response.message())
                                    Log.i("response2", resCode)

                                    Toast.makeText(
                                        this@DescriptionActivity,
                                        "You've already registered.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<CreateTeamResponse?>, t: Throwable) {
                            createTeamCall.isEnabled = true
                            createTeamCall.text = "Create team"
                            Log.i("failed2","Onfailure of ct")
                            Toast.makeText(this@DescriptionActivity,"Couldn't connect to server. Please check internet connection!",Toast.LENGTH_LONG).show()
                        }
                    })

//                    Toast.makeText(this@DescriptionActivity, "Create team text:" + view.findViewById<TextView>(R.id.team_name).text.toString(), Toast.LENGTH_SHORT).show()
//                    Toast.makeText(this@DescriptionActivity, "Join team text:" + view.findViewById<TextView>(R.id.team_id).text.toString(), Toast.LENGTH_SHORT).show()
                }
                joinTeamCall.setOnClickListener  {

                    Log.i("response 4", "Working join team")
                    if (view.findViewById<TextView>(R.id.team_id).text.isEmpty()){
                        Toast.makeText(this@DescriptionActivity, "Please give a Team code.", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }

                    joinTeamCall.isEnabled = false
                    joinTeamCall.text = "Registering..."

                    val team_id = view.findViewById<TextView>(R.id.team_id).text.toString()

                    val regeventapi = retrofitBuilder.create(RegisterEventApi::class.java)
                    val data = RegisterEventInfo(Token.token,team_id, obj.id!!)
                    val call = regeventapi.eventregistration(data)
                    call.enqueue(object : Callback<RegisterEventResponse?> {
                        override fun onResponse(
                            call: Call<RegisterEventResponse?>,
                            response: Response<RegisterEventResponse?>
                        ) {
                            joinTeamCall.isEnabled = true
                            joinTeamCall.text = "Join team"
                            if(response.isSuccessful){


//                                Toast.makeText(this@DescriptionActivity,"Joined Team and Registered Successfully",Toast.LENGTH_LONG).show()

                                dialog.dismiss()
                                // Shows a dialog box showing Share option
                                val builder = AlertDialog.Builder(this@DescriptionActivity)
                                builder.setMessage("Please check your email inbox for further details about the event, noting that the email may be in your spam folder.")
                                    .setPositiveButton("OK",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            // On OK Clicked
                                        })
                                builder.setTitle("Successfully Registered")
                                val alert = builder.create()
                                alert.setOnShowListener {
                                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(androidx.appcompat.R.color.primary_dark_material_dark))
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(androidx.appcompat.R.color.primary_dark_material_dark))
                                }
                                alert.show()

                            }
                            else{

                                val resCode = response.code().toString() // IMP for check 401
                                if(!checkFor401(resCode)){
                                    val errorMessage = response.errorBody()?.charStream()?.readText().toString() // IMP to be on 1st line

                                    Log.i("response2","error in registration")
                                    Log.i("response2",errorMessage)
                                    Log.i("response2",response.message())
                                    Log.i("response2",resCode)

                                    if(errorMessage  == "{\"message\":\"user is already registered\"}")
                                        Toast.makeText(this@DescriptionActivity,"You have already registered in other team.",Toast.LENGTH_LONG).show()
                                    if(errorMessage  == "{\"message\":\"team code not found\"}")
                                        Toast.makeText(this@DescriptionActivity,"Incorrect team code.",Toast.LENGTH_LONG).show()
                                    if(errorMessage  == "{\"message\":\"team code is not applicable for this event\"}")
                                        Toast.makeText(this@DescriptionActivity,"This team code is not valid for this event",Toast.LENGTH_LONG).show()
                                    if(errorMessage  == "{\"message\":\"this event is not meant for your grade\"}")
                                        Toast.makeText(this@DescriptionActivity,"Event not for your grade/age.",Toast.LENGTH_LONG).show()
                                    if(errorMessage  == "{\"message\":\"team is full\"}")
                                        Toast.makeText(this@DescriptionActivity,"This team is full!",Toast.LENGTH_LONG).show()
                                    if(errorMessage  == "{\"message\":\"Cepheus is no longer accepting new registrations\"}"){
                                        Toast.makeText(this@DescriptionActivity,"Event registration is closed",Toast.LENGTH_LONG).show()
                                    }
                                }


                            }
                        }
                        override fun onFailure(call: Call<RegisterEventResponse?>, t: Throwable) {
                            joinTeamCall.isEnabled = true
                            joinTeamCall.text = "Join team"
                            Toast.makeText(this@DescriptionActivity,"Couldn't connect to server. Please check internet connection!",Toast.LENGTH_SHORT).show()
                        }
                    })
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
            val activityIntent = Intent(this@DescriptionActivity, SigninActivity::class.java)
            startActivity(activityIntent)
            Toast.makeText(applicationContext, "Session Expired.", Toast.LENGTH_LONG).show()

            return true
        }
        return false
    }


    private fun setData(obj:EventData,eventImg:Int)
    {
        binding.eventName.text = obj.eventName
        val id = obj.eventName
        binding.eventdec.text = obj.overview
        binding.hostname.text = obj.host
        binding.contactno.text = obj.phone
        binding.eventTime.text = obj.time.toString()
        if(obj.id==1){
            binding.ivEventImage.setImageResource(R.drawable.loremipsum2)
        }
        else if (obj.id==2){
            binding.ivEventImage.setImageResource(R.drawable.hackof2)
        }
        else if (obj.id==3){
            binding.ivEventImage.setImageResource(R.drawable.circuitdil2)
        }
        else if (obj.id==4){
            binding.ivEventImage.setImageResource(R.drawable.datascihack2)
        }
        else if (obj.id==5){
            binding.ivEventImage.setImageResource(R.drawable.hackthegame2)
        }
        else if (obj.id==6){
            binding.ivEventImage.setImageResource(R.drawable.ctf2)
        }
        else if (obj.id==7){
            binding.ivEventImage.setImageResource(R.drawable.fizzbuzz2)
        }
        else if (obj.id==8){
            binding.ivEventImage.setImageResource(R.drawable.onth2)
        }
        else if (obj.id==9){
            binding.ivEventImage.setImageResource(R.drawable.bridgeb2)
        }
        else if (obj.id==10){
            binding.ivEventImage.setImageResource(R.drawable.copythenature2)
        }
        else if (obj.id==11){
            binding.ivEventImage.setImageResource(R.drawable.rulethemark2)
        }
        else if (obj.id==12){
            binding.ivEventImage.setImageResource(R.drawable.launchgala2)
        }
        else if (obj.id==13){
            binding.ivEventImage.setImageResource(R.drawable.kbc2)
        }
        else if (obj.id==14){
            binding.ivEventImage.setImageResource(R.drawable.mazeamaze2)
        }
        else if (obj.id==15){
            binding.ivEventImage.setImageResource(R.drawable.scratch2)
        }
        else if (obj.id==16){
            binding.ivEventImage.setImageResource(R.drawable.th2)
        }
        else if (obj.id==17){
            binding.ivEventImage.setImageResource(R.drawable.buymycode2)
        }
        else if (obj.id==18){
            binding.ivEventImage.setImageResource(R.drawable.pareitdown2)
        }
        else if (obj.id==19){
            binding.ivEventImage.setImageResource(R.drawable.gametheory2)
        }
        else if (obj.id==20){
            binding.ivEventImage.setImageResource(R.drawable.arduino2)
        }
        else if (obj.id==21){
            binding.ivEventImage.setImageResource(R.drawable.ebike2)
        }

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


