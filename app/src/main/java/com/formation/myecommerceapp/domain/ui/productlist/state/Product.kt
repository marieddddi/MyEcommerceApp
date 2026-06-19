package com.formation.myecommerceapp.domain.ui.productlist.state

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val imageDrawable: String?,
    val isAvailable: Boolean,
    val price: Double,
    val isFavorite: Boolean = false,
)
