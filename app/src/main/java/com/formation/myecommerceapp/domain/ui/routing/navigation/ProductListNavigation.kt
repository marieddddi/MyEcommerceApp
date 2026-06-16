package com.formation.myecommerceapp.ui.routing.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.productlist.ProductListPage
import com.formation.myecommerceapp.domain.ui.productlist.ProductListViewModel
import com.formation.myecommerceapp.ui.routing.ProductListRoute
import com.formation.myecommerceapp.ui.shared.ErrorPage
import com.formation.myecommerceapp.ui.shared.LoadingPage
import com.formation.myecommerceapp.utils.Result
import com.formation.myecommerceapp.utils.getExceptionOrNull
import com.formation.myecommerceapp.utils.getOrNull
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.productListNavigation(
    navigateToProductDetails: (Int) -> Unit,
    navigateToCart: () -> Unit,
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
                onCartActionIconTapped = navigateToCart
            )

            is Result.Loading -> LoadingPage()
            is Result.Error -> ErrorPage(stateResult.getExceptionOrNull()!!)
        }
    }
}