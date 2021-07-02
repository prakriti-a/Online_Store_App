package com.prakriti.onlinestoreapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.prakriti.onlinestoreapp.databinding.ActivityConfirmOrderBinding
import java.math.BigDecimal

class ConfirmOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmOrderBinding
    var totalPrice : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Payment Details"

        var calculatePriceUrl = "http://192.168.174.31/OnlineStoreApp/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUM")}"
        var requestQ = Volley.newRequestQueue(this)

        var stringRequest = StringRequest(Request.Method.GET, calculatePriceUrl,
            { response ->
                // no errors
                binding.btnProcessPayment.text = "Make Payment for Rs. $response via PayPal"
                totalPrice = response.toLong()
            },
                { error ->
                Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            })
        requestQ.add(stringRequest)

        // code for PAYPAL INTEGRATION & CONFIG
        var paypalConfig : PayPalConfiguration = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(MyPaypal.clientID)
            // specify environment & client id on paypal config
        // create & start a service
        var paypalService = Intent(this, PayPalService::class.java)
        paypalService.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
        startService(paypalService) // stop the service to avoid memory leak

        // set paypal service to open on button click
        binding.btnProcessPayment.setOnClickListener { v ->
            // access total price
            var paypalProcessing = PayPalPayment(BigDecimal.valueOf(totalPrice), "INR", "Online Store", PayPalPayment.PAYMENT_INTENT_SALE)
                // pass price, currency format, string to show on page, payment intent
            var paymentIntent = Intent(this, PaymentActivity::class.java) // from Paypal SDK
            paymentIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig)
            paymentIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, paypalProcessing)
            startActivityForResult(paymentIntent, 666)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 666 && resultCode == RESULT_OK) {
            // go to new activity
            startActivity(Intent(this, ThankYou::class.java))
        }
        else {
            Toast.makeText(this, R.string.sorry_error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, PayPalService::class.java)) // no longer used
    }
}