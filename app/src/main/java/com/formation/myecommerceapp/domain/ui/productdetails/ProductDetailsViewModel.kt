package com.formation.myecommerceapp.domain.ui.productdetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.formation.myecommerceapp.domain.data.local.dao.ProductDao
import com.formation.myecommerceapp.domain.data.repository.ProductRepository
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
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ProductDetailsRoute>()

    // 1. Define the missing state flows here
    // Note: If your Result sealed class doesn't have a 'Loading' object,
    // change 'Result.Loading' to whatever your default state is.
    private val _state = MutableStateFlow<Result<ProductDetailsState>>(Result.Loading)
    val state: StateFlow<Result<ProductDetailsState>> = _state.asStateFlow()

    init {
        loadState()
    }

    private fun loadState() {
        viewModelScope.launch {
            val result = productRepository.getById(route.productId)
                ?.toProductDetails()
                ?.let { Result.Success(ProductDetailsState(product = it)) }
                ?: Result.Error(ProductNotFoundException(route.productId))
            _state.value = result // Now '_state' is recognized!
        }
    }

    fun addProductToCart() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                productRepository.upsert(state.product.toEntity().copy(isInCart = true))
                loadState()
            }
        }
    }

    fun removeProductFromCart() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                productRepository.upsert(state.product.toEntity().copy(isInCart = false))
                loadState()
            }
        }
    }

    fun toggleFavoriteStatus() {
        _state.value.getOrNull()?.let { state ->
            viewModelScope.launch {
                val product = state.product
                productRepository.upsert(product.toEntity().copy(isFavorite = !product.isFavorite))
                loadState()
            }
        }
    }
}