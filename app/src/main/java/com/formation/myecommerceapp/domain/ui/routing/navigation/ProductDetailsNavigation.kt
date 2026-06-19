package com.formation.myecommerceapp.domain.ui.routing.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.productdetails.ProductDetailsPage
import com.formation.myecommerceapp.domain.ui.productdetails.ProductDetailsViewModel
import com.formation.myecommerceapp.domain.ui.routing.ProductDetailsRoute
import com.formation.myecommerceapp.domain.ui.shared.ErrorPage
import com.formation.myecommerceapp.domain.ui.shared.LoadingPage
import com.formation.myecommerceapp.domain.utils.Result
import com.formation.myecommerceapp.domain.utils.getExceptionOrNull
import com.formation.myecommerceapp.domain.utils.getOrNull
import org.koin.compose.viewmodel.koinViewModel

fun NavController.navigateToProductDetails(productId: Int) {
    navigate(ProductDetailsRoute(productId))
}

fun NavGraphBuilder.productDetailsNavigation(
    navigateBack: () -> Unit,
) {
    composable<ProductDetailsRoute> {
        val viewModel: ProductDetailsViewModel = koinViewModel()
        val stateResult by viewModel.state.collectAsState()

        when (stateResult) {
            is Result.Success -> ProductDetailsPage(
                product = stateResult.getOrNull()!!.product,
                addToCart = viewModel::addProductToCart,
                removeFromCart = viewModel::removeProductFromCart,
                toggleFavoriteStatus = viewModel::toggleFavoriteStatus,
                navigateBack = navigateBack
            )
            is Result.Loading -> LoadingPage()
            is Result.Error -> ErrorPage(stateResult.getExceptionOrNull()!!)
        }
    }
}
