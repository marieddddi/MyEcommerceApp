package com.formation.myecommerceapp.domain.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: Int): ProductEntity?

    @Query("SELECT * FROM products WHERE quantity_in_cart >0")
    fun getCartProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun upsert(product: ProductEntity)
}