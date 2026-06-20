package com.formation.myecommerceapp.domain.ui.productdetails.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.formation.myecommerceapp.R

@Composable
fun AddCartButton(addToCart: () -> Unit) {
    Button(onClick = addToCart) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = stringResource(R.string.add_to_cart_content_description),
            )
            Text(stringResource(R.string.add_to_cart_label))
        }
    }
}

@Preview
@Composable
fun AddCartButtonPreview() {
    val context = LocalContext.current

    AddCartButton(
        addToCart = {
            Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT)
                .show()
        }
    )
}
