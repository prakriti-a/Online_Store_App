package com.prakriti.onlinestoreapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.prakriti.onlinestoreapp.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Home Page"

        var brandsList = ArrayList<String>()
        val fetchBrandsUrl = "http://192.168.174.31/OnlineStoreApp/fetch_brands.php"
        val requestQ = Volley.newRequestQueue(this)

        // array of json objects
        var jsonArrayReq = JsonArrayRequest(Request.Method.GET, fetchBrandsUrl, null,
            { response ->
                // get array
                for (obj in 0.until(response.length())) {
                    brandsList.add(response.getJSONObject(obj).getString("brand"))
                }
                // controller
                var brandsListAdapter = ArrayAdapter(this, R.layout.brand_item_row, brandsList)
                binding.listviewBrandsHomePage.adapter = brandsListAdapter
            },
            { error ->
                Toast.makeText(this, "Error occurred\nPlease try again", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
    })
        requestQ.add(jsonArrayReq)

        // on item click listener
        binding.listviewBrandsHomePage.setOnItemClickListener { parent, clicked_view, position, id ->
            val clickedBrand = brandsList.get(position)
            var intent = Intent(this, ShowEProducts::class.java)
            intent.putExtra("BRAND", clickedBrand)
            startActivity(intent)
        }
    }

}