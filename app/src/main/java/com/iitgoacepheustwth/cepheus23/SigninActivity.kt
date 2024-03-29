package com.iitgoacepheustwth.cepheus23

import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import com.iitgoacepheustwth.cepheus23.APIs.LoginApi
import com.iitgoacepheustwth.cepheus23.databinding.ActivitySigninBinding
import com.iitgoacepheustwth.cepheus23.model.*
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SigninActivity : AppCompatActivity() {

    private var _binding: ActivitySigninBinding? = null
    private val binding get() = _binding!!

    private var oneTapClient: SignInClient? = null
    private var signUpRequest: BeginSignInRequest? = null
    private var signInRequest: BeginSignInRequest? = null
    private var progressBar: ProgressBar? = null

    private val oneTapResult = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()){ result ->
        try {
            val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
            val idToken = credential?.googleIdToken
            when {
                idToken != null -> {
                    progressBar!!.visibility = View.VISIBLE
                    val retrofitBuilder = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://backendcepheus.cf/apiM1/")
                        .build()

                    val lginApi = retrofitBuilder.create(LoginApi::class.java)
                    val loginData = LoginUserInfo(idToken)

                    val apiCall = lginApi.loginUser(loginData)
                    apiCall.enqueue(object : Callback<LoginUserResponse?> {
                        override fun onResponse(
                            call: Call<LoginUserResponse?>,
                            response: Response<LoginUserResponse?>
                        ) {
                            progressBar!!.visibility = View.GONE
                            if(response.isSuccessful){
                                Log.i("login response",response.code().toString())
                                Log.i("login response",response.body()?.token.toString())
                                Log.i("login response", response.body()?.user?.registered.toString())
                                Log.i("login response", response.body().toString())
                                Log.i("login response", response.body()?.user?.id.toString())


                                val responseToken = response.body()?.token.toString()
                                val user_email = response.body()?.user?.email.toString()
                                val user_name = response.body()?.user?.user_name.toString()

                                val uuID = response.body()?.user?.id.toString()
                                val imageURL = response.body()?.user?.image_url.toString()


                                responseToken.trim()
                                Token.token = responseToken
                                Log.i("first jwt",responseToken)




                                if(response.body()?.user?.registered == false){
                                    //                                    Login.login = true
                                    saveLoginStatuslocally("true","false", responseToken, user_email, user_name,uuID,imageURL )
                                    val activityIntent = Intent(this@SigninActivity,DetailsActivity::class.java)
                                    finish()
                                    startActivity(activityIntent)
//                                    Log.i("login response", Login.login.toString())
                                }
                                else{
                                    saveLoginStatuslocally("true","true", responseToken, user_email, user_name,uuID, imageURL )
                                    val activityIntent = Intent(this@SigninActivity,Homescreen::class.java)
                                    finish()
                                    startActivity(activityIntent)
                                }


                            }
                            else{
                                Log.i("login response",response.code().toString())
                                Log.i("login response",response.message().toString())
                                Log.i("login response", response.errorBody()?.charStream()?.readText().toString())
                            }
                        }

                        override fun onFailure(call: Call<LoginUserResponse?>, t: Throwable) {
                            progressBar!!.visibility = View.GONE
                            Toast.makeText(this@SigninActivity,"Please check internet connection",Toast.LENGTH_LONG).show()

                        }

                    })

//                    val msg = "idToken: $idToken"
//                    Snackbar.make(binding.root, msg, Snackbar.LENGTH_INDEFINITE).show()
//                    Log.d("one tap", msg)
                }
                else -> {
                    // Shouldn't happen.
                    Log.d("one tap", "No ID token!")
                    Snackbar.make(binding.root, "No ID token!", Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        } catch (e: ApiException) {
            progressBar!!.visibility = View.GONE
            when (e.statusCode) {
                CommonStatusCodes.CANCELED -> {
                    Log.d("one tap", "One-tap dialog was closed.")
                    // Don't re-prompt the user.
                    Snackbar.make(binding.root, "One-tap dialog was closed.", Snackbar.LENGTH_INDEFINITE).show()
                }
                CommonStatusCodes.NETWORK_ERROR -> {
                    Log.d("one tap", "One-tap encountered a network error.")
                    // Try again or just ignore.
                    Snackbar.make(binding.root, "One-tap encountered a network error.", Snackbar.LENGTH_INDEFINITE).show()
                }
                else -> {
                    Log.d("one tap", "Couldn't get credential from result." +
                            " (${e.localizedMessage})")
                    Snackbar.make(binding.root, "Couldn't get credential from result.\" +\n" +
                            " (${e.localizedMessage})", Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Removes Dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        _binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.issueSigningIn.setOnClickListener{
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://forms.gle/CdszgvwSvs8YBVPz7")
            startActivity(intent)
        }

        progressBar = binding.indeterminateBar
        progressBar!!.visibility = View.GONE

        oneTapClient = Identity.getSignInClient(this)
        signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()

        binding.bvSignin.setOnClickListener {
            displaySignIn()
            progressBar!!.visibility = View.VISIBLE
        }

    }

    private fun displaySignIn(){
        oneTapClient?.beginSignIn(signInRequest!!)
            ?.addOnSuccessListener(this) { result ->
                progressBar!!.visibility = View.GONE
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            ?.addOnFailureListener(this) { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.
                progressBar!!.visibility = View.GONE
//                Toast.makeText(this@SigninActivity,"Please check internet connection",Toast.LENGTH_LONG).show()
                displaySignUp()
                Log.d("btn click", e.localizedMessage!!)
            }
    }

    private fun displaySignUp() {
        oneTapClient?.beginSignIn(signUpRequest!!)
            ?.addOnSuccessListener(this) { result ->
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("btn click", "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            ?.addOnFailureListener(this) { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.
                Toast.makeText(this@SigninActivity,"Please check internet connection",Toast.LENGTH_LONG).show()
//                displaySignUp()
                Log.d("btn click", e.localizedMessage!!)
                Log.d("btn click", e.cause.toString())
                Log.d("btn click", e.message.toString())
            }
    }


    private fun saveLoginStatuslocally(currstatus_login:String, currstatus_register: String, currstatus_token: String, currstatus_email : String, currstatus_name : String, currstatus_uuid : String, currstatus_useravatar: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("Login_status", currstatus_login)
        editor.putString("register_status", currstatus_register)
        editor.putString("JWToken", currstatus_token)
        editor.putString("Email",currstatus_email)
        editor.putString("Name", currstatus_name)
        editor.putString("UniqueUserID", currstatus_uuid)
        editor.putString("ImageURL", currstatus_useravatar)

        editor.apply()
    }




}



