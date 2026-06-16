package com.formation.myecommerceapp.ui.shared

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.formation.myecommerceapp.R

@Composable
fun ErrorPage(exception: Throwable) {
    Log.e("STATE LOADING ERROR", "Error occurred while loading state ${exception.message}")

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = stringResource(R.string.error_while_loading_state),
                fontSize = 36.sp,
                color = Color.Red,
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ErrorPagePreview() {
    ErrorPage(RuntimeException("Exception Sample"))
}