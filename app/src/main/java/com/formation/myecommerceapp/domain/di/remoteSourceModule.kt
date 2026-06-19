package com.formation.myecommerceapp.domain.di

import com.formation.myecommerceapp.domain.data.remote.service.ProductApiService
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val remoteSourceModule = module {

    // 1. Fournir l'instance Retrofit
    single {
        val contentType = okhttp3.MediaType.parse("application/json")!!
        val jsonConfig = Json {
            ignoreUnknownKeys = true
        }

        Retrofit.Builder()
            .baseUrl("http://192.168.1.95:8080/") // adresse IP du pc
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
    }

    // 2. Fournir le Service API
    single<ProductApiService> {
        get<Retrofit>().create(ProductApiService::class.java)
    }
}