package com.formation.myecommerceapp.domain.ui.wishlist

import android.graphics.BitmapFactory
import android.icu.util.Currency
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.formation.myecommerceapp.R
import com.formation.myecommerceapp.domain.ui.wishlist.state.WishlistState
import java.text.NumberFormat

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WishlistPage(
    state: WishlistState,
    navigateToProductDetails: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.wishlist_title)) },
                navigationIcon = {
                    IconButton(navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back_to_product_list),
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.products.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
            ) {
                Text(stringResource(R.string.wishlist_empty))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.products) { product ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigateToProductDetails(product.id) },
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val bitmap = Base64.decode(product.imageDrawable, Base64.DEFAULT)
                            .let { BitmapFactory.decodeByteArray(it, 0, it.size) }
                            ?.asImageBitmap()

                        if (bitmap != null) {
                            Image(
                                modifier = Modifier.size(64.dp),
                                bitmap = bitmap,
                                contentDescription = product.name,
                            )
                        }

                        Column {
                            Text(product.name, fontSize = 18.sp)
                            Text(product.category, fontSize = 14.sp)

                            val formatNumber = NumberFormat.getCurrencyInstance().apply {
                                currency = Currency.getInstance("EUR").toJavaCurrency()
                            }
                            Text(formatNumber.format(product.price), fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}