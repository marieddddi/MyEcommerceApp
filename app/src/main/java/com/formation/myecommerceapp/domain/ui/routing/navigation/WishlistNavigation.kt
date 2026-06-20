package com.formation.myecommerceapp.domain.ui.routing.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.wishlist.WishlistPage
import com.formation.myecommerceapp.domain.ui.wishlist.WishlistViewModel
import com.formation.myecommerceapp.domain.ui.productlist.state.Product
import com.formation.myecommerceapp.domain.utils.Result
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

// 1. Définition de la Route (sérialisable pour le nouveau Navigation Component)
@Serializable
object WishlistRoute

// 2. Fonction d'extension pour simplifier l'appel au trajet
fun NavController.navigateToWishlist() {
    navigate(WishlistRoute)
}

// 3. Le bloc de navigation pour le NavHost
fun NavGraphBuilder.wishlistNavigation(
    navigateToProductDetails: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    composable<WishlistRoute> {
        val viewModel: WishlistViewModel = koinViewModel()
        val stateResult by viewModel.state.collectAsState()

        // On extrait la liste de produits si le Result est un Success
        val products = if (stateResult is Result.Success) {
            (stateResult as Result.Success).data.products
        } else {
            emptyList()
        }

        WishlistPage(
            products = products,
            onProductTapped = { product -> navigateToProductDetails(product.id) },
            onRemoveFavorite = { product -> viewModel.removeFromFavorites(product) },
            navigateBack = navigateBack
        )
    }
}