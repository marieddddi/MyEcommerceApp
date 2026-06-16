package com.formation.myecommerceapp.ui.cart.component

import android.icu.util.Currency
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.formation.myecommerceapp.R
import com.formation.myecommerceapp.domain.ui.cart.state.ProductInCart
import com.formation.myecommerceapp.utils.ImageConverter
import java.text.NumberFormat

@Composable
fun ProductInCartListItem(
    product: ProductInCart,
    onProductTapped: () -> Unit,
    onRemoveFromCartTapped: () -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
        onClick = onProductTapped,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.2f),
                contentAlignment = Alignment.CenterEnd,
            ) {
                val bitmap = android.util.Base64.decode(product.imageDrawable, android.util.Base64.DEFAULT)
                    .let { android.graphics.BitmapFactory.decodeByteArray(it, 0, it.size) }
                    ?.asImageBitmap()

                if (bitmap != null) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = stringResource(R.string.product_content_description),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = product.name, fontSize = 20.sp)

                val formatNumber = NumberFormat.getCurrencyInstance().apply {
                    currency = Currency.getInstance("EUR").toJavaCurrency()
                }
                val price = formatNumber.format(product.price)
                Text(text = price)
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {

                Button(onClick = onRemoveFromCartTapped) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = stringResource(R.string.remove_from_cart_content_description)
                    )
                    Text(text = stringResource(R.string.remove_from_cart_label))
                }
            }

        }
    }
}

@Preview
@Composable
fun ProductInCartListItemPreview() {
    val context = LocalContext.current
    val product = ProductInCart(
        id = 1,
        name = "Bob",
        imageDrawable = "",
        price = 11.84,
    )

    ProductInCartListItem(
        product = product,
        onProductTapped = {
            Toast.makeText(context, "Product is tapped", Toast.LENGTH_SHORT)
                .show()
        },
        onRemoveFromCartTapped = {
            Toast.makeText(context, "Product removed from cart", Toast.LENGTH_SHORT)
                .show()
        }
    )
}