package com.formation.myecommerceapp.domain.di

import com.formation.myecommerceapp.domain.data.remote.service.EmailApiService
import com.formation.myecommerceapp.domain.data.remote.service.ProductApiService
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val remoteSourceModule = module {

    // 1. Fournir l'instance Retrofit
    single(named("LocalRetrofit")) {
        val contentType = okhttp3.MediaType.parse("application/json")!!
        val jsonConfig = Json {
            ignoreUnknownKeys = true
        }

        Retrofit.Builder()
            .baseUrl("http://192.168.1.95:8080/") // adresse IP du pc
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
    }

    // 2. Fournir l'instance Retrofit pour Resend (Email)
    single(named("EmailRetrofit")) {
        val contentType = okhttp3.MediaType.parse("application/json")!!
        val jsonConfig = Json {
            ignoreUnknownKeys = true
        }

        Retrofit.Builder()
            .baseUrl("https://api.resend.com/")
            .addConverterFactory(jsonConfig.asConverterFactory(contentType))
            .build()
    }

    // 3. Fournir le Service API Produit
    single<ProductApiService> {
        get<Retrofit>(named("LocalRetrofit")).create(ProductApiService::class.java)
    }

    // 4. Fournir le Service API Email
    single<EmailApiService> {
        get<Retrofit>(named("EmailRetrofit")).create(EmailApiService::class.java)
    }
}