package com.formation.myecommerceapp.domain.data.repository

import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.data.local.entity.ProductEntity
import com.formation.myecommerceapp.domain.data.remote.service.ProductApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ProductLocalFirstRepository(
    private val productDao: ProductDao,
    private val productApiService: ProductApiService,
) : ProductRepository {

    override fun getAll(): Flow<List<ProductEntity>> = flow {
        // récupération de la bdd
        val localProducts = productDao.getAll().first()

        if (localProducts.isNotEmpty()) {
            emit(localProducts)
        } else {
            val response = productApiService.getProducts()
            if (response.isSuccessful) {
                response.body()?.products?.forEach { dto ->
                    // produit existe déjà en local ?
                    val existingProduct = localProducts.find { it.id == dto.id.toInt() }

                    // Si déjà en local, on garde la quantité locale, sinon on se base sur le booléen
                    val finalQuantity = existingProduct?.quantityInCart
                        ?: (if (dto.isInCart) 1 else 0)

                    productDao.upsert(
                        ProductEntity(
                            id = dto.id.toInt(),
                            name = dto.name,
                            description = dto.description ?: "",
                            isAvailable = dto.isAvailable,
                            price = dto.price,
                            category = dto.category,
                            averageMark = dto.averageMark,
                            markCount = dto.markCount,
                            imageDrawable = dto.imageBase64 ?: "",
                            isFavorite = existingProduct?.isFavorite ?: false,
                            quantityInCart = finalQuantity
                        )
                    )
                }
                emit(productDao.getAll().first())
            }
        }
    }

    override suspend fun getById(id: Int): ProductEntity? = productDao.getById(id)

    override suspend fun upsert(product: ProductEntity) = productDao.upsert(product)

    fun getCartProducts(): Flow<List<ProductEntity>> = productDao.getCartProducts()

    fun getFavoriteProducts(): Flow<List<ProductEntity>> = productDao.getFavoriteProducts()

}