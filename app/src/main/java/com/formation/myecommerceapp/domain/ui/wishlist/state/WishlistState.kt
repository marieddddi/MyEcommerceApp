package com.formation.myecommerceapp.domain.ui.wishlist.state

import com.formation.myecommerceapp.domain.ui.productdetails.state.ProductDetails

data class WishlistState(
    val products: List<ProductDetails> // ou ton type d'affichage liste, à adapter
)