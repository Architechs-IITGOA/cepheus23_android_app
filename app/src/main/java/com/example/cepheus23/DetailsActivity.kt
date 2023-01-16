package com.example.cepheus23

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.cepheus23.databinding.ActivityDetailsBinding
import com.example.cepheus23.model.Token
import com.example.cepheus23.model.UserInfo
import com.example.cepheus23.model.UserResponse
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
            var token = Token.token


            if(username.isEmpty() || college_name.isEmpty() || phonenumber.isEmpty() || gender.isEmpty()|| grade_str.isEmpty()){
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

                            Token.token = token2


                            Log.i("newToken",token2)
                            Toast.makeText(this@DetailsActivity,"Successfully entered",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DetailsActivity,Homescreen::class.java)
                            startActivity(intent)
                            Log.i("response","switched to Homescreen")
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
}