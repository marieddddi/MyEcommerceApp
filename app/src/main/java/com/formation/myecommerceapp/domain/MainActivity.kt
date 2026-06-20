package com.formation.myecommerceapp.domain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.formation.myecommerceapp.domain.di.databaseModule
import com.formation.myecommerceapp.domain.di.viewModelModule
import com.formation.myecommerceapp.domain.ui.routing.Router
import com.formation.myecommerceapp.ui.theme.MyEcommerceAppTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration
import remoteSourceModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinApplication(
                configuration = koinConfiguration { modules(viewModelModule, databaseModule,remoteSourceModule) }
            ) {
                MyEcommerceAppTheme {
                    Router()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyEcommerceAppTheme {
        Router()
    }
}