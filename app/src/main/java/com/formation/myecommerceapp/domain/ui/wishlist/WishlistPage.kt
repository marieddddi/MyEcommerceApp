package com.formation.myecommerceapp.domain.ui.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.formation.myecommerceapp.domain.ui.productlist.component.ProductListItem
import com.formation.myecommerceapp.domain.ui.productlist.state.Product

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WishlistPage(
    products: List<Product>,
    onProductTapped: (Product) -> Unit,
    onRemoveFavorite: (Product) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mes Favoris ❤️") },
                navigationIcon = {
                    IconButton(navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (products.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Votre liste d'envies est vide", fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding).padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(products) { product ->
                    ProductListItem(
                        product = product,
                        onProductTapped = { onProductTapped(product) },
                        onFavoriteToggled = { onRemoveFavorite(product) }
                    )
                }
            }
        }
    }
}