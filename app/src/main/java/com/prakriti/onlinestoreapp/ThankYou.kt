package com.prakriti.onlinestoreapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ThankYou : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)

        /*
        on clicking the pay button, paypal payment page is opened
        click pay with paypal
        login with sandbox acc created for buyer/personal use
        pay after authentication, then thank_you.xml activity is displayed
         */
    }
}