package com.formation.myecommerceapp.domain.ui.productdetails.state

data class ProductDetails(
    val id: Int,
    val name: String,
    val description: String,
    val imageDrawable: String,
    val isAvailable: Boolean,
    val price: Double,
    val category: String,
    val isFavorite: Boolean,
    val quantityInCart: Int,
    val averageMark: Double,
    val markCount: Int,
)