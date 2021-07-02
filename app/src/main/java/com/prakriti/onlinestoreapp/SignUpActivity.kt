package com.prakriti.onlinestoreapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.prakriti.onlinestoreapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            if(binding.edtSignupPassword.text.toString().equals(binding.edtSignupPasswordConfirm.text.toString())) {
                // register
                var email = binding.edtSignupEmail.text.toString().trim()
                var username = binding.edtSignupUsername.text.toString().trim()
                var password = binding.edtSignupPassword.text.toString().trim()
                val signupUrl = "http://192.168.174.31/OnlineStoreApp/add_new_user.php?email=$email&username=$username&password=$password"

                val requestQ = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(Request.Method.GET, signupUrl,
                        { response ->
                            if(response.equals("User has signed up successfully!")) {
                                // track user is signed in
                                Person.email = email
                                Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, HomePage::class.java))
                                finish()
                            }
                            else if(response.equals("A user with this email address already exists!")) {
                                AlertDialog.Builder(this).setTitle("Alert").setMessage(response).create().show()
                            }
                        },
                        { error ->
                            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                            error.printStackTrace()
                        })
                requestQ.add(stringRequest)
            }
            else {
                AlertDialog.Builder(this).setTitle("Password Mismatch").setMessage("Please enter the correct password").create().show()
            }
        }

        binding.btnGoToLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}