package com.formation.myecommerceapp.ui.cart

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.formation.myecommerceapp.R
import com.formation.myecommerceapp.ui.cart.component.ProductInCartListItem
import com.formation.myecommerceapp.ui.cart.state.Cart
import com.formation.myecommerceapp.domain.ui.cart.state.ProductInCart

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CartPage(
    cart: Cart,
    onProductTapped: (ProductInCart) -> Unit,
    onRemoveProductFromCartTapped: (ProductInCart) -> Unit,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.cart_page_title))
                },
                navigationIcon = {
                    IconButton(onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back_to_product_list),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(cart.products) { product ->
                ProductInCartListItem(
                    product = product,
                    onProductTapped = {
                        onProductTapped(product)
                    },
                    onRemoveFromCartTapped = {
                        onRemoveProductFromCartTapped(product)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun CartPagePreview() {
    val context = LocalContext.current

    val cart = Cart(
        products = listOf(
            ProductInCart(
                id = 1,
                name = "Bob",
                imageDrawable = "",
                price = 11.84,
            )
        )
    )

    CartPage(
        cart = cart,
        onProductTapped = { product ->
            Toast.makeText(context, "Product ${product.name} tapped", Toast.LENGTH_SHORT)
                .show()
        },
        onRemoveProductFromCartTapped = { product ->
            Toast.makeText(context, "Product ${product.name} is removed", Toast.LENGTH_SHORT)
                .show()
        },
        onBackPressed = {
            Toast.makeText(context, "Back arrow pressed", Toast.LENGTH_SHORT)
                .show()
        }
    )
}