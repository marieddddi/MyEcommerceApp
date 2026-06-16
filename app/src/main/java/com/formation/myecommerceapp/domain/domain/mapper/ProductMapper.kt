package com.formation.myecommerceapp.domain.domain.mapper

import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity
import com.formation.myecommerceapp.domain.ui.cart.state.ProductInCart
import com.formation.myecommerceapp.domain.ui.productdetails.state.ProductDetails
import com.formation.myecommerceapp.ui.productlist.state.Product

fun ProductEntity.toProduct() = Product(
    id = id,
    name = name,
    description = description,
    imageDrawable = imageDrawable,
    isAvailable = isAvailable,
    price = price,
)

fun ProductEntity.toProductDetails() = ProductDetails(
    id = id,
    name = name,
    description = description,
    imageDrawable = imageDrawable,
    isAvailable = isAvailable,
    price = price,
    category = category,
    isFavorite = isFavorite,
    averageMark = averageMark,
    quantityInCart = quantityInCart,
    markCount = markCount,
)

fun ProductEntity.toProductInCart() = ProductInCart(
    id = id,
    name = name,
    imageDrawable = imageDrawable,
    price = price,
    quantity = quantityInCart,
)

fun ProductDetails.toEntity() = ProductEntity(
    id = id,
    name = name,
    description = description,
    imageDrawable = imageDrawable,
    isAvailable = isAvailable,
    price = price,
    category = category,
    isFavorite = isFavorite,
    averageMark = averageMark,
    markCount = markCount,
    quantityInCart = quantityInCart,
)