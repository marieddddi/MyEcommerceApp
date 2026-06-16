package com.formation.myecommerceapp.ui.productdetails

import android.icu.util.Currency
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastRoundToInt
import com.formation.myecommerceapp.R
import com.formation.myecommerceapp.ui.productdetails.component.AddCartButton
import com.formation.myecommerceapp.ui.productdetails.component.RemoveCartButton
import com.formation.myecommerceapp.domain.ui.productdetails.state.ProductDetails
import com.formation.myecommerceapp.utils.ImageConverter
import java.text.NumberFormat
import kotlin.math.floor

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProductDetailsPage(
    product: ProductDetails,
    addToCart: () -> Unit,
    removeFromCart: () -> Unit,
    toggleFavoriteStatus: () -> Unit,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.product_details_title))
                },
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
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.height(156.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = product.name, fontSize = 28.sp)
                        Text(text = product.category, fontSize = 20.sp)

                        val formatNumber = NumberFormat.getCurrencyInstance().apply {
                            currency = Currency.getInstance("EUR").toJavaCurrency()
                        }
                        val price = formatNumber.format(product.price)
                        Text(price)
                    }

                    Row {
                        if (product.isInCart) {
                            RemoveCartButton(removeFromCart)
                        } else {
                            AddCartButton(addToCart)
                        }
                        IconButton(toggleFavoriteStatus) {
                            if (product.isFavorite) {

                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = stringResource(R.string.remove_from_favorite_button_content_description)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.StarBorder,
                                    contentDescription = stringResource(R.string.add_to_favorite_button_content_description)
                                )
                            }
                        }
                    }
                }
            }
            Text(product.description)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (product.markCount > 0) {
                    Row {
                        repeat(floor(product.averageMark).toInt()) { index ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                stringResource(R.string.filled_star_representing_mark, index)
                            )
                        }
                        if (product.averageMark % 1 >= 0.5) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                                stringResource(R.string.half_filled_star_representing_mark)
                            )
                        }
                        repeat(5 - product.averageMark.fastRoundToInt()) {
                            Icon(
                                imageVector = Icons.Default.StarBorder,
                                stringResource(R.string.empty_star_representing_mark)
                            )
                        }
                    }
                    Text(
                        pluralStringResource(
                            R
                                .plurals.product_average_mark_and_count,
                            product.markCount,
                            product.averageMark, product.markCount
                        )
                    )
                } else {
                    Text(stringResource(R.string.product_with_no_mark))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsPagePreview() {
    val context = LocalContext.current

    var product = ProductDetails(
        id = 1,
        name = "Bob",
        description = "Votre meilleur ami pendant les journées ensoleillées.",
        imageDrawable = "",
        isAvailable = true,
        price = 11.84,
        category = "Hat",
        isFavorite = false,
        averageMark = 0.00,
        markCount = 0,
        isInCart = true,
    )

    ProductDetailsPage(
        product = product,
        addToCart = {
            product = product.copy(isInCart = true)
        },
        removeFromCart = {
            product = product.copy(isInCart = false)
        },
        toggleFavoriteStatus = {
            product = product.copy(isFavorite = !product.isFavorite)
        },
        navigateBack = {
            Toast.makeText(context, "Navigating to previous screen", Toast.LENGTH_SHORT)
                .show()
        }
    )
}
