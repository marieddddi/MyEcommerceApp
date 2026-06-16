package com.formation.myecommerceapp.domain.ui.productdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.domain.exception.ProductNotFoundException
import com.formation.myecommerceapp.domain.domain.mapper.toEntity
import com.formation.myecommerceapp.domain.domain.mapper.toProductDetails
import com.formation.myecommerceapp.ui.productdetails.state.ProductDetailsState
import com.formation.myecommerceapp.ui.routing.ProductDetailsRoute
import com.formation.myecommerceapp.utils.Result
import com.formation.myecommerceapp.utils.getOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val productDao: ProductDao,
) : ViewModel() {
    private val _state = MutableStateFlow<Result<ProductDetailsState>>(Result.Loading)
    val state: StateFlow<Result<ProductDetailsState>>
        get() = _state.asStateFlow()

    val route = savedStateHandle.toRoute<ProductDetailsRoute>()

    init {
        loadState()
    }

    private fun loadState() {
        viewModelScope.launch {
            val result = productDao.getById(route.productId)
                ?.toProductDetails()
                ?.let { product ->
                    Log.d("ProductDetailsViewModel", product.toString())
                    Result.Success(ProductDetailsState(product = product))
                } ?: Result.Error(ProductNotFoundException(route.productId))

            _state.value = result
        }
    }

    fun addProductToCart() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                val product = state.product.toEntity().copy(isInCart = true)

                productDao.upsert(product)

                loadState()
            }
        }
    }

    fun removeProductFromCart() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                val product = state.product.toEntity().copy(isInCart = false)

                productDao.upsert(product)

                loadState()
            }
        }
    }

    fun toggleFavoriteStatus() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                val product = state.product
                val productEntity = product.toEntity().copy(isFavorite = !product.isFavorite)

                productDao.upsert(productEntity)

                loadState()
            }
        }
    }
}