package com.formation.myecommerceapp.domain.data.remote.dto

import com.formation.myecommerceapp.ui.productlist.state.Product
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProductDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
    // Note : On utilise String? ici car kotlinx.serialization ne sait pas
    // décoder nativement l'objet global "Base64" du package kotlin.io.encoding
    @SerialName("imageBase64") val imageBase64: String?,
    @SerialName("isAvailable") val isAvailable: Boolean,
    @SerialName("price") val price: Double,
    @SerialName("category") val category: String,
    @SerialName("averageMark") val averageMark: Double,
    @SerialName("markCount") val markCount: Int,
    @SerialName("isInCart") val isInCart: Boolean
)

fun ProductDto.toProduct(): Product {
    return Product(
        id = this.id.toInt(),
        name = this.name,
        description = this.description ?: "",
        price = this.price,
        imageDrawable =  this.imageBase64,
        isAvailable = this.isAvailable,
    )
}