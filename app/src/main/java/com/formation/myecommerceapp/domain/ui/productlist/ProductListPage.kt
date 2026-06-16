package com.formation.myecommerceapp.domain.ui.productlist

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
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
import com.formation.myecommerceapp.ui.productlist.component.ProductListItem
import com.formation.myecommerceapp.ui.productlist.state.Product

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProductListPage(
    products: List<Product>,
    onProductTapped: (Product) -> Unit,
    onCartActionIconTapped: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.padding(horizontal = 8.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.product_list_title))
                },
                actions = {
                    IconButton(onCartActionIconTapped) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = stringResource(R.string.navigate_back_to_product_list),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(products) { product ->
                ProductListItem(product) {
                    onProductTapped(product)
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ProductListPagePreview() {
    val context = LocalContext.current

    ProductListPage(
        products = listOf(
            Product(
                id = 1,
                name = "Bob",
                description = "Votre meilleur ami pendant les journées ensoleillées.",
                imageDrawable = "",
                isAvailable = true,
                price = 11.84,
            )
        ),
        onProductTapped = { product ->
            Toast.makeText(context, "${product.name} tapped !", Toast.LENGTH_SHORT)
                .show()
        },
        onCartActionIconTapped = {
            Toast.makeText(context, "Navigate to cart details !", Toast.LENGTH_SHORT)
                .show()
        },
    )
}