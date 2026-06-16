package com.formation.myecommerceapp.domain.mapper

import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity
import com.formation.myecommerceapp.domain.domain.mapper.toProductInCart
import com.formation.myecommerceapp.ui.cart.state.Cart

fun List<ProductEntity>.toCart() = Cart(
    products = map { product ->
        product.toProductInCart()
    }
)