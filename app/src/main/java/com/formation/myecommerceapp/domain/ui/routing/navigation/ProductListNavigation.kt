package com.formation.myecommerceapp.domain.ui.routing.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.productlist.ProductListPage
import com.formation.myecommerceapp.domain.ui.productlist.ProductListViewModel
import com.formation.myecommerceapp.domain.ui.routing.ProductListRoute
import com.formation.myecommerceapp.domain.ui.shared.ErrorPage
import com.formation.myecommerceapp.domain.ui.shared.LoadingPage
import com.formation.myecommerceapp.domain.utils.Result
import com.formation.myecommerceapp.domain.utils.getExceptionOrNull
import com.formation.myecommerceapp.domain.utils.getOrNull
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.productListNavigation(
    navigateToProductDetails: (Int) -> Unit,
    navigateToCart: () -> Unit,
    navigateToWishlist: () -> Unit,
) {
    composable<ProductListRoute> {
        val viewModel: ProductListViewModel = koinViewModel()

        val stateResult by viewModel.state.collectAsState()

        when (stateResult) {
            is Result.Success -> ProductListPage(
                products = stateResult.getOrNull()!!.products,
                onProductTapped = { product ->
                    navigateToProductDetails(product.id)
                },
                onCartActionIconTapped = navigateToCart,
                onWishlistActionIconTapped = navigateToWishlist,
                onFavoriteToggled = { product ->
                    viewModel.toggleFavorite(product)
                },
            )

            is Result.Loading -> LoadingPage()
            is Result.Error -> ErrorPage(stateResult.getExceptionOrNull()!!)
        }
    }
}
