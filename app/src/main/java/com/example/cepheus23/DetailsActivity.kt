package com.example.cepheus23

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.example.cepheus23.databinding.ActivityDetailsBinding
import com.example.cepheus23.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)


//        val token:String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjEwMDUsImluaSI6IkkiLCJncmFkZSI6MTEsInJlZ2lzdGVyZWQiOnRydWUsImlhdCI6MTY3MzAyNTMyNiwiZXhwIjoxNjczMjg0NTI2fQ.h2yj7Jk716yyd4AflPin-WdmrZrXeaRbsROPZpTVSiY"

        binding.enterButton.setOnClickListener {

            val username = binding.usernameId.text.toString()
            val college_name = binding.instituteId.text.toString()
            val phonenumber = binding.phoneId.text.toString()
            val grade = binding.standardId.text.toString()
            Token.token = getDefaults("JWToken").toString()

            var token = Token.token
            if(username.isEmpty() || college_name.isEmpty() || phonenumber.isEmpty()){
                Log.i("if Block","3")
                Toast.makeText(this,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
            else{

                val retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://backendcepheus.cf/apiM1/")
                    .build()

                val regApi = retrofitBuilder.create(RegisterApi::class.java)


                val info = UserInfo(username,college_name,phonenumber, grade.toInt(),token)
                Log.i("userinfo","007")

                val call = regApi.registerUser(info)
                call.enqueue(object : Callback<UserResponse?> {
                    override fun onResponse(
                        call: Call<UserResponse?>,
                        response: Response<UserResponse?>
                    ) {
                        if(response.isSuccessful){
                            Log.i("response",response.code().toString())

                            val token2 = response.body()?.token.toString()


                            Log.i("newToken",token2)
                            saveregistrationStatuslocally("true")
                            Toast.makeText(this@DetailsActivity,"Successfully entered",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DetailsActivity,Homescreen::class.java)
                            startActivity(intent)
                            Log.i("response","switched to Homescreen")
                            val responseCode = response.code()

                            if(responseCode != 200){
                                Registration.registration = false
                                Login.login = false
                                val loginIntent = Intent(this@DetailsActivity,SigninActivity::class.java)
                                startActivity(loginIntent)
                            }
                            else{
                                Registration.registration = false
                            }
                        }
                        else{
                            Log.i("response",response.code().toString())
                            Log.i("response",response.message().toString())
                        }
                    }

                    override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                        Log.i("failed",t.message.toString())
                        Toast.makeText(this@DetailsActivity,"Failure occur",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
    private fun saveregistrationStatuslocally(currstatus_register: String) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("register_status", currstatus_register)
        editor.apply()
    }

    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }
}