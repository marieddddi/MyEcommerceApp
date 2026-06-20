package com.formation.myecommerceapp.domain.ui.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.formation.myecommerceapp.domain.ui.routing.navigation.cartNavigation
import com.formation.myecommerceapp.domain.ui.routing.navigation.navigateToCart
import com.formation.myecommerceapp.domain.ui.routing.navigation.navigateToProductDetails
import com.formation.myecommerceapp.domain.ui.routing.navigation.navigateToWishlist
import com.formation.myecommerceapp.domain.ui.routing.navigation.productDetailsNavigation
import com.formation.myecommerceapp.domain.ui.routing.navigation.productListNavigation
import com.formation.myecommerceapp.domain.ui.routing.navigation.wishlistNavigation

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
            navigateToWishlist = navController::navigateToWishlist
        )

        productDetailsNavigation(navigateBack = navController::navigateUp)

        cartNavigation(
            navigateToProductDetails = navController::navigateToProductDetails,
            navigateBack = navController::navigateUp,
        )

        wishlistNavigation(
            navigateToProductDetails = navController::navigateToProductDetails,
            navigateBack = navController::navigateUp
        )
    }
}
