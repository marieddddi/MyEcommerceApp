package com.formation.myecommerceapp.domain.ui.routing.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.routing.WishlistRoute
import com.formation.myecommerceapp.domain.ui.wishlist.WishlistPage
import com.formation.myecommerceapp.domain.ui.wishlist.WishlistViewModel
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.wishlistNavigation(
    navigateToProductDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    composable<WishlistRoute> {
        val viewModel: WishlistViewModel = koinViewModel()
        val state by viewModel.state.collectAsState()

        WishlistPage(
            state = state,
            navigateToProductDetails = navigateToProductDetails,
            navigateBack = navigateBack,
        )
    }
}

fun NavController.navigateToWishlist() {
    navigate(WishlistRoute)
}