package com.formation.myecommerceapp.domain.ui.routing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.formation.myecommerceapp.domain.ui.routing.navigation.navigateToWishlist
import com.formation.myecommerceapp.domain.ui.routing.navigation.wishlistNavigation
import com.formation.myecommerceapp.domain.ui.routing.navigation.checkoutNavigation
import com.formation.myecommerceapp.domain.ui.routing.navigation.navigateToCheckout
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
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
            navigateToCheckout = navController::navigateToCheckout,
            navigateBack = navController::navigateUp,
        )

        checkoutNavigation(
            onOrderValidated = {
                navController.popBackStack(ProductListRoute, false)
            },
            navigateBack = navController::navigateUp
        )

        wishlistNavigation(
                navigateToProductDetails = navController::navigateToProductDetails,
                navigateBack = navController::navigateUp,
            )
        }
    }
}