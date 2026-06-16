package com.formation.myecommerceapp.ui.productlist.component

import android.icu.util.Currency
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.formation.myecommerceapp.R
import com.formation.myecommerceapp.ui.productlist.state.Product
import com.formation.myecommerceapp.utils.ImageConverter
import java.text.NumberFormat

@Composable
fun ProductListItem(product: Product, onProductTapped: () -> Unit) {
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
        onClick = onProductTapped,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val bitmap = android.util.Base64.decode(product.imageDrawable, android.util.Base64.DEFAULT)
                .let { android.graphics.BitmapFactory.decodeByteArray(it, 0, it.size) }
                ?.asImageBitmap()

            if (bitmap != null) {
                Image(
                    modifier = Modifier.size(156.dp),
                    bitmap = bitmap,
                    contentDescription = stringResource(
                        R.string.product_content_description,
                        product.name,
                    ),
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(product.name)
                    Text(product.description)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val statusResourceKey = if (product.isAvailable) {
                            R.string.product_available
                        } else {
                            R.string.product_sold_out
                        }
                        Text(stringResource(statusResourceKey))
                        Icon(
                            imageVector = Icons.Default.Circle,
                            modifier = Modifier.size(4.dp),
                            contentDescription = stringResource(R.string.status_and_price_separator),
                        )
                        val priceFormatter = NumberFormat.getCurrencyInstance().apply {
                            currency = Currency.getInstance("EUR").toJavaCurrency()
                        }
                        val price = priceFormatter.format(product.price)
                        Text(price)
                    }
                    IconButton(onClick = { onProductTapped() }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = stringResource(R.string.select_product_content_description),
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun ProductListItemPreview() {
    val context = LocalContext.current

    val product = Product(
        id = 1,
        name = "Bob",
        description = "Votre meilleur ami pendant les journées ensoleillées.",
        imageDrawable = "",
        isAvailable = true,
        price = 11.84,
    )

    ProductListItem(
        product = product,
        onProductTapped = {
            Toast.makeText(context, "Product clicked", Toast.LENGTH_SHORT)
                .show()
        }
    )
}
