package com.prakriti.onlinestoreapp

class EProduct {

    private var id: Int
    private var name: String
    private var price: Int
    private var brand: String
    private var image: String

    constructor(id:Int, name:String, price:Int, brand:String, image:String) {
        this.id = id
        this.name = name
        this.price = price
        this.brand = brand
        this.image = image
    }

    fun getId(): Int {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getPrice(): Int {
        return price
    }

    fun getBrand(): String {
        return brand
    }

    fun getImage(): String {
        return image
    }
}