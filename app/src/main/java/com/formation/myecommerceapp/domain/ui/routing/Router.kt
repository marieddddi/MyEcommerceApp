package com.formation.myecommerceapp.ui.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.formation.myecommerceapp.ui.routing.navigation.cartNavigation
import com.formation.myecommerceapp.ui.routing.navigation.navigateToCart
import com.formation.myecommerceapp.ui.routing.navigation.navigateToProductDetails
import com.formation.myecommerceapp.ui.routing.navigation.productDetailsNavigation
import com.formation.myecommerceapp.ui.routing.navigation.productListNavigation

@Composable
fun Router() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ProductListRoute,
    ) {
        productListNavigation(
            navigateToProductDetails = navController::navigateToProductDetails,
            navigateToCart = navController::navigateToCart,
        )

        productDetailsNavigation(navigateBack = navController::navigateUp)

        cartNavigation(
            navigateToProductDetails = navController::navigateToProductDetails,
            navigateBack = navController::navigateUp,
        )
    }
}
