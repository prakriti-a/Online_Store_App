package com.prakriti.onlinestoreapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.prakriti.onlinestoreapp.databinding.ActivityViewCartBinding

class ViewCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "View Your Cart"

        var cartItemsUrl = "http://192.168.174.31/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartItemsList = ArrayList<String>()

        var requestQ = Volley.newRequestQueue(this)
        var cartItemsRequest = JsonArrayRequest(Request.Method.GET, cartItemsUrl, null,
            { response ->
                // iterate iver array of json objects
                for (objIndex in 0.until(response.length())) {
                    var jsonObj = response.getJSONObject(objIndex)
                    cartItemsList.add(jsonObj.getInt("id").toString() + "\n" + jsonObj.getString("name") + "\n" +
                        jsonObj.getInt("price") + "\n" + jsonObj.getString("email") + "\n" + jsonObj.getInt("quantity"))
                }
                var cartItemsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cartItemsList)
                    // can set custom adapter & layout
                binding.listviewUsersCart.adapter = cartItemsAdapter
            },
            { error ->
                Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            })
        requestQ.add(cartItemsRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.cartContinueShopping -> {
                // go to home screen
                startActivity(Intent(this, HomePage::class.java))
                finish()
            }

            R.id.cartVerifyOrder -> {
                // verify order & create invoice in table, & delete value from temp_order
                var verifyOrderUrl = "http://192.168.174.31/OnlineStoreApp/verify_order.php?email=${Person.email}"
                var reqQueue = Volley.newRequestQueue(this)

                var stringRequest = StringRequest(Request.Method.GET, verifyOrderUrl,
                        { response ->
                            // move to Confirm page
                            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                            var intent = Intent(this, ConfirmOrderActivity::class.java)
                            intent.putExtra("LATEST_INVOICE_NUM", response)
                            startActivity(intent)
                            finish()
                        },
                        { error ->
                            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                            error.printStackTrace()
                        })
                reqQueue.add(stringRequest)
            }

            R.id.cartDeclineOrder -> {
                // remove orders from listview & db
                var deleteOrdersUrl = "http://192.168.174.31/OnlineStoreApp/decline_order.php?email=${Person.email}"
                var reqQueue = Volley.newRequestQueue(this)

                var stringRequest = StringRequest(Request.Method.GET, deleteOrdersUrl,
                    { response ->
                        // back to home screen -> // change to previous e products screen
                        Toast.makeText(this, "Cart has been emptied", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomePage::class.java))
                        finish()
                    },
                    { error ->
                        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                        error.printStackTrace()
                    })
                reqQueue.add(stringRequest)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}