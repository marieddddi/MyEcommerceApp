package com.formation.myecommerceapp.ui.routing

import kotlinx.serialization.Serializable

@Serializable
object ProductListRoute

@Serializable
data class ProductDetailsRoute(
    val productId: Int,
)

@Serializable
object CartRoute
