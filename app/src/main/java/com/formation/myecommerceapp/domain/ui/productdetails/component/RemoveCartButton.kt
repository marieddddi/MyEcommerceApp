package com.formation.myecommerceapp.ui.productdetails.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveShoppingCart
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
fun RemoveCartButton(removeFromCart: () -> Unit) {
    Button(onClick = removeFromCart) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                imageVector = Icons.Outlined.RemoveShoppingCart,
                contentDescription = stringResource(R.string.remove_from_cart_content_description),
            )
            Text(stringResource(R.string.remove_from_cart_label))
        }
    }
}

@Preview
@Composable
fun RemoveCartButtonPreview() {
    val context = LocalContext.current

    RemoveCartButton(
        removeFromCart = {
            Toast.makeText(context, "Product removed from cart", Toast.LENGTH_SHORT)
                .show()
        }
    )
}