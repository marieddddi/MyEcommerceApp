package com.formation.myecommerceapp.domain.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String,
    @ColumnInfo(name = "image_drawable") val imageDrawable: String,
    @ColumnInfo(name = "is_available") val isAvailable: Boolean,
    val price: Double,
    val category: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "average_mark") val averageMark: Double,
    @ColumnInfo(name = "mark_count") val markCount: Int,
    @ColumnInfo(name = "quantity_in_cart") val quantityInCart: Int = 0,
)
