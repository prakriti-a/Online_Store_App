package com.prakriti.onlinestoreapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.prakriti.onlinestoreapp.databinding.FragmentQuantityDialogBinding

class QuantityDialogFragment : DialogFragment() {

    private var _binding: FragmentQuantityDialogBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuantityDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        // refer to UI
        if(binding.edtQuantity.text.equals("")) {
            binding.edtQuantity.error = "Please specify the quantity"
        }
        else {
            var quantity = binding.edtQuantity.text.toString()
            binding.imgAddToCart.setOnClickListener {
                var tempOrderUrl = "http://192.168.174.31/OnlineStoreApp/insert_temporary_order.php?email=" +
                            Person.email + "&product_id=" + Person.addToCartProductID + "&quantity=" + quantity;
                // Person accesses email of logged in user and selected prouct's id
                var requestQ = Volley.newRequestQueue(activity) // gets ShowEProducts activity
                var tempOrderRequest = StringRequest(Request.Method.GET, tempOrderUrl,
                    { response ->
                        // after storing to temporary_orders table
                        Toast.makeText(activity, "Added to Cart!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity, ViewCartActivity::class.java))
                    },
                    { error ->
                        Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show()
                        error.printStackTrace()
                    })
                requestQ.add(tempOrderRequest)
            }
        }
        return view
//        return inflater.inflate(R.layout.fragment_quantity_dialog, container, false)
    }

    companion object {
        // Use this factory method to create a new instance of this fragment
        const val TAG = "QuantityDialogFragment"
        // @JvmStatic fun newInstance() {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}