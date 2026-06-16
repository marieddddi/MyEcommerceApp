package com.formation.myecommerceapp.domain.data.remote.service

import com.formation.myecommerceapp.domain.data.remote.dto.ProductDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET

@Serializable
data class ProductResponse(
    @SerialName("products") val products: List<ProductDto>
)

interface ProductApiService {
    @GET("v1/products")
    suspend fun getProducts(): Response<ProductResponse>
}