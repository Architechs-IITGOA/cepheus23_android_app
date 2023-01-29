package com.iitgoacepheustwth.cepheus23

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.iitgoacepheustwth.cepheus23.databinding.ActivityDetailsBinding
import com.iitgoacepheustwth.cepheus23.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Removes Dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        val languages = resources.getStringArray(R.array.Languages)


        val standardDropdown = binding.standardId
        val grades = arrayOf("B.Tech/ B.E./ B.Sc./ B.Com or equivalent", "M.Tech / M.Com / M. Sc. or equivalent", "PhD or Equivalent", "8th Class", "9th Class", "10th Class", "11th Class", "12th Class")
        val gradesAdapter = ArrayAdapter<String>(this, R.layout.dropdown_item, grades)
        standardDropdown.setAdapter(gradesAdapter)
        val genderDropdown = binding.gender
        val gender = arrayOf("Female", "Male", "Other")
        val genderAdapter = ArrayAdapter<String>(this, R.layout.dropdown_item, gender)
        genderDropdown.setAdapter(genderAdapter)



//        val token:String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjEwMDUsImluaSI6IkkiLCJncmFkZSI6MTEsInJlZ2lzdGVyZWQiOnRydWUsImlhdCI6MTY3MzAyNTMyNiwiZXhwIjoxNjczMjg0NTI2fQ.h2yj7Jk716yyd4AflPin-WdmrZrXeaRbsROPZpTVSiY"

        binding.enterButton.setOnClickListener {

            val username = binding.usernameId.text.toString()
            val college_name = binding.instituteId.text.toString()
            val phonenumber = binding.phoneId.text.toString()
            val gender = binding.gender.text.toString()
            val grade_str = binding.standardId.text.toString()
            val grade = when (grade_str) {
                "B.Tech/ B.E./ B.Sc./ B.Com or equivalent" -> 13
                "M.Tech / M.Com / M. Sc. or equivalent" -> 14
                "PhD or Equivalent" -> 15
                "8th Class" -> 8
                "9th Class" -> 9
                "10th Class" -> 10
                "11th Class" -> 11
                else -> 12
            }
            Token.token = getDefaults("JWToken").toString()
            var token = Token.token


            if(username.isEmpty() || college_name.isEmpty() || phonenumber.isEmpty() || gender.isEmpty()|| grade_str.isEmpty()){
                Log.i("if Block","3")
                Toast.makeText(this,"Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.enterButton.isEnabled = false
                binding.enterButton.text = "Let me remember you..."

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
                        binding.enterButton.isEnabled = true
                        binding.enterButton.text = "Enter the Multiverse of Cepheus"
                        if(response.isSuccessful){
                            Log.i("response",response.code().toString())

                            val token2 = response.body()?.token.toString()


                            Log.i("newToken",token2)
                            saveregistrationStatuslocally("true", token2)
                            Token.token = getDefaults("JWToken").toString()
                            Log.i("TestingDetails", "JWT updated")


                            Toast.makeText(this@DetailsActivity,"Successfully entered",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DetailsActivity,Homescreen::class.java)
                            finish()
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
                        binding.enterButton.isEnabled = true
                        binding.enterButton.text = "Enter the Multiverse of Cepheus"
                        Log.i("failed",t.message.toString())
                        Toast.makeText(this@DetailsActivity,"Please check your internet connection",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
    private fun saveregistrationStatuslocally(currstatus_register: String, currstatus_token: String ) {
//        val sharedPreferences =getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        editor.putString("register_status", currstatus_register)
        editor.putString("JWToken", currstatus_token)
        editor.apply()
    }

    fun getDefaults(key: String?): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        return preferences.getString(key, null)
    }
}