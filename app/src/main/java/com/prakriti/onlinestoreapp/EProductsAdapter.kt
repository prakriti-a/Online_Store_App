package com.prakriti.onlinestoreapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prakriti.onlinestoreapp.databinding.EProductRowBinding
import com.squareup.picasso.Picasso

class EProductsAdapter(var context: Context, var arrayList: ArrayList<EProduct>) : RecyclerView.Adapter<EProductsAdapter.ProductViewHolder>() {

    private lateinit var binding: EProductRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = EProductRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
//        val prodView = LayoutInflater.from(context).inflate(R.layout.e_product_row, parent, false)
//        return ProductViewHolder(prodView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
//        (holder as ProductViewHolder).initializeRowUI(arrayList.get(position).getId(), arrayList.get(position).getName(), arrayList.get(position).getPrice(),
//            arrayList.get(position).getImage())
        holder.binding.txtEProductID.text = arrayList.get(position).getId().toString()
        holder.binding.txtEProductName.text = arrayList.get(position).getName()
        holder.binding.txtEProductPrice.text = arrayList.get(position).getPrice().toString()

        // for image -> avoid having spaces in image name or url
        var imageUrl = "http://192.167.174.31/OnlineStoreApp/osimages/" + arrayList.get(position).getImage()
        imageUrl = imageUrl.replace("", "%20")
        Picasso.get().load(imageUrl).into(holder.binding.imgEProduct)

        // set click listener for add image
        holder.binding.imgAddProduct.setOnClickListener {
            // access product id of clicked product
            Person.addToCartProductID = arrayList.get(position).getId()
            // initialise dialog fragment
            var quantityFragment = QuantityDialogFragment()
            // get Fragment Manager
            // var fragmentManager = (holder.itemView.context as Activity).fragmentmanager
            // quantityFragment.show(QuantityDialogFragment, QuantityDialogFragment.TAG)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    inner class ProductViewHolder(val binding: EProductRowBinding) : RecyclerView.ViewHolder(binding.root) {

//        fun initializeRowUI(id:Int, name:String, price:Int, image:String) {
//            binding = EProductRowBinding.bind(view)
//            // refer to EProduct row to be inflated on recycler view
//            // pre update -> used itemView.[id] = ...
//            binding.txtEProductID.text = id.toString()
//                // (R.string.id + id).toString()
//            binding.txtEProductName.text = name
//            binding.txtEProductPrice.text = price.toString()
//                // (R.string.price + price).toString()
//
//            // for image -> avoid having spaces in image name or url
//            var imageUrl = "http://192.167.174.31/OnlineStoreApp/osimages/" + image
//            imageUrl = imageUrl.replace("", "%20")
//            Picasso.get().load(imageUrl).into(binding.imgEProduct)
    }

}