package com.formation.myecommerceapp.ui.routing.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.cart.CartViewModel
import com.formation.myecommerceapp.domain.ui.cart.CartPage
import com.formation.myecommerceapp.ui.routing.CartRoute
import com.formation.myecommerceapp.ui.shared.ErrorPage
import com.formation.myecommerceapp.ui.shared.LoadingPage
import com.formation.myecommerceapp.utils.Result
import com.formation.myecommerceapp.utils.getExceptionOrNull
import com.formation.myecommerceapp.utils.getOrNull
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.cartNavigation(
    navigateToProductDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    composable<CartRoute> {
        val viewModel: CartViewModel = koinViewModel()
        val stateResult by viewModel.state.collectAsState()

        when (stateResult) {
            is Result.Success -> CartPage(
                cart = stateResult.getOrNull()!!.cart,
                onProductTapped = { product ->
                    navigateToProductDetails(product.id)
                },
                onIncreaseQuantityTapped = { product ->
                    viewModel.increaseProductQuantity(product.id)
                },
                onDecreaseQuantityTapped = { product ->
                    viewModel.decreaseProductQuantity(product.id)
                },
                onBackPressed = navigateBack,
            )

            is Result.Loading -> LoadingPage()
            is Result.Error -> ErrorPage(stateResult.getExceptionOrNull()!!)
        }
    }
}

fun NavController.navigateToCart() {
    navigate(CartRoute)
}