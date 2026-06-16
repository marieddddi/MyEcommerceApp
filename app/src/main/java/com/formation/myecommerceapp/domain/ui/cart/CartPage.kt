package com.formation.myecommerceapp.domain.ui.cart

import android.icu.util.Currency
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.formation.myecommerceapp.R
import com.formation.myecommerceapp.domain.ui.cart.component.ProductInCartListItem
import com.formation.myecommerceapp.domain.ui.cart.state.Cart
import com.formation.myecommerceapp.domain.ui.cart.state.ProductInCart
import java.text.NumberFormat

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CartPage(
    cart: Cart,
    onProductTapped: (ProductInCart) -> Unit,
    onIncreaseQuantityTapped: (ProductInCart) -> Unit,
    onDecreaseQuantityTapped: (ProductInCart) -> Unit,
    onBackPressed: () -> Unit,
) {
    val currencyFormat = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("EUR").toJavaCurrency()
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.cart_page_title)) },
                navigationIcon = {
                    IconButton(onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back_to_product_list),
                        )
                    }
                }
            )
        },
        // montant total
        bottomBar = {
            if (cart.products.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total :",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = currencyFormat.format(cart.totalPrice),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Passer la commande", fontSize = 16.sp)
                        }
                    }
                }
            }
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
                    onProductTapped = { onProductTapped(product) },
                    onIncreaseClick = { onIncreaseQuantityTapped(product) },
                    onDecreaseClick = { onDecreaseQuantityTapped(product) }
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
                quantity = 2
            ),
            ProductInCart(
                id = 2,
                name = "T-Shirt",
                imageDrawable = "",
                price = 25.00,
                quantity= 1
            )
        )
    )

    CartPage(
        cart = cart,
        onProductTapped = { product ->
            Toast.makeText(context, "Product ${product.name} tapped", Toast.LENGTH_SHORT).show()
        },
        onIncreaseQuantityTapped = { product ->
            Toast.makeText(context, "Product ${product.name} quantity +1", Toast.LENGTH_SHORT).show()
        },
        onDecreaseQuantityTapped = { product ->
            Toast.makeText(context, "Product ${product.name} quantity -1", Toast.LENGTH_SHORT).show()
        },
        onBackPressed = {
            Toast.makeText(context, "Back arrow pressed", Toast.LENGTH_SHORT).show()
        }
    )
}