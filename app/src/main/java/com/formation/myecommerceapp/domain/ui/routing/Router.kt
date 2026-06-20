package com.formation.myecommerceapp.domain.ui.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.formation.myecommerceapp.domain.ui.routing.navigation.navigateToWishlist
import com.formation.myecommerceapp.domain.ui.routing.navigation.wishlistNavigation
import com.formation.myecommerceapp.ui.routing.navigation.cartNavigation
import com.formation.myecommerceapp.ui.routing.navigation.loginNavigation // Import à ajouter
import com.formation.myecommerceapp.ui.routing.navigation.navigateToCart
import com.formation.myecommerceapp.ui.routing.navigation.navigateToProductDetails
import com.formation.myecommerceapp.ui.routing.navigation.navigateToProductList // Import à ajouter
import com.formation.myecommerceapp.ui.routing.navigation.productDetailsNavigation
import com.formation.myecommerceapp.domain.ui.routing.navigation.productListNavigation

@Composable
fun Router() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
    ) {
        loginNavigation(
            onLoginSuccess = {
                navController.navigateToProductList()
            }
        )

        productListNavigation(
            navigateToProductDetails = navController::navigateToProductDetails,
            navigateToCart = navController::navigateToCart,
            navigateToWishlist = navController::navigateToWishlist,
        )

        productDetailsNavigation(navigateBack = navController::navigateUp)

        cartNavigation(
            navigateToProductDetails = navController::navigateToProductDetails,
            navigateBack = navController::navigateUp,
        )

        wishlistNavigation(
            navigateToProductDetails = navController::navigateToProductDetails,
            navigateBack = navController::navigateUp,
        )
    }
}