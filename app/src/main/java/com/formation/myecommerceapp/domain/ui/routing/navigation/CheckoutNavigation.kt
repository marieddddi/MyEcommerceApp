package com.formation.myecommerceapp.domain.ui.routing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.checkout.CheckoutPage
import com.formation.myecommerceapp.domain.ui.routing.CheckoutRoute

fun NavGraphBuilder.checkoutNavigation(
    onOrderValidated: () -> Unit,
    navigateBack: () -> Unit,
) {
    composable<CheckoutRoute> {
        CheckoutPage(
            onOrderValidated = onOrderValidated,
            onBackPressed = navigateBack
        )
    }
}

fun NavController.navigateToCheckout() {
    navigate(CheckoutRoute)
}
