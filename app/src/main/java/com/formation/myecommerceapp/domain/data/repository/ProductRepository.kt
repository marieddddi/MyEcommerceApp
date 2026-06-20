package com.formation.myecommerceapp.domain.data.repository

import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAll(): Flow<List<ProductEntity>>
    suspend fun getById(id: Int): ProductEntity?
    suspend fun upsert(product: ProductEntity)
    fun getFavorites(): Flow<List<ProductEntity>>
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
}