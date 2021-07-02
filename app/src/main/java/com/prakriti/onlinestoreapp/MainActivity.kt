package com.prakriti.onlinestoreapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.prakriti.onlinestoreapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
                // log in validation
                var email = binding.edtLoginEmail.text.toString().trim()
                var password = binding.edtLoginPassword.text.toString().trim()
                val loginUrl = "http://192.168.174.31/OnlineStoreApp/login_app_user.php?email=" + email + "&password=" + password

                val requestQ = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(Request.Method.GET, loginUrl,
                    { response ->
                        if(response.equals("User logged in successfully!")) {
                            // track logged in user
                            Person.email = email
                            Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                            // transition to home screen
                            startActivity(Intent(this, HomePage::class.java))
                            this.finish()
                        }
                        else if(response.equals("Error: The user does not exist")) {
                            AlertDialog.Builder(this).setTitle("Alert").setMessage(response).create().show()
                        }
                    },
                    { error ->
                        Toast.makeText(this, "Error occurred\nPlease try again", Toast.LENGTH_SHORT).show()
                        error.printStackTrace()
                    })
                requestQ.add(stringRequest)
        }

        binding.btnGoToSignup.setOnClickListener {
//            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}