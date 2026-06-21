package com.formation.myecommerceapp.domain.ui.routing

import kotlinx.serialization.Serializable

// Route pour l'écran de connexion
@Serializable
object LoginRoute
@Serializable
object ProductListRoute

@Serializable
data class ProductDetailsRoute(
    val productId: Int,
)

@Serializable
object CartRoute

@Serializable
object CheckoutRoute

@Serializable
object WishlistRoute
