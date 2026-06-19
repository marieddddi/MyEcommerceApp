package com.formation.myecommerceapp.domain.ui.routing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.routing.CartRoute

fun NavController.navigateToCart() {
    navigate(CartRoute)
}

fun NavGraphBuilder.cartNavigation(
    navigateToProductDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    composable<CartRoute> {
        // CartPage(...)
    }
}
