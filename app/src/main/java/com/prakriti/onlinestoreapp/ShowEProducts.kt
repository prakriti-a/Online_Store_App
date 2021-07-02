package com.prakriti.onlinestoreapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.prakriti.onlinestoreapp.databinding.ActivityShowEProductsBinding
import org.json.JSONObject

class ShowEProducts : AppCompatActivity() {

    private lateinit var binding: ActivityShowEProductsBinding
    private lateinit var productsAdapter: EProductsAdapter
    private lateinit var eProductsList: ArrayList<EProduct>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowEProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Buy From Brands"

        eProductsList = ArrayList<EProduct>()
        productsAdapter = EProductsAdapter(this, eProductsList)
        binding.rvEProducts.layoutManager = LinearLayoutManager(this)
        binding.rvEProducts.adapter = productsAdapter

        val selectedBrand = intent.getStringExtra("BRAND")
        binding.txtBrandName.text = "$selectedBrand Products"
        if (selectedBrand != null) {
            getListOfProducts(selectedBrand)
            productsAdapter.notifyDataSetChanged()
        }
    }

    private fun getListOfProducts(brandName: String) {

        val clickedBrandUrl = "http://192.168.174.31/OnlineStoreApp/fetch_eproducts.php?brand=$brandName"
        var jsonProductsReq = JsonArrayRequest(Request.Method.GET, clickedBrandUrl, null,
            { response ->
                // get array, populate view
                for (productIndex in 0.until(response.length())) {
                    // adding EProduct type items [ json array <- json object <- key/value pair ]
                    var product: JSONObject = response.getJSONObject(productIndex)
                    eProductsList.add(EProduct(product.getInt("id"), product.getString("name"), product.getInt("price"),
                        product.getString("brand"), product.getString("image")))
                }
                // controller
                productsAdapter = EProductsAdapter(this, eProductsList)
                // set layout manager for recycler view
                binding.rvEProducts.layoutManager = LinearLayoutManager(this)
                binding.rvEProducts.adapter = productsAdapter
                Toast.makeText(this, "RESPONSE ACHIEVED", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
                // Log.e("PRODUCTS", error.printStackTrace().toString())
                error.printStackTrace()
            })

        val requestQ = Volley.newRequestQueue(this)
        requestQ.add(jsonProductsReq)
    }
}