package com.formation.myecommerceapp.ui.productlist.state

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val imageDrawable: String?,
    val isAvailable: Boolean,
    val price: Double,
)