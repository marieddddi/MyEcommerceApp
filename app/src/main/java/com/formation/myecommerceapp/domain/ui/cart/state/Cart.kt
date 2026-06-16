package com.formation.myecommerceapp.domain.ui.cart.state

data class Cart(
    val products: List<ProductInCart>
) {
    // calcul du total du panier
    val totalPrice: Double
        get() = products.sumOf { it.price * it.quantity }
}