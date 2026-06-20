package com.formation.myecommerceapp.ui.routing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.formation.myecommerceapp.domain.ui.login.LoginPage
import com.formation.myecommerceapp.domain.ui.routing.LoginRoute
import com.formation.myecommerceapp.domain.ui.routing.ProductListRoute

fun NavGraphBuilder.loginNavigation(
    onLoginSuccess: () -> Unit
) {
    composable<LoginRoute> {
        LoginPage(onLoginSuccess = onLoginSuccess)
    }
}
fun NavController.navigateToProductList() {
    this.navigate(ProductListRoute) {
        // On supprime l'écran de login de l'historique pour que
        // le bouton "Retour" du téléphone ne revienne pas sur le login.
        popUpTo<LoginRoute> { inclusive = true }
    }
}